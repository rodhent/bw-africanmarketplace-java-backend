package backend.lambdaschool.services;

import backend.lambdaschool.models.Item;
import backend.lambdaschool.models.User;

import java.util.List;

public interface ItemService {
    List<Item> listAllItems();
    Item save(Item item);
    void deleteAllItems();
    Item findItemById(long id);

    List<Item> findAll();

    void delete(long itemcode);
}

