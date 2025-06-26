package ru.platform.orders.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.platform.monitoring.MonitoringMethodType;
import ru.platform.monitoring.PlatformMonitoring;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.orders.dao.repository.OrderRepository;
import ru.platform.orders.dto.request.CreateOrderRqDto;
import ru.platform.orders.dto.response.OrderRsDto;
import ru.platform.orders.enumz.OrderStatus;
import ru.platform.orders.mapper.OrderMapper;
import ru.platform.orders.service.IOrderCustomerService;
import ru.platform.orders.service.IValidationOrderService;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.service.IAuthService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderCustomerService implements IOrderCustomerService {

    private final OrderMapper mapper;
    private final IAuthService authService;
    private final OrderRepository orderRepository;
    private final IValidationOrderService validationOrderService;

    private final String LOG_PREFIX = "OrderCustomerService: {}";

    // TODO сделать обновление полей total_orders и total_amount_of_orders
    //  в профиле заказчика при успешном создании заказа
    @Override
    @Transactional
    @PlatformMonitoring(name = MonitoringMethodType.CREATE_ORDER)
    public void createOrder(CreateOrderRqDto orderRqDto) {
        validationOrderService.validateCreateOrderRqDto(orderRqDto);

        UserEntity user = authService.getAuthUser();
        List<OrderEntity> ordersToSave = orderRqDto.getItems().stream()
                .map(item -> {
                    OrderEntity entity = mapper.toOrderEntity(item);
                    entity.setCreator(user);
                    return entity;
                })
                .toList();


        orderRepository.saveAll(ordersToSave);
    }

    @Override
    public List<OrderRsDto> getByCreator(OrderStatus status) {
        UserEntity user = authService.getAuthUser();
        List<OrderEntity> orders;
        if (status == null) {
            orders = orderRepository.findAllByCreator(user);
        } else {
            orders = orderRepository.findAllByStatusAndByCreator(status.name(), user);
        }
        return orders.stream().map(mapper::toOrderRsDto).toList();
    }

    @Override
    public long getCountOrdersByCustomer(UserEntity user) {
        return orderRepository.findCountOrdersByCreator(user);
    }

    @Override
    public long getCountCompletedOrdersByBooster(UserEntity user) {
        return orderRepository.findCountCompletedOrdersByBooster(user);
    }

}
