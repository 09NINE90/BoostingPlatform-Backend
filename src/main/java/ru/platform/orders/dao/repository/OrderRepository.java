package ru.platform.orders.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.user.dao.UserEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository  extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findAllByCreator(UserEntity user);
}
