package ru.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.platform.entity.options_entity.OptionEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface OptionRepository extends JpaRepository<OptionEntity, Long> {

    List<OptionEntity> findAllByServiceId(UUID serviceId);
}
