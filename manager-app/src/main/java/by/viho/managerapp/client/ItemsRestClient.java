package by.viho.managerapp.client;

import by.viho.managerapp.domain.Item;

import java.util.List;
import java.util.Optional;

public interface ItemsRestClient
{
    List<Item> findAllItems();

    Item createItem(String title, String details);

    Optional<Item> findItem(int productId);

    void updateItem(int productId, String title, String details);

    void deleteItem(int productId);
}
