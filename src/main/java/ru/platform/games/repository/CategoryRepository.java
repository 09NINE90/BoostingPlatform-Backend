package ru.platform.games.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.platform.games.dao.CategoryEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query("SELECT o FROM CategoryEntity o WHERE o.game.id = :gameId")
    List<CategoryEntity> findAllByGameId(@Param("gameId") UUID gameId);

}
