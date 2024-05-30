package by.viho.feedbackservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteItem
{
    private UUID uuid;
    private int itemId;
}
