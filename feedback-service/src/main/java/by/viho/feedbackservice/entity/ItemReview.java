package by.viho.feedbackservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "itemReview")
public class ItemReview
{
    @Id
    private UUID id;
    private int itemId;
    private int rating;
    private String review;
}
