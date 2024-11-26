package ru.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.platform.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
