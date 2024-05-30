package by.viho.customerapp.client;

import by.viho.customerapp.client.exceptions.ClientBadRequestException;
import by.viho.customerapp.controller.payload.NewReviewPayload;
import by.viho.customerapp.entity.ItemReview;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class WebCLientFeedbackServiceReviewsClient implements ItemsReviewsClient
{
    private final WebClient  webClient;
    @Override
    public Flux<ItemReview> findReviewsByItemId(int id)
    {
        return this.webClient.get().uri("feedback-api/item-review/{itemId}", id).retrieve().bodyToFlux(ItemReview.class);
    }

    @Override
    public Mono<ItemReview> createReview(Integer itemId, Integer rating, String review)
    {
        return this.webClient.post().uri("feedback-api/create").bodyValue(new NewReviewPayload(itemId,rating,review))
                .retrieve().bodyToMono(ItemReview.class).onErrorMap(WebClientResponseException.BadRequest.class,
                exception -> new ClientBadRequestException(exception,
                        ((List<String>) exception.getResponseBodyAs(ProblemDetail.class)
                                .getProperties().get("errors"))));
    }


}
