package ro.utcluj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.utcluj.entity.FavoriteProduct;
import ro.utcluj.entity.Product;
import ro.utcluj.entity.User;

import java.util.List;

@Repository
public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Integer> {
    List<FavoriteProduct> findByUser(User user);
    void deleteByUserAndProduct(User user, Product product);
}
