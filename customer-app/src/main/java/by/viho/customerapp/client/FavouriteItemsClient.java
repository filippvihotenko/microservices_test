package by.viho.customerapp.client;

import by.viho.customerapp.entity.FavouriteItem;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouriteItemsClient
{
    Flux<FavouriteItem> getAllFavouriteItems();

    Mono<FavouriteItem> addToFavouritesItems(Integer itemId);
    Mono<ResponseEntity> deleteFromFavourites(Integer itemId);

    Mono<FavouriteItem> findFavouriteItemByItemId(Integer itemId);
  }
