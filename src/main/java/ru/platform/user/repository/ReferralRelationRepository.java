package ru.platform.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.platform.user.dao.ReferralRelationEntity;
import ru.platform.user.dao.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReferralRelationRepository extends JpaRepository<ReferralRelationEntity, UUID> {

    boolean existsByReferredId(UUID referredId);

    Optional<ReferralRelationEntity> findByReferred(UserEntity referred);

    List<ReferralRelationEntity> findAllByReferrer(UserEntity referrer);

    int countByReferrerId(UUID referrerId);

}
