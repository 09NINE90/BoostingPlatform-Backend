package ru.platform.orders.service.impl;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.platform.exception.PlatformException;
import ru.platform.finance.service.IBoosterFinanceService;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.orders.dao.repository.OrderRepository;
import ru.platform.orders.dao.specification.OrderDashboardSpecification;
import ru.platform.orders.dao.specification.OrdersByBoosterSpecification;
import ru.platform.orders.dto.request.DashboardRqDto;
import ru.platform.orders.dto.request.OrdersByBoosterRqDto;
import ru.platform.orders.dto.response.*;
import ru.platform.orders.mapper.OrderMapper;
import ru.platform.orders.service.IOrderBoosterService;
import ru.platform.orders.utils.PaginationOrdersUtil;
import ru.platform.orders.utils.SortOrderUtils;
import ru.platform.user.dao.BoosterProfileEntity;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.service.IAuthService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptySet;
import static ru.platform.LocalConstants.BoosterSettings.BOOSTER_LIMIT_ORDERS_IN_WORK;
import static ru.platform.exception.ErrorType.*;
import static ru.platform.orders.enumz.OrderStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderBoosterService implements IOrderBoosterService {

    private final OrderMapper mapper;
    private final IAuthService authService;
    private final OrderRepository orderRepository;
    private final IBoosterFinanceService boosterFinanceService;
    private final OrdersByBoosterSpecification ordersByBoosterSpecification;
    private final OrderDashboardSpecification orderDashboardSpecification;

    private final String LOG_PREFIX = "OrderBoosterService: {}";

    @Override
    public DashboardFiltersRsDto getFiltersDashboard() {
        UserEntity user = authService.getAuthUser();
        BoosterProfileEntity boosterProfile = user.getBoosterProfile();

        Set<String> gameNames = getGameTagsByBooster(boosterProfile);

        List<String> gamePlatforms = orderRepository.findAllDistinctGamePlatforms(gameNames);
        Double minPrice = orderRepository.findMinPrice(gameNames);
        Double maxPrice = orderRepository.findMaxPrice(gameNames);

        return DashboardFiltersRsDto.builder()
                .gameNames(gameNames)
                .gamePlatforms(gamePlatforms)
                .price(DashboardFiltersRsDto.PriceFilterDto.builder()
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
    public OrderListRsDto getDashboard(DashboardRqDto request) {
        UserEntity user = authService.getAuthUser();
        BoosterProfileEntity boosterProfile = user.getBoosterProfile();

        if (request.getGameNames().isEmpty()) {
            if (getGameTagsByBooster(boosterProfile).isEmpty()) {
                throw new PlatformException(NO_GAME_TAGS_ERROR);
            }
            request.setGameNames(getGameTagsByBooster(boosterProfile));
        }

        double ratio = boosterProfile.getPercentageOfOrder();

        request.setStatus(CREATED);
        preparationRequest(request, ratio);
        Page<OrderEntity> orders = getServicePageFuncWithSortAndPage().apply(request);
        OrderListRsDto response = mapper.toOrderListRsDto(orders);
        response.setOrders(recalculationPrice(response.getOrders(), ratio));
        return response;
    }

    /**
     * Получение игровых тегов бустера
     */
    private Set<String> getGameTagsByBooster(BoosterProfileEntity boosterProfile) {
        if (boosterProfile.getGameTags() == null || boosterProfile.getGameTags().isEmpty()) return emptySet();
        return boosterProfile.getGameTags().stream()
                .map(gameTag -> gameTag.getGame().getTitle())
                .collect(Collectors.toSet());
    }

    /**
     * Подготовка запроса (изменение цен относительно процента бустера)
     */
    private void preparationRequest(DashboardRqDto request, double ratio) {
        DashboardRqDto.PriceDto requestPrice = request.getTotalPrice();
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
                .peek(o -> o.setTotalPrice(o.getTotalPrice().multiply(new BigDecimal(String.valueOf(ratio)))
                        .setScale(4, RoundingMode.HALF_UP)))
                .toList();
    }

    @Override
    public List<OrderByBoosterRsDto> getOrdersByBooster(OrdersByBoosterRqDto request) {
        UserEntity user = authService.getAuthUser();
        request.setBooster(user);

        List<OrderEntity> orders = getServicePageFuncWithSort().apply(request);
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
        order.setStatus(IN_PROGRESS);
        order.setStartTimeExecution(OffsetDateTime.now());
        order.setBoosterSalary(calculateBoosterSalary(order, ratio));
        orderRepository.save(order);
    }

    /**
     * Проверка возможности взять заказ бустером
     */
    void checkPossibilityToAcceptOrder(OrderEntity order, UserEntity user) {
        if (!order.getStatus().equals(CREATED) && order.getBooster() != null) {
            throw new PlatformException(ORDER_ALREADY_IN_PROGRESS_ERROR);
        }

        if (getCountOrdersInWork(user) >= BOOSTER_LIMIT_ORDERS_IN_WORK) {
            throw new PlatformException(ORDER_LIMIT_EXCEEDED_ERROR);
        }
    }

    /**
     * Расчет суммы, которую бустер получит за заказ
     * умножаем на процент бустера и округляем до сотых в большую сторону
     */
    private BigDecimal calculateBoosterSalary(OrderEntity order, double ratio) {
        return order.getTotalPrice().multiply(new BigDecimal(String.valueOf(ratio)))
                .setScale(4, RoundingMode.HALF_UP);
    }

    /**
     * Получение количества заказов, которые находятся в работе и закреплены за бустером
     */
    private long getCountOrdersInWork(UserEntity boosterUser) {
        return orderRepository.findCountOrdersInWorkByBooster(boosterUser);
    }

    /**
     * Запрос на получение списка ордеров с фильтрами, пагинацией и сортировкой
     */
    private Function<DashboardRqDto, Page<OrderEntity>> getServicePageFuncWithSortAndPage() {
        try {
            return request -> orderRepository
                    .findAll(orderDashboardSpecification.getFilter(request), PaginationOrdersUtil.getPageRequest(request));
        } catch (Exception e) {
            log.error(LOG_PREFIX, NOT_FOUND_ERROR.getMessage());
            throw new PlatformException(NOT_FOUND_ERROR);
        }
    }

    /**
     * Запрос на получение списка ордеров с фильтрами и сортировкой
     */
    private Function<OrdersByBoosterRqDto, List<OrderEntity>> getServicePageFuncWithSort() {
        try {
            return request -> orderRepository
                    .findAll(ordersByBoosterSpecification.getFilter(request), SortOrderUtils.getSortBy(request.getSort()));
        } catch (Exception e) {
            log.error(LOG_PREFIX, NOT_FOUND_ERROR.getMessage());
            throw new PlatformException(NOT_FOUND_ERROR);
        }
    }

    /**
     * Заверение выполнения заказа бустером
     */
    @Override
    @Transactional
    public void completeExecutionOrder(UUID orderId) {
        log.debug(LOG_PREFIX, "Поиск заказа для завершения");
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new PlatformException(NOT_FOUND_ERROR));

        if (order.getStatus() != IN_PROGRESS) {
            log.error(LOG_PREFIX, "Заказ с таким статусом не может быть завершен");
            throw new PlatformException(INVALID_ORDER_STATUS_FOR_COMPLETION_ERROR);
        }

        log.debug(LOG_PREFIX, "Установка даты/времени завершения выполнения заказа и смена статуса на ON_PENDING");
        order.setEndTimeExecution(OffsetDateTime.now());
        order.setStatus(ON_PENDING);

        log.debug(LOG_PREFIX, "Сохранение заказа с обновленными данными");
        orderRepository.save(order);

        log.debug(LOG_PREFIX, "Запрос на создание записи баланса с ЗП бустера");
        boosterFinanceService.createNewRecordOfSalaryBooster(order);
    }

}
