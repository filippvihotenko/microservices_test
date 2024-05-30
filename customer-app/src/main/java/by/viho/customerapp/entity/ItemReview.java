package by.viho.customerapp.entity;

import java.util.UUID;

public record ItemReview(UUID id, int itemId,int rating,String review)
{
}
