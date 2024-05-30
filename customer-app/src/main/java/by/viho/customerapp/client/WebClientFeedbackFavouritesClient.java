package by.viho.customerapp.client;

import by.viho.customerapp.client.exceptions.ClientBadRequestException;
import by.viho.customerapp.controller.payload.NewFavouriteItemPayload;
import by.viho.customerapp.entity.FavouriteItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class WebClientFeedbackFavouritesClient implements FavouriteItemsClient
{
    private final WebClient webClient;
    @Override
    public Flux<FavouriteItem> getAllFavouriteItems()
    {
        return this.webClient.get().uri("feedback-api/favourite-items/get").retrieve().bodyToFlux(FavouriteItem.class);
    }

    @Override
    public Mono<FavouriteItem> addToFavouritesItems(Integer itemId)
    {
        return this.webClient.post().uri("feedback-api/create").bodyValue(new NewFavouriteItemPayload(itemId))
                .retrieve().bodyToMono(FavouriteItem.class).onErrorMap(WebClientResponseException.BadRequest.class,
            exception -> new ClientBadRequestException(exception,
                    ((List<String>) exception.getResponseBodyAs(ProblemDetail.class)
                            .getProperties().get("errors"))));
    }

    @Override
    public Mono<ResponseEntity> deleteFromFavourites(Integer itemId)
    {
        return this.webClient.delete()
                .uri("feedback-api/favourite-items/delete/{itemId}", itemId).retrieve().bodyToMono(ResponseEntity.class) ;
    }

    @Override
    public Mono<FavouriteItem> findFavouriteItemByItemId(Integer itemId)
    {
        return this.webClient.get()
                .uri("feedback-api/favourite-items/item/{itemId}", itemId)
                .retrieve().bodyToMono(FavouriteItem.class);
    }
}
