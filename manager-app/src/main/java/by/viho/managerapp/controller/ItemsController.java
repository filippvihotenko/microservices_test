
package by.viho.managerapp.controller;

import by.viho.managerapp.client.BadRequestException;
import by.viho.managerapp.client.ItemsRestClient;
import by.viho.managerapp.client.RestClientItemsRestClient;
import by.viho.managerapp.controller.payload.NewItemPayload;
import by.viho.managerapp.domain.Item;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
    public String getItemsList(Model model){
        model.addAttribute("items", restClient.findAllItems());
        return "catalogue/items/list";
    }

    @GetMapping("create")
    public String createItem(){
        return "catalogue/items/newItem";
    }
    @PostMapping("create")
    public String postCreateItem(NewItemPayload payload,
                                 Model model, HttpServletResponse response) {

        try{
            Item item = this.restClient.createItem(payload.title(), payload.details());
            return "redirect:/catalogue/item/%d".formatted(item.id());
        }
        catch (BadRequestException exception){
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "catalogue/items/newItem";
        }
    }
}


