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

    @Query("SELECT DISTINCT o.gameName FROM OrderEntity o WHERE o.creator = :creator")
    List<String> findAllDistinctGameNamesByCreator(UserEntity creator);

    @Query("SELECT MIN(o.totalPrice) FROM OrderEntity o WHERE o.creator = :creator")
    Double findMinPrice(@Param("creator") UserEntity creator);

    @Query("SELECT MAX(o.totalPrice) FROM OrderEntity o WHERE o.creator = :creator")
    Double findMaxPrice(@Param("creator") UserEntity creator);
}
