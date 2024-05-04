
package by.viho.managerapp.controller;

import by.viho.managerapp.client.ItemsRestClient;
import by.viho.managerapp.client.RestClientItemsRestClient;
import by.viho.managerapp.controller.payload.NewItemPayload;
import by.viho.managerapp.domain.Item;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/catalogue/items")
public class ItemsController
{
    private final ItemsRestClient restClient;

    @GetMapping("list")
    public String getItemsList(Model model, Principal principal){
        LoggerFactory.getLogger(ItemsController.class).info("User: {}", principal);
        model.addAttribute("items", restClient.findAllItems());
        return "catalogue/items/list";
    }

    @GetMapping("create")
    public String createItem(){
        return "catalogue/items/newItem";
    }
    @PostMapping("create")
    public String createProduct(NewItemPayload payload,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return "catalogue/items/newItem";
        } else {
            this.restClient.createItem(payload.title(), payload.details());
            return "redirect:/catalogue/items/list";
        }
    }

}

