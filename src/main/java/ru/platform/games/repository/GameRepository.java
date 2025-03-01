package ru.platform.games.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.platform.games.dao.GameEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, UUID> {

    List<GameEntity> findAllByOrderByRatingDesc();
}
