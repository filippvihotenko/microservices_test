package by.viho.customerapp.controller;

import by.viho.customerapp.client.FavouriteItemsClient;
import by.viho.customerapp.client.ItemsClient;
import by.viho.customerapp.client.ItemsReviewsClient;
import by.viho.customerapp.client.exceptions.ClientBadRequestException;
import by.viho.customerapp.controller.payload.NewReviewPayload;
import by.viho.customerapp.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("customer/item/{itemId:\\d+}")
public class ItemController
{
    private final ItemsClient itemsClient;

    private final ItemsReviewsClient itemsReviewsClient;

    private final FavouriteItemsClient favouriteItemsClient;
    @ModelAttribute(name = "item", binding = false)
    public Mono<Item> loadItem(@PathVariable("itemId") int id){
        return this.itemsClient.findItem(id);
    }

    @GetMapping
    public Mono<String> getProductPage(@PathVariable("itemId") int id, Model model) {
        model.addAttribute("inFavourite", false);
        return this.itemsReviewsClient.findReviewsByItemId(id)
                .collectList()
                .doOnNext(productReviews -> model.addAttribute("reviews", productReviews))
                .then(this.favouriteItemsClient.findFavouriteItemByItemId(id)
                        .doOnNext(favouriteProduct -> model.addAttribute("inFavourite", true)))
                .thenReturn("customer/items/item");
    }
    @PostMapping("add-to-favourites")
    public Mono<String> addItemToFavourites(@ModelAttribute("item") Mono<Item> itemMono)
    {
        return itemMono.map(Item::id)
                .flatMap(itemId -> this.favouriteItemsClient.addToFavouritesItems(itemId)
                        .thenReturn("redirect:/customer/item/%d".formatted(itemId)));
    }

    @PostMapping("remove-from-favourites")
    public  Mono<String> deleteFavouriteItem(@ModelAttribute("item") Mono<Item> itemMono)
    {
        return itemMono.map(Item::id).flatMap(itemId-> this.favouriteItemsClient.deleteFromFavourites(itemId).thenReturn("redirect:/customer/item/%d".formatted(itemId)));
    }
    @PostMapping("create-review")
    public  Mono<String> createItemReview(@PathVariable("itemId") int id, NewReviewPayload newReviewPayload, Model model){
        return this.itemsReviewsClient.createReview(id, newReviewPayload.rating(), newReviewPayload.review()).thenReturn("redirect:/customer/item/%d".formatted(id))
                .onErrorResume(ClientBadRequestException.class, exception -> {
                    model.addAttribute("inFavourite", false);
                    model.addAttribute("payload", newReviewPayload);
                    model.addAttribute("errors", exception.getErrors());
                    return this.favouriteItemsClient.findFavouriteItemByItemId(id)
                            .doOnNext(favouriteProduct -> model.addAttribute("inFavourite", true))
                            .thenReturn("customer/items/list");
                });
    }
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model){
        model.addAttribute("errors", exception.getMessage());
        return "errors/404";
    }
}

