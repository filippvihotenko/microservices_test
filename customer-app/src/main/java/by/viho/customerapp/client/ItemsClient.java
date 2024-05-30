package by.viho.customerapp.client;

import by.viho.customerapp.entity.FavouriteItem;
import by.viho.customerapp.entity.Item;
import by.viho.customerapp.entity.ItemReview;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ItemsClient
{
    Flux<Item> findAllItems();

    Mono<Item> findItem(int id);



}
