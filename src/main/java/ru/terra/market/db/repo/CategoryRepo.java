package ru.terra.market.db.repo;

import org.springframework.data.repository.CrudRepository;
import ru.terra.market.db.entity.Category;

public interface CategoryRepo extends CrudRepository<Category, Integer> {
}
