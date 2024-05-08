package by.viho.catalogueservice.controller;

import by.viho.catalogueservice.domain.Item;
import by.viho.catalogueservice.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("catalogue-api/items")
public class ItemsRestController{
    private final ItemService itemService;

    @GetMapping("/list")
    public Iterable<Item> getList(){
        return itemService.findAllItems();
    }

    @PostMapping("create")
    public ResponseEntity<?> createItem(@Valid @RequestBody Item item, BindingResult bindingResult, UriComponentsBuilder uriComponentsBuilder) throws BindException
    {
        if(bindingResult.hasErrors()){
            if (bindingResult instanceof BindException e){
                throw e;
            }
            else{
                throw new BindException(bindingResult);
            }
        }
        else{
            Item createdItem =  itemService.createItem(item);
            return ResponseEntity.created(uriComponentsBuilder.replacePath("/catalogue-api/item/{itemId}").build(Map.of("itemId", item.getId()))).body(createdItem);
        }
    }

}
