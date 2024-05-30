package by.viho.feedbackservice.controller;

import by.viho.feedbackservice.controller.payload.NewFavouriteItemPayload;
import by.viho.feedbackservice.entity.FavouriteItem;
import by.viho.feedbackservice.service.FavouriteItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("feedback-api/favourite-items")
public class FavouriteItemRestController
{
    private final FavouriteItemService favouriteItemService;
    @GetMapping("get")
    public Flux<FavouriteItem> getAllFavouriteItems(){
        return favouriteItemService.getAllFavouriteItems();
    }
    @PostMapping("add-to-favourites")
    public Mono<ResponseEntity<FavouriteItem>> addToFavouritesItems(@RequestBody Mono<NewFavouriteItemPayload> payloadMonoayloadMono,
                                                                    UriComponentsBuilder uriComponentsBuilder){
        return payloadMonoayloadMono.flatMap(payload-> favouriteItemService.addFavouriteItem(payload.itemId()))
                .map(payload -> ResponseEntity.created(uriComponentsBuilder.replacePath("/feedback-api/favourite-items")
                                .build().toUri())
                        .body(payload));

    }

    @GetMapping("item/{itemId:\\d+}")
    public Mono<ResponseEntity<FavouriteItem>> getFavouriteItem(@PathVariable("itemId") int id){
        return favouriteItemService.getFavouriteItemByItemId(id).map(ResponseEntity::ok);
    }



    @DeleteMapping("{itemId}/delete")
    public Mono<ResponseEntity<?>> deleteFromFavourites(@PathVariable ("itemId") int itemId){
        favouriteItemService.deleteFromFavourites(itemId);
        return Mono.just(ResponseEntity.ok().build());
    }

}
