package by.viho.feedbackservice.controller;

import by.viho.feedbackservice.controller.payload.NewReviewPayload;
import by.viho.feedbackservice.entity.ItemReview;
import by.viho.feedbackservice.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("feedback-api/")
@RequiredArgsConstructor
public class ItemReviewsRestController
{
    private final ItemService itemService;

    @GetMapping("item-review/{itemId:\\d+}")
    public Flux<ItemReview> findReviewsByItmeId(@PathVariable("itemId") int itemId){
        return itemService.getReviewsByItemId(itemId);
    }

    @PostMapping("create")
    public Mono<ResponseEntity<ItemReview>> createItemReview(@RequestBody Mono<NewReviewPayload> payloadMono, UriComponentsBuilder uriComponentsBuilder){
        return payloadMono.flatMap(payload -> itemService.createItemReview(payload.itemId(), payload.rating(), payload.review()))
                .map(itemReview ->  ResponseEntity
                .created(uriComponentsBuilder.replacePath("/feedback-api/item-reviews/{id}")
                        .build(itemReview.getId()))
                .body(itemReview));
    }

 }
