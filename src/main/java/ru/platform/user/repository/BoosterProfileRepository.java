package ru.platform.user.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.platform.user.dao.BoosterProfileEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoosterProfileRepository extends JpaRepository<BoosterProfileEntity, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM BoosterProfileEntity p WHERE p.id = :id")
    Optional<BoosterProfileEntity> findByIdForUpdate(@Param("id") UUID id);
}
