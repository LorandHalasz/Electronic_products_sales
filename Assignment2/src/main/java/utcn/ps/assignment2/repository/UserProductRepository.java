package utcn.ps.assignment2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utcn.ps.assignment2.entity.Product;
import utcn.ps.assignment2.entity.User;
import utcn.ps.assignment2.entity.UserProduct;

import java.util.List;

@Repository
public interface UserProductRepository extends JpaRepository<UserProduct, Integer> {
    List<UserProduct> findByUser(User user);
    void deleteByUser(User user);
}
