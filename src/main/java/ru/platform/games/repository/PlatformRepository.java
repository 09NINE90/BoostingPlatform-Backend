package ru.platform.games.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.platform.games.dao.PlatformEntity;
import ru.platform.games.enumz.GamePlatform;

import java.util.Optional;

@Repository
public interface PlatformRepository extends JpaRepository<PlatformEntity, Long> {
    Optional<PlatformEntity> findByTitle(GamePlatform title);
}
