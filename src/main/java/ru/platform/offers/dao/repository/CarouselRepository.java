package ru.platform.offers.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.platform.offers.dao.CarouselEntity;

import java.util.List;

@Repository
public interface CarouselRepository extends JpaRepository<CarouselEntity, Long> {
    List<CarouselEntity> findAllByIsActive(boolean isActive);
}
