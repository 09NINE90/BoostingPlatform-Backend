package ru.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.platform.entity.BaseOrdersEntity;

import java.util.UUID;

@Repository
public interface BaseOrdersRepository extends JpaRepository<BaseOrdersEntity, UUID>, JpaSpecificationExecutor<BaseOrdersEntity> {
}
