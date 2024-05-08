package by.viho.managerapp.client;

import by.viho.managerapp.controller.payload.NewItemPayload;
import by.viho.managerapp.controller.payload.UpdateItemPayload;
import by.viho.managerapp.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@RequiredArgsConstructor
public class RestClientItemsRestClient implements ItemsRestClient

{
    private static final ParameterizedTypeReference<List<Item>> PRODUCTS_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {
            };

    private final RestClient restClient;
    @Override
    public List<Item> findAllItems()
    {
        return restClient.get().uri("catalogue-api/items/list").retrieve().body(PRODUCTS_TYPE_REFERENCE);
    }

    @Override
    public Item createItem(String title, String details)
    {
        try{
            return restClient.post().uri("catalogue-api/items/create").contentType(MediaType.APPLICATION_JSON).body(new NewItemPayload(title,details)).retrieve().body(Item.class);
        }catch (HttpClientErrorException.BadRequest httpClientErrorException){
            ProblemDetail problemDetail = httpClientErrorException.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<Item> findItem(int itemId)
    {
        try{
            return Optional.ofNullable(restClient.get().uri("catalogue-api/item/{itemId}", itemId).retrieve().body(Item.class));
        }
        catch (HttpClientErrorException.NotFound exception){
            return null;
        }

    }

    @Override
    public void updateItem(int itemId, String title, String details)
    {
        try{
            restClient.patch().uri("catalogue-api/item/{itemId}/edit", itemId ).contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateItemPayload(title,details)).retrieve().toBodilessEntity();
        }
        catch (HttpClientErrorException.BadRequest exception){
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteItem(int itemId)
    {
        try {
            this.restClient
                    .delete()
                    .uri("/catalogue-api/item/{itemId}/delete", itemId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
