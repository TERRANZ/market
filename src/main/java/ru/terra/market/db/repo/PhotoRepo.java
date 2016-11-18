package ru.terra.market.db.repo;

import org.springframework.data.repository.CrudRepository;
import ru.terra.market.db.entity.Photo;

public interface PhotoRepo extends CrudRepository<Photo, Integer> {
}
