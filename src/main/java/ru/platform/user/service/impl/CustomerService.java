package ru.platform.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.platform.user.dao.CustomerProfileEntity;
import ru.platform.user.repository.CustomerProfileRepository;
import ru.platform.user.service.ICustomerService;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final CustomerProfileRepository customerProfileRepository;

    @Override
    @Transactional
    public void updateCustomerProfile(CustomerProfileEntity customerProfileEntity) {
        customerProfileRepository.save(customerProfileEntity);
    }
}
