package by.viho.customerapp.controller;

import by.viho.customerapp.client.FavouriteItemsClient;
import by.viho.customerapp.client.ItemsClient;
import by.viho.customerapp.client.ItemsReviewsClient;
import by.viho.customerapp.controller.payload.NewReviewPayload;
import by.viho.customerapp.entity.FavouriteItem;
import by.viho.customerapp.entity.Item;
import by.viho.customerapp.entity.ItemReview;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.http.server.reactive.MockServerHttpResponse;
import org.springframework.ui.ConcurrentModel;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest
{
    @Mock
    ItemsClient itemsClient;
    @Mock
    ItemsReviewsClient itemsReviewsClient;

    @Mock
    FavouriteItemsClient favouriteItemsClient;

    @InjectMocks
    ItemController itemController;

    @Test
    void loadItem_ItemExist_ReturnsNotEmptyMono(){
        //given
        var item = new Item(1, "Fil", "details");
        doReturn(Mono.just(item)).when(this.itemsClient).findItem(1);
        //when
        StepVerifier.create(this.itemController.loadItem(1))
                //then
                .expectNext(new Item(1, "Fil", "details")).verifyComplete();

        verify(this.itemsClient).findItem(1);
        verifyNoMoreInteractions(this.itemsClient);
        verifyNoInteractions(this.favouriteItemsClient, this.itemsReviewsClient);
    }
    @Test
    void deleteItem_ItemRemove_ReturnsMonoString(){
        //given
        var item = new Item(1, "title", "details");
        doReturn(Mono.just("redirect:/customer/item/1")).when(this.favouriteItemsClient).deleteFromFavourites(1);
        ///when
        StepVerifier.create(this.itemController.deleteFavouriteItem(Mono.just(item)))
                //then
                .expectNext("redirect:/customer/item/1").verifyComplete();

        verify(this.favouriteItemsClient).deleteFromFavourites(1);
    }
    @Test
    void createItemReview_RequestIsValid_ReturnsMonoString(){
        //given
        var item = new Item(1, "title", "details");
        var uuid = UUID.randomUUID();
        var model = new ConcurrentModel();
        var response = new MockServerHttpResponse();
        var itemReview = new ItemReview(uuid, 1, 5, "Good");
            doReturn(Mono.just(itemReview)).when(this.itemsReviewsClient).createReview( 1, 5, "Good");
            //when
        StepVerifier.create(this.itemController.createItemReview(1, new NewReviewPayload(1,5, "Good"),model))
                //then
                .expectNext("redirect:/customer/item/1").verifyComplete();



        verify(this.itemsReviewsClient).createReview(1, 5, "Good");
        verifyNoMoreInteractions(this.itemsReviewsClient);
        verifyNoInteractions(this.itemsClient, this.favouriteItemsClient);
    }
    @Test
    void addItemToFavourites_RequestIdValid_ReturnsMonoString(){
        //given
        var item = new Item(1, "title", "details");
        var uuid = UUID.randomUUID();
        var favouriteItem = new FavouriteItem(uuid,1);
        doReturn(Mono.just(favouriteItem)).when(this.favouriteItemsClient).addToFavouritesItems(1);
        //when
        StepVerifier.create(this.itemController.addItemToFavourites(Mono.just(item)))
                //then
                .expectNext("redirect:/customer/item/1").verifyComplete();




    }

}
