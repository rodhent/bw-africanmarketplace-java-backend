package backend.lambdaschool.controllers;

import backend.lambdaschool.models.Item;
import backend.lambdaschool.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemservice;

    @GetMapping(value = "/items", produces = "application/json")
    public ResponseEntity<?> listAllItems(){
        List<Item> myList = itemservice.findAll();
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }
    @DeleteMapping(value = "/item/{itemcode}")
    public ResponseEntity<?> deleteItemById(@PathVariable long itemcode){
        itemservice.delete(itemcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
