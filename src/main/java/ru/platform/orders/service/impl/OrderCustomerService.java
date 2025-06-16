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
import ru.platform.orders.dto.request.OrdersByFiltersRqDto;
import ru.platform.orders.dto.response.OrderFromCartRsDto;
import ru.platform.orders.dto.response.OrderRsDto;
import ru.platform.orders.enumz.OrderStatus;
import ru.platform.orders.mapper.OrderMapper;
import ru.platform.orders.service.IOrderCustomerService;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.service.IAuthService;

import java.util.List;
import java.util.function.Function;

import static ru.platform.exception.ErrorType.NOT_FOUND_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderCustomerService implements IOrderCustomerService {

    private final OrderMapper mapper;
    private final IAuthService authService;
    private final OrderRepository orderRepository;
    private final OrderSpecification specification;

    private final String LOG_PREFIX = "OrderCustomerService: {}";

    @Override
    @PlatformMonitoring(name = MonitoringMethodType.CREATE_ORDER)
    public List<OrderFromCartRsDto> createOrder(CreateOrderRqDto orderRqDto) {
        List<OrderEntity> ordersToSave = orderRqDto.getItems().stream()
                .map(mapper::toOrderEntity)
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

    /**
     * Запрос на получение списка ордеров с фильтрами
     */
    private Function<OrdersByFiltersRqDto, List<OrderEntity>> getServicePageFunc() {
        try {
            return request -> orderRepository.findAll(specification.getFilter(request));
        } catch (Exception e) {
            log.error(LOG_PREFIX, NOT_FOUND_ERROR.getMessage());
            throw new PlatformException(NOT_FOUND_ERROR);
        }
    }
}
