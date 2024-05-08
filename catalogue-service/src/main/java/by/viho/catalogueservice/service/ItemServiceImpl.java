package by.viho.catalogueservice.service;

import by.viho.catalogueservice.controller.payload.NewItemPayload;
import by.viho.catalogueservice.controller.payload.UpdateItemPayload;

import by.viho.catalogueservice.domain.Item;
import by.viho.catalogueservice.repo.ItemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ItemServiceImpl implements ItemService
{
    private final ItemRepo itemRepo;

    @Override
    public Iterable<Item> findAllItems()
    {
        return itemRepo.findAll();
    }

    @Override
    @Transactional
    public Item createItem(Item item)
    {
         return this.itemRepo.save(item);
    }

    @Override
    public Optional<Item> findItem(int id)
    {
        return itemRepo.findById(id);
    }

    @Override
    @Transactional
    public void deleteItem(int id)
    {
        itemRepo.deleteById(id);
    }


    @Override
    @Transactional
    public void  update(UpdateItemPayload updateItemPayload, int id)
    {
          this.itemRepo.findById(id)
                .ifPresentOrElse(item -> {
                    item.setTitle(updateItemPayload.title());
                    item.setDetails(updateItemPayload.details());
                }, () -> {
                    throw new NoSuchElementException();
                });
    }
}
