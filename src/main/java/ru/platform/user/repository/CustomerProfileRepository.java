package ru.platform.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.platform.user.dao.CustomerProfileEntity;

import java.util.UUID;

@Repository
public interface CustomerProfileRepository extends JpaRepository<CustomerProfileEntity, UUID> {
}
