package ru.platform.orders.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.platform.exception.PlatformException;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.orders.dao.repository.OrderRepository;
import ru.platform.orders.dao.specification.OrderSpecification;
import ru.platform.orders.dto.request.OrdersByBoosterRqDto;
import ru.platform.orders.dto.request.OrdersByFiltersRqDto;
import ru.platform.orders.dto.response.OrderByBoosterRsDto;
import ru.platform.orders.dto.response.OrderFiltersRsDto;
import ru.platform.orders.dto.response.OrderListRsDto;
import ru.platform.orders.dto.response.OrderRsDto;
import ru.platform.orders.mapper.OrderMapper;
import ru.platform.orders.service.IOrderBoosterService;
import ru.platform.orders.utils.PaginationOrdersUtil;
import ru.platform.orders.utils.SortOrderUtils;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.service.IAuthService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import static ru.platform.LocalConstants.Variables.BOOSTER_LIMIT_ORDERS_IN_WORK;
import static ru.platform.exception.ErrorType.*;
import static ru.platform.orders.enumz.OrderStatus.CREATED;
import static ru.platform.orders.enumz.OrderStatus.IN_PROGRESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderBoosterService implements IOrderBoosterService {

    private final OrderMapper mapper;
    private final IAuthService authService;
    private final OrderRepository orderRepository;
    private final OrderSpecification specification;

    private final String LOG_PREFIX = "OrderBoosterService: {}";

    @Override
    public OrderFiltersRsDto getFiltersForCreatedOrders() {
        List<String> statuses = orderRepository.findAllDistinctStatuses();
        List<String> gamePlatforms = orderRepository.findAllDistinctGamePlatforms();
        List<String> gameNames = orderRepository.findAllDistinctGameNames();
        Double minPrice = orderRepository.findMinPrice();
        Double maxPrice = orderRepository.findMaxPrice();

        return OrderFiltersRsDto.builder()
                .gameNames(gameNames)
                .gamePlatforms(gamePlatforms)
                .statuses(statuses)
                .price(OrderFiltersRsDto.PriceFilterDto.builder()
                        .priceMin(minPrice)
                        .priceMax(maxPrice)
                        .build())
                .build();
    }

    @Override
    public OrderFiltersRsDto getFiltersForOrdersByBooster() {
        UserEntity user = authService.getAuthUser();
        List<String> statuses = orderRepository.findAllDistinctStatusesByBooster(user);
        List<String> gamePlatforms = orderRepository.findAllDistinctGamePlatformsByBooster(user);
        List<String> gameNames = orderRepository.findAllDistinctGameNamesByBooster(user);
        Double minPrice = orderRepository.findMinPriceByBooster(user);
        Double maxPrice = orderRepository.findMaxPriceByBooster(user);

        return OrderFiltersRsDto.builder()
                .gameNames(gameNames)
                .gamePlatforms(gamePlatforms)
                .statuses(statuses)
                .price(OrderFiltersRsDto.PriceFilterDto.builder()
                        .priceMin(minPrice)
                        .priceMax(maxPrice)
                        .build())
                .build();
    }

    @Override
    public OrderListRsDto getAllOrders(OrdersByFiltersRqDto request) {
        UserEntity user = authService.getAuthUser();
        double ratio = user.getBoosterProfile().getPercentageOfOrder();

        request.setStatus(CREATED);
        preparationRequest(request, ratio);
        Page<OrderEntity> orders = getServicePageFuncWithSortAndPage().apply(request);
        OrderListRsDto response = mapper.toOrderListRsDto(orders);
        response.setOrders(recalculationPrice(response.getOrders(), ratio));
        return response;
    }

    @Override
    public List<OrderByBoosterRsDto> getOrdersByBooster(OrdersByBoosterRqDto request) {
        UserEntity user = authService.getAuthUser();
        request.setBooster(user);

        OrdersByFiltersRqDto preparedRequest = mapper.toOrdersByFiltersRqDto(request);

        List<OrderEntity> orders = getServicePageFuncWithSort().apply(preparedRequest);

        return orders.stream().map(mapper::toOrderByBoosterRsDto).toList();
    }

    @Override
    public void acceptOrder(UUID orderId) {
        UserEntity user = authService.getAuthUser();
        double ratio = user.getBoosterProfile().getPercentageOfOrder();

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new PlatformException(NOT_FOUND_ERROR));

        checkPossibilityToAcceptOrder(order, user);

        order.setBooster(user);
        order.setStatus(IN_PROGRESS.name());
        order.setStartTimeExecution(OffsetDateTime.now());
        order.setBoosterSalary(calculateBoosterSalary(order, ratio));
        orderRepository.save(order);
    }

    /**
     * Проверка возможности взять заказ бустером
     */
    void checkPossibilityToAcceptOrder(OrderEntity order,UserEntity user){
        if (!order.getStatus().equals(CREATED.name()) && order.getBooster() != null) {
            throw new PlatformException(ORDER_ALREADY_IN_PROGRESS_ERROR);
        }

        if (getCountOrdersInWork(user) >= BOOSTER_LIMIT_ORDERS_IN_WORK){
            throw new PlatformException(ORDER_LIMIT_EXCEEDED_ERROR);
        }
    }

    /**
     * Расчет суммы, которую бустер получит за заказ
     * умножаем на процент бустера и округляем до сотых в большую сторону
     */
    private BigDecimal calculateBoosterSalary(OrderEntity order, double ratio) {
        return order.getTotalPrice().multiply(new BigDecimal(String.valueOf(ratio)))
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Получение количества заказов, которые находятся в работе и закреплены за бустером
     */
    private long getCountOrdersInWork(UserEntity boosterUser) {
        return orderRepository.findCountOrdersInWorkByBooster(boosterUser);
    }

    /**
     * Подготовка запроса (изменение цен относительно процента бустера)
     */
    private void preparationRequest(OrdersByFiltersRqDto request, double ratio) {
        OrdersByFiltersRqDto.PriceDto requestPrice = request.getTotalPrice();
        if (requestPrice != null && requestPrice.getPriceFrom() != null && requestPrice.getPriceTo() != null) {
            requestPrice.setPriceFrom(Math.floor(requestPrice.getPriceFrom() / ratio));
            requestPrice.setPriceTo(Math.floor(requestPrice.getPriceTo() / ratio));
        }
    }

    /**
     * Пересчет цен относительно процента бустера для ответа фронту
     */
    private List<OrderRsDto> recalculationPrice(List<OrderRsDto> orders, double ratio) {
        return orders.stream()
                .peek(o -> o.setTotalPrice(o.getTotalPrice() * ratio))
                .toList();
    }

    /**
     * Запрос на получение списка ордеров с фильтрами, пагинацией и сортировкой
     */
    private Function<OrdersByFiltersRqDto, Page<OrderEntity>> getServicePageFuncWithSortAndPage() {
        try {
            return request -> orderRepository
                    .findAll(specification.getFilter(request), PaginationOrdersUtil.getPageRequest(request));
        } catch (Exception e) {
            log.error(LOG_PREFIX, NOT_FOUND_ERROR.getMessage());
            throw new PlatformException(NOT_FOUND_ERROR);
        }
    }

    /**
     * Запрос на получение списка ордеров с фильтрами и сортировкой
     */
    private Function<OrdersByFiltersRqDto, List<OrderEntity>> getServicePageFuncWithSort() {
        try {
            return request -> orderRepository
                    .findAll(specification.getFilter(request), SortOrderUtils.getSortBy(request));
        } catch (Exception e) {
            log.error(LOG_PREFIX, NOT_FOUND_ERROR.getMessage());
            throw new PlatformException(NOT_FOUND_ERROR);
        }
    }

}
