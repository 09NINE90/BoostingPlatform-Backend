package ru.platform.user.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.platform.user.dao.UserEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @EntityGraph(attributePaths = {
            "profile",
            "boosterProfile",
            "customerProfile",
            "referredUsers",
            "referredBy"
    })
    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);
}
