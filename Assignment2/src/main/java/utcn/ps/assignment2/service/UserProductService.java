package utcn.ps.assignment2.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utcn.ps.assignment2.entity.Product;
import utcn.ps.assignment2.entity.User;
import utcn.ps.assignment2.entity.UserProduct;
import utcn.ps.assignment2.report.Report;
import utcn.ps.assignment2.report.ReportFactory;
import utcn.ps.assignment2.repository.ProductRepository;
import utcn.ps.assignment2.repository.UserProductRepository;
import utcn.ps.assignment2.repository.UserRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class UserProductService{

    private UserProductRepository repository;
    private UserRepository userRepository;
    private ProductRepository productRepository;

    @Autowired
    public UserProductService(UserRepository userRepository, ProductRepository productRepository, UserProductRepository repository){
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.repository = repository;
    }

    public ObservableList<UserProduct> getAll(User user){
        ObservableList<UserProduct> userProducts = FXCollections.observableArrayList();
        userProducts.addAll(repository.findByUser(user));
        return userProducts;
    }

    public ObservableList<UserProduct> getAll(){
        ObservableList<UserProduct> userProducts = FXCollections.observableArrayList();
        userProducts.addAll(repository.findAll());
        return userProducts;
    }

    public Integer addUserProduct(Integer userid, Integer productid){
        User user = userRepository.getOne(userid);
        Product product = productRepository.getOne(productid);
        if(product.getQuantity().equals(0))
            return -1;
        if(user.getBalance_account() < product.getPrice())
            return -2;
        repository.save(new UserProduct(user, product, new Date(System.currentTimeMillis())));
        userRepository.getOne(userid).setBalance_account(Double.parseDouble(String.valueOf(new BigDecimal(String.valueOf(user.getBalance_account() - product.getPrice())).setScale(2, BigDecimal.ROUND_FLOOR))));
        productRepository.getOne(productid).setQuantity(product.getQuantity() - 1);
        return 0;
    }

    public void report(String filePath, String reportType){

        List<User> userList = userRepository.findAll();
        Map<User, Integer> map = new HashMap<>();

        for (User user: userList) {
            map.put(user, repository.findByUser(user).size());
            System.out.println(user + " " + repository.findByUser(user).size());
        }

        Map<User, Integer> result = map.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        ReportFactory reportFactory = new ReportFactory();
        Report report = reportFactory.createReport(reportType);
        report.createReport(filePath, result);
    }

    public int deleteUserProduct(Integer id){

        try {
            repository.deleteByUser(userRepository.getOne(id));
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
