package ru.platform.games.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.platform.games.dao.GameEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, UUID> {

    @Query("SELECT g FROM GameEntity g WHERE g.isActive = true ORDER BY g.rating DESC")
    List<GameEntity> findAllByIsActiveByOrderByRatingDesc();

    Optional<GameEntity> findBySecondId(String secondId);
}
