package by.viho.catalogueservice.controller;

import by.viho.catalogueservice.controller.payload.UpdateItemPayload;
import by.viho.catalogueservice.domain.Item;
import by.viho.catalogueservice.service.ItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Locale;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("catalogue-api/item/{itemId:\\d+}")
public class ItemRestController
{
   private final ItemService itemService;

   private MessageSource messageSource;

   @ModelAttribute("item")
   public Item getItem(@PathVariable("itemId") int itemId){
      return itemService.findItem(itemId).orElseThrow(()->new NoSuchElementException("catalogue.errors.item.not_found"));
   }

   @GetMapping
   public Item findItem(@ModelAttribute("item") Item item){
      return item;
   }
   @PatchMapping("edit")
   public ResponseEntity<Void> editItem(@PathVariable("itemId") int itemId ,@Valid @RequestBody UpdateItemPayload updateItemPayload, BindingResult bindingResult) throws BindException
   {
      if(bindingResult.hasErrors()){
            if(bindingResult instanceof BindException exception)
            {
               throw exception;
            }
            else {
               throw new BindException(bindingResult);
            }
      }else {
         itemService.update(updateItemPayload,itemId);
         return ResponseEntity.noContent().build();
      }
   }

   @DeleteMapping("delete")
   public ResponseEntity<Void> deleteItem(@PathVariable("itemId") int id ){
      this.itemService.deleteItem(id);
      return ResponseEntity.noContent().build();
   }
   @ExceptionHandler(NoSuchElementException.class)
   public ResponseEntity<ProblemDetail> handlerNoSuchElementException(NoSuchElementException exception, Locale locale){
      return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, this.messageSource.getMessage(exception.getMessage(), new Object[0],exception.getMessage(),locale)));
   }

}
