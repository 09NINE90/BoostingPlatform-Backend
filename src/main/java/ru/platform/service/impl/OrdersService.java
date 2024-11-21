package ru.platform.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.platform.entity.BaseOrdersEntity;
import ru.platform.repository.BaseOrdersRepository;
import ru.platform.repository.OrdersByCustomersRepository;
import ru.platform.repository.OrdersPerWeekRepository;
import ru.platform.service.IOrdersService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService implements IOrdersService {

    private final OrdersByCustomersRepository ordersByCustomersRepository;
    private final OrdersPerWeekRepository ordersPerWeekRepository;
    private final BaseOrdersRepository baseOrdersRepository;

    @Override
    public List<BaseOrdersEntity> getAllOrders() {
        return baseOrdersRepository.findAll();
    }
}
