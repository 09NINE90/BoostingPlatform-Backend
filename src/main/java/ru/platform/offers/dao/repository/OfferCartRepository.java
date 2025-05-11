package ru.platform.offers.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.platform.offers.dao.OfferCartEntity;
import ru.platform.user.dao.UserEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface OfferCartRepository extends JpaRepository<OfferCartEntity, UUID> {

    List<OfferCartEntity> findAllByCreator(UserEntity creator);
}
