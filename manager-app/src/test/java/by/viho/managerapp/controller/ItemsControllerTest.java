
package by.viho.managerapp.controller;

import by.viho.managerapp.client.BadRequestException;
import by.viho.managerapp.client.ItemsRestClient;
import by.viho.managerapp.controller.payload.NewItemPayload;
import by.viho.managerapp.domain.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.ConcurrentModel;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemsControllerTest
{
    @Mock
    private ItemsRestClient restClient;
    @InjectMocks
    private ItemsController itemsController;

    @Test
    void create_RequestIsValid_ReturnsRedirectionToItemsPage(){
        //given
        var payload = new NewItemPayload("Новый товар", "Описание нового товара");
        var model = new ConcurrentModel();
        var response = new MockHttpServletResponse();
        BindingResult bindingResult = new BeanPropertyBindingResult(payload, "payload");
        doReturn(new Item(1,"Новый товар", "Описание нового товара")).when(this.restClient).createItem("Новый товар","Описание нового товара");
        //when

        var result =  itemsController.postCreateItem(payload, model,  response);
        //then
        assertEquals("redirect:/catalogue/item/1", result);
        Mockito.verify(this.restClient).createItem("Новый товар", "Описание нового товара");
        Mockito.verifyNoMoreInteractions(this.restClient);
    }

    @Test
    void create_RequestIsBad_ThrowsException(){
        //given
        var payload = new NewItemPayload("  ", null);
        var model = new ConcurrentModel();
        var response =  new MockHttpServletResponse();
        doThrow(new BadRequestException(List.of("Ошибка 1", "Ошибка 2"))).when(this.restClient).createItem("  ", null);
        //when
        var result = itemsController.postCreateItem(payload, model, response);

        //then
        assertEquals("catalogue/items/newItem", result);
        assertEquals(payload, model.getAttribute("payload"));
        assertEquals(List.of("Ошибка 1", "Ошибка 2"), model.getAttribute("errors"));
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        verify(this.restClient).createItem("  ", null);
        verifyNoMoreInteractions(this.restClient);
    }
    @Test
    void getItemsList_ReturnsItemsList(){

        //given
        var model = new ConcurrentModel();
        var items =
                IntStream.range(1, 4)
                .mapToObj(i -> new Item(i, "Товар №%d".formatted(i),
                        "Описание товара №%d".formatted(i)))
                .toList();


        when(restClient.findAllItems()).thenReturn(items);
        //when
        var result = itemsController.getItemsList(model);
        //then
        assertEquals("catalogue/items/list", result);
        assertEquals(items, model.getAttribute("items"));
    }
}