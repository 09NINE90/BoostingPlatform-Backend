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
import ru.platform.orders.dto.response.OrderFiltersRsDto;
import ru.platform.orders.dto.response.OrderListRsDto;
import ru.platform.orders.dto.response.OrderRsDto;
import ru.platform.orders.mapper.OrderMapper;
import ru.platform.orders.service.IOrderBoosterService;
import ru.platform.orders.utils.PaginationOrdersUtil;
import ru.platform.orders.utils.SortOrderUtils;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.service.IAuthService;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import static ru.platform.exception.ErrorType.NOT_FOUND_ERROR;
import static ru.platform.exception.ErrorType.ORDER_ALREADY_IN_PROGRESS_ERROR;
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
    public OrderFiltersRsDto getOrderFilters() {
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
    public OrderListRsDto getAllOrders(OrdersByFiltersRqDto request) {
        UserEntity user = authService.getAuthUser();
        double ratio = user.getBoosterProfile().getPercentageOfOrder();

        request.setStatus(CREATED);
        preparationRequest(request, ratio);
        Page<OrderEntity> orders = getServicePageFuncWithSortAndPage().apply(request);
        OrderListRsDto response = mapper.toOrderListRsDto(orders);
        response.setOrders(recalculationPrice(response, ratio));
        return response;
    }

    @Override
    public List<OrderRsDto> getOrdersByBooster(OrdersByBoosterRqDto request) {
        UserEntity user = authService.getAuthUser();
        request.setWorker(user);

        OrdersByFiltersRqDto preparedRequest = mapper.toOrdersByFiltersRqDto(request);
        double ratio = user.getBoosterProfile().getPercentageOfOrder();
        preparationRequest(preparedRequest, ratio);

        List<OrderEntity> orders = getServicePageFuncWithSort().apply(preparedRequest);
        return orders.stream().map(mapper::toOrderRsDto).toList();
    }

    @Override
    public void acceptOrder(UUID orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new PlatformException(NOT_FOUND_ERROR));

        if (!order.getStatus().equals(CREATED.name()) && order.getWorkerId() != null) {
            throw new PlatformException(ORDER_ALREADY_IN_PROGRESS_ERROR);
        }

        UserEntity user = authService.getAuthUser();
        order.setWorkerId(user);
        order.setStatus(IN_PROGRESS.name());
        orderRepository.save(order);
    }

    /**
     * Подготовка запроса (изменение цен относительно процента бустера)
     */
    private void preparationRequest(OrdersByFiltersRqDto request, double ratio) {
        OrdersByFiltersRqDto.PriceDto requestPrice = request.getPrice();
        if (requestPrice != null && requestPrice.getPriceFrom() != null && requestPrice.getPriceTo() != null) {
            requestPrice.setPriceFrom(Math.floor(requestPrice.getPriceFrom() / ratio));
            requestPrice.setPriceTo(Math.floor(requestPrice.getPriceTo() / ratio));
        }
    }

    /**
     * Пересчет цен относительно процента бустера для ответа фронту
     */
    private List<OrderRsDto> recalculationPrice(OrderListRsDto response, double ratio) {
        return response.getOrders().stream()
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
