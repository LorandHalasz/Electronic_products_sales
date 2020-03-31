package ro.utcluj;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import ro.utcluj.api.dto.FavoriteProductBaseDTO;
import ro.utcluj.api.dto.UserBaseDTO;
import ro.utcluj.entity.FavoriteProduct;
import ro.utcluj.entity.Product;
import ro.utcluj.entity.User;
import ro.utcluj.mapper.FavoriteProductMapper;
import ro.utcluj.mapper.UserMapper;
import ro.utcluj.notification.NotificationService;
import ro.utcluj.repository.FavoriteProductRepository;
import ro.utcluj.repository.ProductRepository;
import ro.utcluj.repository.UserRepository;
import ro.utcluj.service.FavoriteProductService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FavoriteProductServiceTest {

    @Mock private FavoriteProductRepository favoriteProductRepository;
    @Mock private UserRepository userRepository;
    @Mock private ProductRepository productRepository;
    @Mock private FavoriteProductMapper favoriteProductMapper;
    @Mock private UserMapper userMapper;
    @Mock private NotificationService notificationService;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    private FavoriteProductService favoriteProductService;
    private User user;
    private UserBaseDTO userBaseDTO;
    private Product product;
    private FavoriteProduct favoriteProduct;

    @Before
    public void setup(){
        favoriteProductService = new FavoriteProductService(userRepository, productRepository, favoriteProductRepository, favoriteProductMapper, userMapper);
        userBaseDTO = new UserBaseDTO(1, "user", "user", "User1234.", "user@user.com", "Str. User", "0752324212", "admin", 3800.0);
        user = new User("user", "user", "User1234.", "user@user.com", "Str. User", "0752324212", "admin", 3800.0, null);
        user.setIduser(1);
        product = new Product("Laptop", "Asus", 2800.0, 5, 4.82);
        product.setIdproduct(1);
        favoriteProduct = new FavoriteProduct(user, product);
        favoriteProduct.setId(1);
        notificationService = new NotificationService();
    }

    @Test
    public void getAllWithParamTest(){
        when(favoriteProductRepository.findByUser(user)).thenReturn(new ArrayList<>());
        when(favoriteProductMapper.mapFavoriteProductBaseDTOList(new ArrayList<>())).thenReturn(new ArrayList<>());
        when(userMapper.mapUser(userBaseDTO)).thenReturn(user);

        List<FavoriteProductBaseDTO> favoriteProductBaseDTOS = favoriteProductService.getAll(userBaseDTO);

        Assert.assertEquals(favoriteProductBaseDTOS, new ArrayList<>());

        verify(favoriteProductRepository, times(1)).findByUser(any());
        verify(favoriteProductMapper, times(1)).mapFavoriteProductBaseDTOList(new ArrayList<>());
        verify(userMapper, times(1)).mapUser(any());
    }

    @Test
    public void getAllTest(){
        when(favoriteProductRepository.findAll()).thenReturn(new ArrayList<>());
        when(favoriteProductMapper.mapFavoriteProductBaseDTOList(new ArrayList<>())).thenReturn(new ArrayList<>());

        List<FavoriteProductBaseDTO> favoriteProductBaseDTOS = favoriteProductService.getAll();

        Assert.assertEquals(favoriteProductBaseDTOS, new ArrayList<>());

        verify(favoriteProductRepository, times(1)).findAll();
        verify(favoriteProductMapper, times(1)).mapFavoriteProductBaseDTOList(new ArrayList<>());
    }

    @Test
    public void addFavoriteProductTest(){
        when(favoriteProductRepository.save(favoriteProduct)).thenReturn(favoriteProduct);
        when(userRepository.getOne(anyInt())).thenReturn(user);
        when(productRepository.getOne(anyInt())).thenReturn(product);

        int status = favoriteProductService.addFavoriteProduct(user.getIduser(), product.getIdproduct());

        Assert.assertEquals(status, 1);

        verify(favoriteProductRepository, times(1)).save(any());
        verify(userRepository, times(1)).getOne(any());
        verify(productRepository, times(1)).getOne(any());
    }

    @Test
    public void deleteFavoriteProductTest(){
        when(userRepository.getOne(anyInt())).thenReturn(user);
        when(productRepository.getOne(anyInt())).thenReturn(product);

       int status = favoriteProductService.deleteFavoriteProduct(user.getIduser(), product.getIdproduct());

        Assert.assertEquals(status, 1);

        verify(favoriteProductRepository, times(1)).deleteByUserAndProduct(any(), any());
        verify(userRepository, times(1)).getOne(any());
        verify(productRepository, times(1)).getOne(any());
    }
}
