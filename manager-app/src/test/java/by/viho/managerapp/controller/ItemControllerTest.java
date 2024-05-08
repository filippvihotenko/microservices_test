package by.viho.managerapp.controller;

import by.viho.managerapp.client.BadRequestException;
import by.viho.managerapp.client.ItemsRestClient;
import by.viho.managerapp.controller.payload.UpdateItemPayload;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest
{
    @Mock
    ItemsRestClient itemsRestClient;
    @InjectMocks
    ItemController itemController;

    @Test
    void findItemById_returnOptional(){
        //given
        var item = new Item(1, "Some title", "Some details");
        doReturn(Optional.of(item)).when(itemsRestClient).findItem(1);
        //when
        var result = itemController.item(1);
        //then
        assertEquals(item, result);

        Mockito.verify(this.itemsRestClient).findItem(1);
        Mockito.verifyNoMoreInteractions(this.itemsRestClient);
    }
    @Test
    void findItemByIdHtml_returnView(){
        //give
        var view = "catalogue/items/item";
        //when
        var result = itemController.getItem();
        //then
        assertEquals(view, result);
    }
    @Test
    void editItemByIdHtml_returnView(){
        //give
        var view = "catalogue/items/edit";
        //when
        var result = itemController.updateItem();
        //then
        assertEquals(view, result);
    }


    @Test
    void  postEditItemById_return(){
        //given
        var model = new ConcurrentModel();
        var payload = new UpdateItemPayload("Some title", "Some details");
        var item = new Item(1, "Some title", "Some details");
        var response = new MockHttpServletResponse();
        //when
        var result = itemController.updateItemPost(item, payload, model, response);
        //then
        assertEquals("redirect:/catalogue/item/1", result);

        verify(this.itemsRestClient).updateItem(1, "Some title", "Some details");
        verifyNoMoreInteractions(this.itemsRestClient);

    }

    @Test
    void  exceptionEdit_returnsException(){
        //given
        var response = new MockHttpServletResponse();
        var model = new ConcurrentModel();
        var payload = new UpdateItemPayload("   ", null);
        var item = new Item(1, "   ", null);

        doThrow(new BadRequestException(List.of("Ошибка 1", "Ошибка 2"))).when(this.itemsRestClient).updateItem(1, "   ", null);
        //when
        var result = this.itemController.updateItemPost(item, payload, model, response);
        //then
        assertEquals("catalogue/items/edit", result);
        assertEquals(payload, model.getAttribute("payload"));
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        verify(this.itemsRestClient).updateItem(1, "   ", null);
        verifyNoMoreInteractions(this.itemsRestClient);
    }

    @Test
    void deleteById_returnListPage(){
        //given
        var id = 1;
        //when
        var result = itemController.deleteItem(id);
        //then
        assertEquals("redirect:/catalogue/items/list", result);
    }

}
