package ru.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.platform.entity.ServicesEntity;

import java.util.UUID;

@Repository
public interface ServicesRepository extends JpaRepository<ServicesEntity, UUID>, JpaSpecificationExecutor<ServicesEntity> {
}