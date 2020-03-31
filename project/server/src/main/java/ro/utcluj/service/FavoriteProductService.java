package ro.utcluj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.utcluj.api.dto.FavoriteProductBaseDTO;
import ro.utcluj.api.serviceInterface.FavoriteProductServiceInterface;
import ro.utcluj.api.dto.UserBaseDTO;
import ro.utcluj.entity.FavoriteProduct;
import ro.utcluj.entity.Product;
import ro.utcluj.entity.User;
import ro.utcluj.mapper.FavoriteProductMapper;
import ro.utcluj.mapper.UserMapper;
import ro.utcluj.repository.FavoriteProductRepository;
import ro.utcluj.repository.ProductRepository;
import ro.utcluj.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.*;

@Component
@Transactional
public class FavoriteProductService implements FavoriteProductServiceInterface {

    private FavoriteProductRepository repository;
    private UserRepository userRepository;
    private ProductRepository productRepository;

    private FavoriteProductMapper favoriteProductMapper;
    private UserMapper userMapper;

    @Autowired
    public FavoriteProductService(UserRepository userRepository, ProductRepository productRepository, FavoriteProductRepository repository, FavoriteProductMapper favoriteProductMapper, UserMapper userMapper){
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.repository = repository;
        this.favoriteProductMapper = favoriteProductMapper;
        this.userMapper = userMapper;
    }

    public List<FavoriteProductBaseDTO> getAll(UserBaseDTO user){
        List<FavoriteProductBaseDTO> favoriteProducts = new ArrayList<FavoriteProductBaseDTO>();
        favoriteProducts.addAll(favoriteProductMapper.mapFavoriteProductBaseDTOList(repository.findByUser(userMapper.mapUser(user))));
        return favoriteProducts;
    }

    public List<FavoriteProductBaseDTO> getAll(){
        List<FavoriteProductBaseDTO> favoriteProducts = new ArrayList<FavoriteProductBaseDTO>();
        favoriteProducts.addAll(favoriteProductMapper.mapFavoriteProductBaseDTOList(repository.findAll()));
        return favoriteProducts;
    }

    public int addFavoriteProduct(Integer userid, Integer productid){
        try {
            User user = userRepository.getOne(userid);
            Product product = productRepository.getOne(productid);
            repository.save(new FavoriteProduct(user, product));
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public int deleteFavoriteProduct(Integer idUser, Integer idProduct){
        try {
            repository.deleteByUserAndProduct(userRepository.getOne(idUser), productRepository.getOne(idProduct));
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
