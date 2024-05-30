package by.viho.customerapp.client;

import by.viho.customerapp.entity.Item;
import by.viho.customerapp.entity.ItemReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ItemsReviewsClient
{
    Flux<ItemReview> findReviewsByItemId(int id);

    Mono<ItemReview> createReview(Integer itemID, Integer rating, String review);
}
