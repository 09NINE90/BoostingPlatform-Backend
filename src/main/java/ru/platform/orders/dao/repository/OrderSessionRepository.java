package ru.platform.orders.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.platform.orders.dao.OrderSessionEntity;
import ru.platform.orders.enumz.SessionStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderSessionRepository extends JpaRepository<OrderSessionEntity, Long> {

    List<OrderSessionEntity> findByOrderId(UUID orderId);

    Optional<OrderSessionEntity> findByOrderIdAndStatus(UUID orderId, SessionStatus status);

    @Query("SELECT s FROM OrderSessionEntity s WHERE s.order.id = :orderId AND s.status = 'ACTIVE'")
    Optional<OrderSessionEntity> findActiveSessionByOrderId(@Param("orderId") UUID orderId);

    @Query("SELECT s FROM OrderSessionEntity s WHERE s.user.id = :userId AND s.status = 'ACTIVE'")
    List<OrderSessionEntity> findActiveSessionsByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(s) > 0 FROM OrderSessionEntity s WHERE s.order.id = :orderId AND s.status = 'ACTIVE'")
    boolean existsActiveSessionByOrderId(@Param("orderId") UUID orderId);
}
