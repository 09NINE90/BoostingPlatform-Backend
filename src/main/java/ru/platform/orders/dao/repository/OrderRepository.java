package ru.platform.orders.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.orders.enumz.OrderStatus;
import ru.platform.user.dao.UserEntity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID>, JpaSpecificationExecutor<OrderEntity> {
    List<OrderEntity> findAllByCreator(UserEntity creator);

    @Query("SELECT o FROM OrderEntity o WHERE o.creator = :creator AND o.status = :status")
    List<OrderEntity> findAllByStatusAndByCreator(@Param("status") OrderStatus status,
                                                  @Param("creator") UserEntity creator);

    @Query("SELECT DISTINCT o.gamePlatform FROM OrderEntity o WHERE o.gameName IN :gameNames")
    List<String> findAllDistinctGamePlatforms(@Param("gameNames") Set<String> gameNames);

    @Query("SELECT MIN(o.totalPrice) FROM OrderEntity o WHERE o.gameName IN :gameNames")
    Double findMinPrice(@Param("gameNames") Set<String> gameNames);

    @Query("SELECT MAX(o.totalPrice) FROM OrderEntity o WHERE o.gameName IN :gameNames")
    Double findMaxPrice(@Param("gameNames") Set<String> gameNames);

    @Query("select COUNT(*) from OrderEntity o WHERE o.creator = :creator")
    long findCountOrdersByCreator(@Param("creator") UserEntity creator);

    /**
     * Запросы для бустера
     */
    @Query("SELECT DISTINCT o.status FROM OrderEntity o WHERE o.booster = :booster")
    List<String> findAllDistinctStatusesByBooster(@Param("booster") UserEntity booster);

    @Query("SELECT DISTINCT o.gamePlatform FROM OrderEntity o WHERE o.booster = :booster")
    List<String> findAllDistinctGamePlatformsByBooster(@Param("booster") UserEntity booster);

    @Query("SELECT DISTINCT o.gameName FROM OrderEntity o WHERE o.booster = :booster")
    List<String> findAllDistinctGameNamesByBooster(@Param("booster") UserEntity booster);

    @Query("SELECT MIN(o.boosterSalary) FROM OrderEntity o WHERE o.booster = :booster")
    Double findMinPriceByBooster(@Param("booster") UserEntity booster);

    @Query("SELECT MAX(o.boosterSalary) FROM OrderEntity o WHERE o.booster = :booster")
    Double findMaxPriceByBooster(@Param("booster") UserEntity booster);

    @Query("select COUNT(*) from OrderEntity o WHERE o.booster = :booster AND o.status = 'IN_PROGRESS'")
    long findCountOrdersInWorkByBooster(@Param("booster") UserEntity booster);

    @Query("SELECT COUNT(*) FROM OrderEntity o WHERE o.booster = :booster AND o.status = 'COMPLETED'")
    long findCountCompletedOrdersByBooster(@Param("booster") UserEntity booster);

    @Modifying
    @Query("UPDATE OrderEntity o SET o.status = :newStatus, o.completedAt = :completedAt " +
            "WHERE o.status = 'ON_PENDING' AND o.endTimeExecution <= :cutoffDate ")
    int markPendingAsCompleted(
            @Param("cutoffDate") OffsetDateTime cutoffDate,
            @Param("newStatus") OrderStatus newStatus,
            @Param("completedAt") OffsetDateTime completedAt
    );

    @Query("SELECT o FROM OrderEntity o WHERE o.booster = :booster AND o.status = 'COMPLETED' " +
            "ORDER BY o.completedAt DESC")
    List<OrderEntity> findAllCompletedOrdersByBooster(@Param("booster") UserEntity booster);
}
