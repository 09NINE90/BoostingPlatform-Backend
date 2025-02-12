package ru.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.platform.entity.OrderServicesEntity;

import java.util.UUID;

@Repository
public interface OrderServicesRepository extends JpaRepository<OrderServicesEntity, UUID>, JpaSpecificationExecutor<OrderServicesEntity> {
}