package ru.platform.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import ru.platform.repository.BaseOrdersRepository;
import ru.platform.repository.OrdersByCustomersRepository;
import ru.platform.repository.OrdersPerWeekRepository;
import ru.platform.service.IOrdersService;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class OrdersService implements IOrdersService {

    private OrdersByCustomersRepository ordersByCustomersRepository;
    private OrdersPerWeekRepository ordersPerWeekRepository;
    private BaseOrdersRepository baseOrdersRepository;

}
