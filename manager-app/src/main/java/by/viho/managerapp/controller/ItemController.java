
package by.viho.managerapp.controller;


import by.viho.managerapp.client.BadRequestException;
import by.viho.managerapp.client.ItemsRestClient;
import by.viho.managerapp.client.RestClientItemsRestClient;
import by.viho.managerapp.controller.payload.UpdateItemPayload;
import by.viho.managerapp.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequestMapping(("catalogue/item/{itemId:\\d+}"))
@RequiredArgsConstructor
public class ItemController
{
    private final ItemsRestClient restClient;


@ModelAttribute("item")
    public Item item(@PathVariable("itemId") int itemId){
        return restClient.findItem(itemId).orElseThrow(() -> new NoSuchElementException("catalogue.errors.product.not_found"));
    }


    @GetMapping
    public  String getItem(){
        return "catalogue/items/item";
    }

    @GetMapping("edit")
    public  String updateItem(){
        return "catalogue/items/edit";
    }

    @PostMapping("edit")
    public String updateItemPost(@ModelAttribute(name = "item") Item item  , UpdateItemPayload payload, Model model){
        try {
            restClient.updateItem(item.id(), payload.title(),payload.details());
            return "redirect:/catalogue/items/list";
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            return "catalogue/items/edit";
        }

    }

    @PostMapping("delete")
    public String deleteItem(@PathVariable("itemId") int id)
    {
        restClient.deleteItem(id);
        return "redirect:/catalogue/items/list";
    }
}

