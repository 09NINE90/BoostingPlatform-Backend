package ru.platform.orders.service.impl;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.platform.exception.PlatformException;
import ru.platform.monitoring.MonitoringMethodType;
import ru.platform.monitoring.PlatformMonitoring;
import ru.platform.offers.dao.OfferCartEntity;
import ru.platform.offers.dao.repository.OfferCartRepository;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.orders.dao.repository.OrderRepository;
import ru.platform.orders.dto.response.OrderRsDto;
import ru.platform.orders.enumz.OrderStatus;
import ru.platform.orders.mapper.OrderMapper;
import ru.platform.orders.service.IOrderCustomerService;
import ru.platform.user.dao.CustomerProfileEntity;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.enumz.CustomerStatus;
import ru.platform.user.service.IAuthService;
import ru.platform.user.service.ICustomerService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

import static ru.platform.LocalConstants.CustomerSettings.*;
import static ru.platform.exception.ErrorType.NOT_FOUND_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderCustomerService implements IOrderCustomerService {

    private final OrderMapper mapper;
    private final IAuthService authService;
    private final OrderRepository orderRepository;
    private final ICustomerService customerService;
    private final OfferCartRepository offerCartRepository;

    private final String LOG_PREFIX = "OrderCustomerService: {}";

    // TODO сделать обновление полей total_orders, total_amount_of_orders
    //  и добавить пересчет кэшбека и уровня заказчика
    //  в профиле заказчика при успешном создании заказа
    @Override
    @Transactional
    @PlatformMonitoring(name = MonitoringMethodType.CREATE_ORDER)
    public void createOrder(List<UUID> itemsIds) {

        // Получение заказов из таблицы по списку id
        List<OfferCartEntity> cartEntities = offerCartRepository.findAllById(itemsIds);

        UserEntity user = authService.getAuthUser();
        List<OrderEntity> ordersToSave = cartEntities.stream()
                .map(item -> {
                    OrderEntity entity = mapper.toOrderEntity(item);
                    entity.setCreator(user);
                    return entity;
                })
                .toList();

        // Сохранение заказов из корзины
        orderRepository.saveAll(ordersToSave);

        updateCustomerProfile(user.getCustomerProfile(), ordersToSave);
        // Удаление из корзины объектов, по которым созданы заказы
        offerCartRepository.deleteAllById(itemsIds);
    }

    /**
     * Обновление профиля заказчика
     */
    private void updateCustomerProfile(CustomerProfileEntity customerProfile, List<OrderEntity> createdOrders) {
        BigDecimal cashbackPercent = customerProfile.getDiscountPercentage();

        BigDecimal totalAmount = customerProfile.getTotalAmountOfOrders();
        for (OrderEntity order : createdOrders) {
            totalAmount = totalAmount.add(order.getTotalPrice());
        }
        int totalOrders = customerProfile.getTotalOrders() + createdOrders.size();

        BigDecimal cashbackToAdd = totalAmount
                .multiply(cashbackPercent)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal cashbackBalance = customerProfile.getCashbackBalance().add(cashbackToAdd);

        customerProfile.setTotalOrders(totalOrders);
        customerProfile.setTotalAmountOfOrders(totalAmount);
        customerProfile.setCashbackBalance(cashbackBalance);

        updateCustomerRating(customerProfile);
    }

    /**
     * Обновление статуса заказчика
     */
    private void updateCustomerRating(CustomerProfileEntity customerProfile) {
        int totalOrders = customerProfile.getTotalOrders();

        if (totalOrders >= COUNT_ORDERS_FOR_IMMORTAL_STATUS) {
            customerProfile.setStatus(CustomerStatus.IMMORTAL);
            customerProfile.setDiscountPercentage(DISCOUNT_PERCENTAGE_FOR_IMMORTAL_STATUS);
        } else if (totalOrders >= COUNT_ORDERS_FOR_VANGUARD_STATUS) {
            customerProfile.setStatus(CustomerStatus.VANGUARD);
            customerProfile.setDiscountPercentage(DISCOUNT_PERCENTAGE_FOR_VANGUARD_STATUS);
        }

        customerService.updateCustomerProfile(customerProfile);
    }

    @Override
    @PlatformMonitoring(name = MonitoringMethodType.GET_USER_ORDERS_BY_STATUS)
    public List<OrderRsDto> getOrdersByCreator(OrderStatus status) {
        UserEntity user = authService.getAuthUser();
        List<OrderEntity> orders;
        if (status == null) {
            orders = orderRepository.findAllByCreator(user);
        } else {
            orders = orderRepository.findAllByStatusAndByCreator(status, user);
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

    @Override
    public OrderRsDto getOrderById(UUID orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(
                () -> new PlatformException(NOT_FOUND_ERROR)
        );

        return mapper.toOrderRsDto(orderEntity);
    }

}
