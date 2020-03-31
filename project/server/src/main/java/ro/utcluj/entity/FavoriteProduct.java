package ro.utcluj.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@IdClass(FavoriteProduct.class)
public class FavoriteProduct implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    public FavoriteProduct() {
    }

    public FavoriteProduct(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoriteProduct))
            return false;
        FavoriteProduct that = (FavoriteProduct) o;
        return Objects.equals(user.getName(), that.getUser().getName()) &&
                Objects.equals(product.getName(), that.getProduct().getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.getIduser(), product.getIdproduct());
    }

    @Override
    public String toString() {
        return "FavoriteProduct{" +
                "id=" + id +
                ", user=" + user +
                ", product=" + product +
                '}';
    }
}
