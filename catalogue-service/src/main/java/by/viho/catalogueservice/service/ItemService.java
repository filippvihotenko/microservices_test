package by.viho.catalogueservice.service;

import by.viho.catalogueservice.controller.payload.NewItemPayload;
import by.viho.catalogueservice.controller.payload.UpdateItemPayload;
import by.viho.catalogueservice.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface ItemService
{
    Iterable<Item> findAllItems();

    Item  createItem(Item item);

    Optional<Item> findItem(int id);

    void deleteItem(int id);

    void update(UpdateItemPayload updateItemPayload, int id);
}
