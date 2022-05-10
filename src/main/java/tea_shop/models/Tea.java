package tea_shop.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tea")
@Getter
@Setter
public class Tea {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "sort")
    String sort;
    @Column(name = "name")
    String name;
    @Column(name = "image")
    String image;
    @Column(name = "weight")
    int weight;
    @Column(name = "price")
    int price;
    @Column(name = "description")
    String description;
    @Column(name = "quantity")
    int quantity;
    @ManyToMany(mappedBy = "teas")
    private Set<User> baskets=new HashSet<>();
}
