package ru.platform.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.platform.user.dao.UserProfileEntity;

import java.util.UUID;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileEntity, UUID> {

    @Modifying
    @Query("UPDATE UserProfileEntity p SET p.nickname = :nickname WHERE p.id = :id")
    void updateNickname(@Param("id") UUID id, @Param("nickname") String nickname);

    @Modifying
    @Query("UPDATE UserProfileEntity p SET p.description = :description WHERE p.id = :id")
    void updateDescription(@Param("id") UUID id, @Param("description") String description);
}
