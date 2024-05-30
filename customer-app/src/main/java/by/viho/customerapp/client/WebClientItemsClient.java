package by.viho.customerapp.client;

import by.viho.customerapp.entity.Item;
import by.viho.customerapp.entity.ItemReview;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class WebClientItemsClient implements ItemsClient
{
    private final WebClient webClient;

    @Override
    public Flux<Item> findAllItems()
    {
        return this.webClient.get().uri("/catalogue-api/items/list").retrieve().bodyToFlux(Item.class);
    }

    @Override
    public Mono<Item> findItem(int id)
    {
        return this.webClient.get().uri("catalogue-api/item/{itemid}", id).retrieve().bodyToMono(Item.class);
    }

}
