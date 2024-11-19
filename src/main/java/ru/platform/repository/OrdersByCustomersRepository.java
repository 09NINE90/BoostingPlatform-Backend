package ru.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.platform.entity.OrdersByCustomersEntity;

import java.util.UUID;

@Repository
public interface OrdersByCustomersRepository extends JpaRepository<OrdersByCustomersEntity, UUID> {
}
