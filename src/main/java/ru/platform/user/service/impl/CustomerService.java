package ru.platform.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.platform.user.dao.CustomerProfileEntity;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.dao.UserProfileEntity;
import ru.platform.user.dto.response.CustomerProfileRsDto;
import ru.platform.user.enumz.CustomerStatus;
import ru.platform.user.repository.CustomerProfileRepository;
import ru.platform.user.service.IAuthService;
import ru.platform.user.service.ICustomerService;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static ru.platform.LocalConstants.CustomerSettings.COUNT_ORDERS_FOR_IMMORTAL_STATUS;
import static ru.platform.user.enumz.CustomerStatus.IMMORTAL;
import static ru.platform.user.enumz.CustomerStatus.VANGUARD;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final IAuthService authService;
    private final CustomerProfileRepository customerProfileRepository;

    @Override
    @Transactional
    public void updateCustomerProfile(CustomerProfileEntity customerProfileEntity) {
        customerProfileRepository.save(customerProfileEntity);
    }

    /**
     * Получение информации о заказчике
     */
    @Override
    public CustomerProfileRsDto getCustomerProfileData() {
        UserEntity userEntity = authService.getAuthUser();
        UserProfileEntity profileEntity = userEntity.getProfile();
        CustomerProfileEntity customerProfile = userEntity.getCustomerProfile();

        return CustomerProfileRsDto.builder()
                .email(userEntity.getUsername())
                .nickname(profileEntity.getNickname())
                .imageUrl(profileEntity.getImageUrl())
                .secondId(profileEntity.getSecondId())
                .description(profileEntity.getDescription())
                .discountPercentage(customerProfile.getDiscountPercentage().multiply(BigDecimal.valueOf(100)))
                .cashbackBalance(customerProfile.getCashbackBalance())
                .status(customerProfile.getStatus())
                .nextStatus(getNextStatus(customerProfile.getStatus()))
                .totalOrders(customerProfile.getTotalOrders())
                .progressAccountStatus(getProgressAccountStatus(customerProfile.getTotalOrders()))
                .build();
    }

    /**
     * Получение прогресса статуса заказчика
     * если процент прогресса больше 100, возвращаем ровно 100
     */
    private BigDecimal getProgressAccountStatus(Integer totalOrders) {
        BigDecimal result = BigDecimal.valueOf(totalOrders)
                .multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(COUNT_ORDERS_FOR_IMMORTAL_STATUS), 2, RoundingMode.HALF_UP);

        return result.compareTo(BigDecimal.valueOf(100)) > 0 ? BigDecimal.valueOf(100) : result;
    }

    /**
     * Получение следующего статуса заказчика
     */
    private CustomerStatus getNextStatus(CustomerStatus status) {
        return switch (status) {
            case EXPLORER -> VANGUARD;
            case VANGUARD -> IMMORTAL;
            case IMMORTAL -> null;
        };
    }
}
