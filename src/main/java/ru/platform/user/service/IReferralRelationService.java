package ru.platform.user.service;

import ru.platform.user.dao.UserEntity;
import ru.platform.user.dto.response.ReferralInfoRsDto;

import java.math.BigDecimal;

public interface IReferralRelationService {

    void calculateReferralBonus(UserEntity referral, BigDecimal bonusSum, BigDecimal totalSum, Integer totalOrderCount);

    ReferralInfoRsDto getUserReferralInfo(UserEntity referrer);
}
