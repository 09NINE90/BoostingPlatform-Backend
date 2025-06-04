package ru.platform.orders.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.platform.exception.PlatformException;
import ru.platform.monitoring.MonitoringMethodType;
import ru.platform.monitoring.PlatformMonitoring;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.orders.dao.repository.OrderRepository;
import ru.platform.orders.dao.specification.OrderSpecification;
import ru.platform.orders.dto.request.CreateOrderRqDto;
import ru.platform.orders.dto.request.OrdersByCreatorRqDto;
import ru.platform.orders.dto.response.OrderFiltersRsDto;
import ru.platform.orders.dto.response.OrderFromCartRsDto;
import ru.platform.orders.dto.response.OrderListRsDto;
import ru.platform.orders.dto.response.OrderStatusRsDto;
import ru.platform.orders.enumz.OrderStatus;
import ru.platform.orders.mapper.OrderMapper;
import ru.platform.orders.service.IOrderService;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.service.IAuthService;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import static ru.platform.exception.ErrorType.NOT_FOUND_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderMapper mapper;
    private final IAuthService authService;
    private final OrderRepository orderRepository;
    private final OrderSpecification specification;

    private final String LOG_PREFIX = "OrderService: {}";

    @Override
    @PlatformMonitoring(name = MonitoringMethodType.CREATE_ORDER)
    public List<OrderFromCartRsDto> createOrder(CreateOrderRqDto orderRqDto) {
        List<OrderEntity> ordersToSave = orderRqDto.getItems().stream()
                .map(mapper::toOrder)
                .toList();
        orderRepository.saveAll(ordersToSave);

        UserEntity user = authService.getAuthUser();

        List<OrderEntity> orders = orderRepository.findAllByCreator(user);
        return orders.stream().map(mapper::toOrderFromCartDto).toList();
    }

    @Override
    public List<OrderListRsDto> getByCreator(OrderStatus status) {
        UserEntity user = authService.getAuthUser();

        OrdersByCreatorRqDto ordersByCreatorRqDto = OrdersByCreatorRqDto.builder().status(status).build();
        ordersByCreatorRqDto.setCreator(user);

        List<OrderEntity> orders = getServicePageFunc().apply(ordersByCreatorRqDto);
        return orders.stream().map(mapper::toOrderLisRsDto).toList();
    }

    private Function<OrdersByCreatorRqDto, List<OrderEntity>> getServicePageFunc() {
        try {
            return request -> orderRepository.findAll(specification.getFilter(request));
        } catch (Exception e) {
            log.error(LOG_PREFIX, NOT_FOUND_ERROR.getMessage());
            throw new PlatformException(NOT_FOUND_ERROR);
        }
    }

    @Override
    public OrderFiltersRsDto getOrderFilters() {
        UserEntity user = authService.getAuthUser();

        List<String> statuses = orderRepository.findAllDistinctStatusesByCreator(user);
        List<String> gameNames = orderRepository.findAllDistinctGameNamesByCreator(user);
        double minPrice = orderRepository.findMinPrice(user);
        double maxPrice = orderRepository.findMaxPrice(user);

        return OrderFiltersRsDto.builder()
                .gameNames(gameNames)
                .statuses(statuses)
                .price(OrderFiltersRsDto.PriceFilterDto.builder()
                        .priceMin(minPrice)
                        .priceMax(maxPrice)
                        .build())
                .build();
    }

    @Override
    public List<OrderStatusRsDto> getOrderStatuses() {
        UserEntity user = authService.getAuthUser();
        return orderRepository.findAllDistinctStatusesByCreator(user)
                .stream()
                .map(s -> OrderStatusRsDto.builder()
                        .id(UUID.randomUUID().toString())
                        .name(s)
                        .build())
                .toList();
    }

}
