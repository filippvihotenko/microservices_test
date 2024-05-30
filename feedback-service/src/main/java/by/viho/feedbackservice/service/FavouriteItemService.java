package by.viho.feedbackservice.service;

import by.viho.feedbackservice.controller.payload.NewFavouriteItemPayload;
import by.viho.feedbackservice.entity.FavouriteItem;
import by.viho.feedbackservice.repo.FavouriteItemRepository;
import by.viho.feedbackservice.repo.ItemReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavouriteItemService
{
    private final FavouriteItemRepository favouriteItemRepository;

    public Flux<FavouriteItem> getAllFavouriteItems(){
        return favouriteItemRepository.findAll();
    }
    public Mono<FavouriteItem> addFavouriteItem(int itemId){
        return favouriteItemRepository.save(new FavouriteItem(UUID.randomUUID(), itemId));
    }
    public Mono<Void> deleteFromFavourites(int itemId){
        return Mono.empty();
    }
    public Mono<FavouriteItem> getFavouriteItemByItemId(Integer itemId){
        return favouriteItemRepository.getFavouriteItemByItemId(itemId);
    }

}
