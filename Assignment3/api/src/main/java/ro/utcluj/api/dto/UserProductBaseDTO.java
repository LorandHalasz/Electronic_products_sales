package ro.utcluj.api.dto;

import java.io.Serializable;
import java.sql.Date;

public class UserProductBaseDTO implements Serializable {

    private Integer id;

    private UserBaseDTO user;
    private ProductBaseDTO product;

    private Date date;

    public UserProductBaseDTO() {
    }

    public UserProductBaseDTO(UserBaseDTO user, ProductBaseDTO product, Date date) {
        this.user = user;
        this.product = product;
        this.date = date;
    }

    public UserProductBaseDTO(Integer id, UserBaseDTO user, ProductBaseDTO product, Date date) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
