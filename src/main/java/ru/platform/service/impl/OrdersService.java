package ru.platform.service.impl;

import org.springframework.stereotype.Service;
import ru.platform.repository.BaseOrdersRepository;
import ru.platform.repository.OrdersByCustomersRepository;
import ru.platform.service.IOrdersService;

@Service
public class OrdersService implements IOrdersService {

    private final OrdersByCustomersRepository ordersByCustomersRepository;
    private final BaseOrdersRepository baseOrdersRepository;

    public OrdersService(OrdersByCustomersRepository ordersByCustomersRepository, BaseOrdersRepository baseOrdersRepository) {
        this.ordersByCustomersRepository = ordersByCustomersRepository;
        this.baseOrdersRepository = baseOrdersRepository;
    }
}
