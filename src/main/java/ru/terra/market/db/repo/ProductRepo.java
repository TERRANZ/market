package ru.terra.market.db.repo;

import org.springframework.data.repository.CrudRepository;
import ru.terra.market.db.entity.Product;

public interface ProductRepo extends CrudRepository<Product, Integer> {
}
