package ru.platform.user.service;

import ru.platform.user.dao.CustomerProfileEntity;

public interface ICustomerService {
    void updateCustomerProfile(CustomerProfileEntity customerProfileEntity);
}
