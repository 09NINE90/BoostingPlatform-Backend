package ru.platform.orders.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.user.dao.UserEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository  extends JpaRepository<OrderEntity, UUID>, JpaSpecificationExecutor<OrderEntity> {
    List<OrderEntity> findAllByCreator(UserEntity user);

    @Query("SELECT DISTINCT o.gameName FROM OrderEntity o")
    List<String> findAllDistinctGameNames();

    @Query("SELECT DISTINCT o.status FROM OrderEntity o")
    List<String> findAllDistinctStatuses();

    @Query("SELECT DISTINCT o.gamePlatform FROM OrderEntity o")
    List<String> findAllDistinctGamePlatforms();

    @Query("SELECT MIN(o.totalPrice) FROM OrderEntity o")
    Double findMinPrice();

    @Query("SELECT MAX(o.totalPrice) FROM OrderEntity o")
    Double findMaxPrice();

    /**
     * Запросы для бустера
     */
    @Query("SELECT DISTINCT o.status FROM OrderEntity o WHERE o.booster = :booster")
    List<String> findAllDistinctStatusesByBooster(@Param("booster") UserEntity booster);

    @Query("SELECT DISTINCT o.gamePlatform FROM OrderEntity o WHERE o.booster = :booster")
    List<String> findAllDistinctGamePlatformsByBooster(@Param("booster") UserEntity booster);

    @Query("SELECT DISTINCT o.gameName FROM OrderEntity o WHERE o.booster = :booster")
    List<String> findAllDistinctGameNamesByBooster(@Param("booster") UserEntity booster);

    @Query("SELECT MIN(o.totalPrice) FROM OrderEntity o WHERE o.booster = :booster")
    Double findMinPriceByBooster(@Param("booster") UserEntity booster);

    @Query("SELECT MAX(o.totalPrice) FROM OrderEntity o WHERE o.booster = :booster")
    Double findMaxPriceByBooster(@Param("booster") UserEntity booster);

    @Query("select COUNT(*) from OrderEntity o WHERE o.booster = :booster AND o.status = 'IN_PROGRESS'")
    long findCountOrdersInWorkByBooster(@Param("booster") UserEntity booster);
}
