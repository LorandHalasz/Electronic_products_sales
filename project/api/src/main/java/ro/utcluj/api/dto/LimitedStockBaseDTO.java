package ro.utcluj.api.dto;

import java.io.Serializable;

public class LimitedStockBaseDTO implements Serializable {

    private Integer id;
    private Integer stock;
    private Integer discount;

    public LimitedStockBaseDTO() {
    }

    public LimitedStockBaseDTO(Integer id, Integer stock, Integer discount) {
        this.id = id;
        this.stock = stock;
        this.discount = discount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "LimitedStock{" +
                "id=" + id +
                ", stock=" + stock +
                ", discount=" + discount +
                '}';
    }
}
