package ru.platform.user.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.platform.exception.ErrorType;
import ru.platform.exception.PlatformException;
import ru.platform.user.dao.ReferralRelationEntity;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.dto.response.ReferralInfoRsDto;
import ru.platform.user.mapper.ReferralInfoMapper;
import ru.platform.user.repository.ReferralRelationRepository;
import ru.platform.user.service.IReferralRelationService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReferralRelationService implements IReferralRelationService {

    private final ReferralInfoMapper referralInfoMapper;
    private final ReferralRelationRepository referralRelationRepository;

    @Override
    @Transactional
    public void calculateReferralBonus(UserEntity referral, BigDecimal bonusSum, BigDecimal totalSum, Integer totalOrderCount) {
        ReferralRelationEntity referralRelation = referralRelationRepository.findByReferred(referral)
                .orElseThrow(() -> new PlatformException(ErrorType.NOT_FOUND_ERROR));

        referralRelation.setReferralBalance(
                referralRelation.getReferralBalance().add(bonusSum)
        );
        referralRelation.setTotalEarned(
                referralRelation.getTotalEarned().add(bonusSum)
        );

        if (totalOrderCount > 0) {
            referralRelation.setHasActivity(true);
            referralRelation.setLastActivityAt(OffsetDateTime.now());
        }

        referralRelationRepository.save(referralRelation);
    }

    @Override
    public ReferralInfoRsDto getUserReferralInfo(UserEntity referrer) {
        List<ReferralRelationEntity> relationEntities = referralRelationRepository.findAllByReferrer(referrer);
        return referralInfoMapper.mapToReferralInfoDto(relationEntities);
    }

}
