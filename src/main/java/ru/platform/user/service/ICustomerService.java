package ru.platform.user.service;

import ru.platform.user.dao.CustomerProfileEntity;
import ru.platform.user.dto.response.CustomerProfileRsDto;

public interface ICustomerService {
    /**
     * Обновляет профиль заказчика
     */
    void updateCustomerProfile(CustomerProfileEntity customerProfileEntity);

    /**
     * Получение профиля пользователя (заказчика)
     */
    CustomerProfileRsDto getCustomerProfileData();
}
