package ru.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.platform.entity.OrdersPerWeekEntity;

import java.util.UUID;

@Repository
public interface OrdersPerWeekRepository extends JpaRepository<OrdersPerWeekEntity, UUID> {
}
