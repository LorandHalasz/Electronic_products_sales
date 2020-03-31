package ro.utcluj.api.dto;

import java.io.Serializable;

public class FavoriteProductBaseDTO implements Serializable {

    private Integer id;

    private UserBaseDTO user;
    private ProductBaseDTO product;

    public FavoriteProductBaseDTO() {
    }

    public FavoriteProductBaseDTO(UserBaseDTO user, ProductBaseDTO product) {
        this.user = user;
        this.product = product;
    }

    public FavoriteProductBaseDTO(Integer id, UserBaseDTO user, ProductBaseDTO product) {
        this.id = id;
        this.user = user;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public UserBaseDTO getUser() {
        return user;
    }

    public void setUser(UserBaseDTO user) {
        this.user = user;
    }

    public ProductBaseDTO getProduct() {
        return product;
    }

    public void setProduct(ProductBaseDTO product) {
        this.product = product;
    }

}
