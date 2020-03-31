package ro.utcluj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.utcluj.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    void deleteById(Integer id);
}
