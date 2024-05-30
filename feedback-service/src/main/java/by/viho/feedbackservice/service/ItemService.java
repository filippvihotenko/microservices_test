package by.viho.feedbackservice.service;

import by.viho.feedbackservice.entity.ItemReview;
import by.viho.feedbackservice.repo.ItemReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService
{
    private final ItemReviewRepository itemReviewRepository;

    public Mono<ItemReview> createItemReview(int itemId, int rating, String review ){
        return this.itemReviewRepository.save(new ItemReview(UUID.randomUUID(),itemId, rating,review));
    }
    public Flux<ItemReview> getReviewsByItemId(int id){
        return this.itemReviewRepository.findAllByItemId(id);
    }
}
