package ru.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.platform.entity.OptionsEntity;

import java.util.UUID;

@Repository
public interface OptionsRepository extends JpaRepository<OptionsEntity, UUID> {
}
