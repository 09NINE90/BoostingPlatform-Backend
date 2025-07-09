package ru.platform.user.service;

import ru.platform.user.dao.CustomerProfileEntity;

public interface ICustomerService {
    /**
     * Обновляет профиль заказчика
     */
    void updateCustomerProfile(CustomerProfileEntity customerProfileEntity);
}
