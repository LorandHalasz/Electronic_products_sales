package ro.utcluj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.utcluj.entity.User;
import ro.utcluj.entity.UserProduct;

import java.util.List;

@Repository
public interface UserProductRepository extends JpaRepository<UserProduct, Integer> {

    List<UserProduct> findByUser(User user);
    void deleteByUser(User user);
}
