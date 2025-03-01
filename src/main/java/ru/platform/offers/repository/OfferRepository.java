package ru.platform.offers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.platform.offers.dao.OfferEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, UUID>, JpaSpecificationExecutor<OfferEntity> {

    @Query("SELECT o FROM OfferEntity o WHERE o.game.id = :gameId")
    List<OfferEntity> findAllByGameId(@Param("gameId") UUID gameId);
}