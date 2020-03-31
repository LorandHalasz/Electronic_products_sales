package ro.utcluj.api.dto;

import java.io.Serializable;

public class ProductBaseDTO implements Serializable {

    private Integer idproduct;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private Double rating;

    public ProductBaseDTO(){

    }

    public ProductBaseDTO(String name, String description, Double price, Integer quantity, Double rating) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.rating = rating;
    }

    public ProductBaseDTO(Integer idproduct, String name, String description, Double price, Integer quantity, Double rating) {
        this.idproduct = idproduct;
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
