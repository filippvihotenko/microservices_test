
package by.viho.customerapp.controller;

import by.viho.customerapp.client.ItemsClient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("customer/items")
public class ItemsController
{
    private final  ItemsClient itemsClient;

    @GetMapping("list")
    public Mono<String> getItemsListPage(Model model){
        return  this.itemsClient.findAllItems().collectList()
                .doOnNext(items -> model.addAttribute("items", items)).thenReturn("customer/items/list");
    }

}