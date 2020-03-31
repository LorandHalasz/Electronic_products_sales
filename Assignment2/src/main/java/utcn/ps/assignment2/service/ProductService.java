package utcn.ps.assignment2.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utcn.ps.assignment2.entity.Product;
import utcn.ps.assignment2.entity.User;
import utcn.ps.assignment2.repository.ProductRepository;
import utcn.ps.assignment2.repository.UserRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class ProductService {

    private ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository){ this.repository = repository;}

    public ObservableList<Product> getProducts(){
        ObservableList<Product> products = FXCollections.observableArrayList();
        products.addAll(repository.findAll());
        return products;
    }

    public ObservableList<Product> filterProducts(String name, String description){
        ObservableList<Product> products = FXCollections.observableArrayList();
        if(!name.isEmpty() && !description.isEmpty())
            products.addAll(repository.findAllByNameAndDescription(name, description));
        else
            if(!name.isEmpty())
                products.addAll(repository.findAllByName(name));
            else
                if(!description.isEmpty())
                    products.addAll(repository.findAllByDescription(description));
                else
                    products.addAll(repository.findAll());
        return products;
    }
}
