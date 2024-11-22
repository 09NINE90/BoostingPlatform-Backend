package ru.platform.service;

import ru.platform.entity.BaseOrdersEntity;

import java.util.List;

public interface IOrdersService {
    List<BaseOrdersEntity> getAllOrders();
}
