package by.viho.catalogueservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull
    @Size(min = 3, max = 100)
    private String title;

    @Column(name = "c_details")
    @NotNull
    @Size(max = 1000)
    private String details;

    public Item(String title, String details)
    {
        this.title = title;
        this.details = details;
    }
}
