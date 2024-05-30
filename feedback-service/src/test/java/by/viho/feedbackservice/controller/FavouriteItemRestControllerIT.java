package by.viho.feedbackservice.controller;

import by.viho.feedbackservice.controller.payload.NewFavouriteItemPayload;
import by.viho.feedbackservice.entity.FavouriteItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureWebTestClient
public class FavouriteItemRestControllerIT
{

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    @BeforeEach
    void setUp (){
        this.reactiveMongoTemplate.insertAll(List.of(
                new FavouriteItem(UUID.fromString("fe87eef6-cbd7-11ee-aeb6-275dac91de02"), 1),
                new FavouriteItem(UUID.fromString("37b79df0-cbda-11ee-b5d0-17231cdeab05"), 2),
                new FavouriteItem(UUID.fromString("23ff1d58-cbd8-11ee-9f4f-ef497a4e4799"), 3)
        )).blockLast();
    }
    @AfterEach
    void tearDown(){
        this.reactiveMongoTemplate.remove(FavouriteItem.class).all().block();
    }

    @Test
    void  findFavouriteItemByItemId_ReturnsFavouriteItem(){
        //given
        //then
        this.webTestClient.get().uri("/feedback-api/favourite-items/item/1").exchange().expectStatus().isOk()
                //when
                .expectBody().json("""
                        {
                            "uuid": "fe87eef6-cbd7-11ee-aeb6-275dac91de02",
                            "itemId": 1
                        }""");
    }

    @Test
    void  deleteFromFavourites_Returns(){
        //given
        //when
        this.webTestClient.delete().uri("/feedback-api/favourite-items/1/delete").exchange()
                //then
                .expectStatus().isOk().expectBody().isEmpty();
    }
    @Test
    void  findAllFavouritesItems_RequestIdValid(){
        //given
        var data =  List.of(
                new FavouriteItem(UUID.fromString("fe87eef6-cbd7-11ee-aeb6-275dac91de02"), 1),
                new FavouriteItem(UUID.fromString("37b79df0-cbda-11ee-b5d0-17231cdeab05"), 2),
                new FavouriteItem(UUID.fromString("23ff1d58-cbd8-11ee-9f4f-ef497a4e4799"), 3)
        );
        //when
        this.webTestClient.get().uri("/feedback-api/favourite-items/get").exchange()
                //then
                .expectBody().json("""
                        [
                            {
                                "uuid": "fe87eef6-cbd7-11ee-aeb6-275dac91de02",
                                "itemId": 1
                            },
                            {
                                "uuid": "37b79df0-cbda-11ee-b5d0-17231cdeab05",
                                "itemId": 2
                            },
                            {
                                "uuid": "23ff1d58-cbd8-11ee-9f4f-ef497a4e4799",
                                "itemId": 3
                            }
                        ]""");
    }
    @Test
    void addFavouriteProduct_RequestIsValid_ReturnsCreatedFavouriteProduct(){
        //given
        //when
        this.webTestClient.post().uri("/feedback-api/favourite-items/add-to-favourites").contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "itemId": 4
                        }""").exchange()
                .expectStatus().isCreated();
                //then

    }
}

