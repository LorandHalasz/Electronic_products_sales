package utcn.ps.assignment2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utcn.ps.assignment2.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByName(String name);
    List<Product> findAllByDescription(String description);
    List<Product> findAllByNameAndDescription(String name, String description);
    Product findByIdproduct(Integer id);

}
