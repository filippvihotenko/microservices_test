package by.viho.catalogueservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "catalogue",name = "t_item")
public class Item
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "c_title")
    private String title;
    @Column(name = "c_details")
    private String details;

    public Item(String title, String details)
    {
        this.title = title;
        this.details = details;
    }
}
