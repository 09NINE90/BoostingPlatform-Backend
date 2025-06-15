package ru.platform.orders.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.platform.exception.PlatformException;
import ru.platform.monitoring.MonitoringMethodType;
import ru.platform.monitoring.PlatformMonitoring;
import ru.platform.orders.PaginationOrdersUtil;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.orders.dao.repository.OrderRepository;
import ru.platform.orders.dao.specification.OrderSpecification;
import ru.platform.orders.dto.request.CreateOrderRqDto;
import ru.platform.orders.dto.request.OrdersByFiltersRqDto;
import ru.platform.orders.dto.response.OrderFiltersRsDto;
import ru.platform.orders.dto.response.OrderFromCartRsDto;
import ru.platform.orders.dto.response.OrderListRsDto;
import ru.platform.orders.dto.response.OrderRsDto;
import ru.platform.orders.enumz.OrderStatus;
import ru.platform.orders.mapper.OrderMapper;
import ru.platform.orders.service.IOrderService;
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
public class OrderService implements IOrderService {

    private final OrderMapper mapper;
    private final IAuthService authService;
    private final OrderRepository orderRepository;
    private final OrderSpecification specification;
    private final PaginationOrdersUtil paginationOrdersUtil;

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
    public List<OrderRsDto> getByCreator(OrderStatus status) {
        UserEntity user = authService.getAuthUser();

        OrdersByFiltersRqDto ordersByCreatorRqDto = OrdersByFiltersRqDto.builder()
                .status(status)
                .creator(user)
                .build();

        List<OrderEntity> orders = getServicePageFunc().apply(ordersByCreatorRqDto);
        return orders.stream().map(mapper::toOrderRsDto).toList();
    }

    private Function<OrdersByFiltersRqDto, List<OrderEntity>> getServicePageFunc() {
        try {
            return request -> orderRepository.findAll(specification.getFilter(request));
        } catch (Exception e) {
            log.error(LOG_PREFIX, NOT_FOUND_ERROR.getMessage());
            throw new PlatformException(NOT_FOUND_ERROR);
        }
    }

    private Function<OrdersByFiltersRqDto, Page<OrderEntity>> getServicePageFuncWithSort() {
        try {
            return request -> orderRepository
                    .findAll(specification.getFilter(request), paginationOrdersUtil.getPageRequest(request));
        } catch (Exception e) {
            log.error(LOG_PREFIX, NOT_FOUND_ERROR.getMessage());
            throw new PlatformException(NOT_FOUND_ERROR);
        }
    }

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

        preparationRequest(request, ratio);
        Page<OrderEntity> orders = getServicePageFuncWithSort().apply(request);
        OrderListRsDto response = mapper.toOrderListRsDto(orders);
        return recalculationPrice(response, ratio);
    }

    private void preparationRequest(OrdersByFiltersRqDto request, double ratio) {
        request.setStatus(CREATED);
        OrdersByFiltersRqDto.PriceDto requestPrice = request.getPrice();
        if (requestPrice != null && requestPrice.getPriceFrom() != null && requestPrice.getPriceTo() != null) {
            requestPrice.setPriceFrom(Math.floor(requestPrice.getPriceFrom() / ratio));
            requestPrice.setPriceTo(Math.floor(requestPrice.getPriceTo() / ratio));
        }
    }

    private OrderListRsDto recalculationPrice(OrderListRsDto response, double ratio) {
        List<OrderRsDto> orders = response.getOrders().stream()
                .peek(o -> o.setTotalPrice(o.getTotalPrice() * ratio))
                .toList();
        response.setOrders(orders);
        return response;
    }

    @Override
    public OrderRsDto getOrderById(UUID orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new PlatformException(NOT_FOUND_ERROR));
        return mapper.toOrderRsDto(order);
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

}
