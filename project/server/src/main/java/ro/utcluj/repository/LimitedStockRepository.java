package ro.utcluj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.utcluj.entity.LimitedStock;

@Repository
public interface LimitedStockRepository extends JpaRepository<LimitedStock, Integer> {

    LimitedStock getOne(Integer integer);
}
