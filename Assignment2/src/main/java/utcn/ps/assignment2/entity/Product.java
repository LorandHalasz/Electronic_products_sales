package utcn.ps.assignment2.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idproduct;

    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private Double rating;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<UserProduct> userProducts = new HashSet<>();;

    public Product(){

    }

    public Product(String name, String description, Double price, Integer quantity, Double rating) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.rating = rating;
    }

    public Integer getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(Integer id_roduct) {
        this.idproduct = idproduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Set<UserProduct> getUserProducts() {
        return userProducts;
    }

    public void setUserProducts(Set<UserProduct> userProducts) {
        this.userProducts = userProducts;
    }

    @Override
    public String toString() {
        return "Product{" +
                "idproduct=" + idproduct +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", rating=" + rating +
                '}';
    }
}
