package ru.platform.offers.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.platform.offers.dao.OptionItemEntity;

import java.util.UUID;

@Repository
public interface OptionItemRepository extends JpaRepository<OptionItemEntity, UUID> {
}
