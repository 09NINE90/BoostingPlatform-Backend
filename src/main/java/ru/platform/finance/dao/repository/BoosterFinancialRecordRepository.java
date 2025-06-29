package ru.platform.finance.dao.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.platform.finance.dao.BoosterFinancialRecordEntity;
import ru.platform.finance.enumz.PaymentStatus;
import ru.platform.user.dao.UserEntity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoosterFinancialRecordRepository extends JpaRepository<BoosterFinancialRecordEntity, UUID> {

    @Modifying
    @Query("UPDATE BoosterFinancialRecordEntity r SET r.status = :newStatus, r.completedAt = :completedAt " +
            "WHERE r.status = 'ON_PENDING' AND r.createdAt <= :cutoffDate " +
            "AND (r.recordType = 'SALARY' OR r.recordType = 'TIP')")
    int markPendingAsCompleted(
            @Param("cutoffDate") OffsetDateTime cutoffDate,
            @Param("newStatus") PaymentStatus newStatus,
            @Param("completedAt") OffsetDateTime completedAt
    );

    @Query("SELECT r FROM BoosterFinancialRecordEntity r " +
            "WHERE r.status = 'COMPLETED' AND r.calculated = false " +
            "AND (r.recordType = 'SALARY' OR r.recordType = 'TIP') " +
            "ORDER BY r.completedAt ASC")
    List<BoosterFinancialRecordEntity> findUncalculatedCompleted();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM BoosterFinancialRecordEntity r WHERE r.id = :id")
    Optional<BoosterFinancialRecordEntity> findByIdForUpdate(@Param("id") UUID id);

    @Query("SELECT r FROM BoosterFinancialRecordEntity r WHERE r.booster = :booster ORDER BY r.createdAt DESC")
    List<BoosterFinancialRecordEntity> findAllByBooster(@Param("booster") UserEntity booster);
}
