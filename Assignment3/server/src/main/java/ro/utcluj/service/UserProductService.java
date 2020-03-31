package ro.utcluj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.utcluj.api.dto.UserBaseDTO;
import ro.utcluj.api.dto.UserProductBaseDTO;
import ro.utcluj.api.serviceInterface.UserProductServiceInterface;
import ro.utcluj.entity.FavoriteProduct;
import ro.utcluj.entity.Product;
import ro.utcluj.entity.User;
import ro.utcluj.entity.UserProduct;
import ro.utcluj.mapper.UserMapper;
import ro.utcluj.mapper.UserProductMapper;
import ro.utcluj.notification.NotificationService;
import ro.utcluj.report.Report;
import ro.utcluj.report.ReportFactory;
import ro.utcluj.repository.FavoriteProductRepository;
import ro.utcluj.repository.ProductRepository;
import ro.utcluj.repository.UserProductRepository;
import ro.utcluj.repository.UserRepository;


import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class UserProductService implements UserProductServiceInterface {

    private UserProductRepository repository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private FavoriteProductRepository favoriteProductRepository;

    private UserProductMapper userProductMapper;
    private UserMapper userMapper;

    @Autowired
    public NotificationService notificationService;

    @Autowired
    public UserProductService(UserRepository userRepository, ProductRepository productRepository, UserProductRepository repository, UserProductMapper userProductMapper, UserMapper userMapper, FavoriteProductRepository favoriteProductRepository){
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.repository = repository;
        this.userProductMapper = userProductMapper;
        this.userMapper = userMapper;
        this.favoriteProductRepository = favoriteProductRepository;
    }

    public List<UserProductBaseDTO> getAll(UserBaseDTO user){
        List<UserProductBaseDTO> userProducts = new ArrayList<UserProductBaseDTO>();
        userProducts.addAll(userProductMapper.mapUserProductBaseDTOList(repository.findByUser(userMapper.mapUser(user))));
        return userProducts;
    }

    public List<UserProductBaseDTO> getAll(){
        List<UserProductBaseDTO> userProducts = new ArrayList<UserProductBaseDTO>();
        userProducts.addAll(userProductMapper.mapUserProductBaseDTOList(repository.findAll()));
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

        if(productRepository.getOne(productid).getQuantity() < 5) {

            List<FavoriteProduct> list = favoriteProductRepository.findAll();
            String users = ",";
            for(FavoriteProduct favoriteProduct : list){
                if(favoriteProduct.getProduct().getIdproduct().equals(productid))
                    users += favoriteProduct.getUser().getIduser() + ",";
            }
            notificationService.sendMessageToSomeClients("The product " + product.getName() + " has limited stock! Users:" + users);
        }
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
