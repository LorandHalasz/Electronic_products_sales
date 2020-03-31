package utcn.ps.assignment2.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Entity
@IdClass(UserProduct.class)
public class UserProduct implements Serializable {

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

    private Date date;

    public UserProduct() {
    }

    public UserProduct(User user, Product product, Date date) {
        this.user = user;
        this.product = product;
        this.date = date;
    }

    public Integer getId() {
        return id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProduct))
            return false;
        UserProduct that = (UserProduct) o;
        return Objects.equals(user.getName(), that.user.getName()) &&
                Objects.equals(product.getName(), that.product.getName()) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.getIduser(), product.getIdproduct(), date);
    }
}
