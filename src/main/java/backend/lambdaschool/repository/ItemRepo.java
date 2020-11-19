package backend.lambdaschool.repository;

import backend.lambdaschool.models.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepo extends CrudRepository<Item, Long> {
}
