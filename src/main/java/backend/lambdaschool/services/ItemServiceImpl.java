package backend.lambdaschool.services;


import backend.lambdaschool.models.Item;
import backend.lambdaschool.models.User;
import backend.lambdaschool.repository.ItemRepo;
import backend.lambdaschool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "itemservices")
public class ItemServiceImpl implements ItemService{
    @Autowired
    ItemRepo itemrepo;
    @Autowired
    UserRepository userrepo;


    @Override
    public Item findItemById(long id) throws EntityNotFoundException {
        return itemrepo.findById(id)
                .orElseThrow(()->new EntityNotFoundException("item "+ id ));
    }

    @Override
    public List<Item> listAllItems() {
        List<Item> list = new ArrayList<>();
        itemrepo.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public List<Item> findAll() {
        List<Item> list = new ArrayList<>();
        itemrepo.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public void deleteAllItems() {

        itemrepo.deleteAll();
    }

    @Transactional
    @Override
    public Item save(Item item) {

        Item newItem = new Item();

        if(item.getItemcode() !=0){
            findItemById(item.getItemcode());
            newItem.setItemcode(item.getItemcode());
        }

        newItem.setDescription(item.getDescription());
        newItem.setItemcost(item.getItemcost());
        newItem.setLocation(item.getLocation());
        newItem.setName(item.getName());
        newItem.setType(item.getType());
        newItem.setUser(userrepo.findByUsername(item.getUser().getUsername()));



       //saving
        return itemrepo.save(newItem);

    }
    @Transactional
    @Override
    public void delete(long itemcode) {
        if(itemrepo.findById(itemcode).isPresent()){
            itemrepo.deleteById(itemcode);
        }
        else{
            throw new EntityNotFoundException("Item "+ itemcode + " Not Found");
        }
    }
}
