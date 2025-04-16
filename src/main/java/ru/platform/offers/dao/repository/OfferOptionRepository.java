package ru.platform.offers.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.platform.offers.dao.OfferOptionEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface OfferOptionRepository extends JpaRepository<OfferOptionEntity, UUID> {
    List<OfferOptionEntity>findAllByOfferId(UUID offerId);
}
