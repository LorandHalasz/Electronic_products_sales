package ro.utcluj;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import ro.utcluj.api.dto.ProductBaseDTO;
import ro.utcluj.api.dto.UserBaseDTO;
import ro.utcluj.api.dto.UserProductBaseDTO;
import ro.utcluj.entity.LimitedStock;
import ro.utcluj.entity.Product;
import ro.utcluj.entity.User;
import ro.utcluj.entity.UserProduct;
import ro.utcluj.mapper.FavoriteProductMapper;
import ro.utcluj.mapper.UserMapper;
import ro.utcluj.mapper.UserProductMapper;
import ro.utcluj.notification.NotificationService;
import ro.utcluj.repository.*;
import ro.utcluj.service.FavoriteProductService;
import ro.utcluj.service.UserProductService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class UserProductServiceTest {
    @Mock private UserProductRepository userProductRepository;
    @Mock private UserRepository userRepository;
    @Mock private ProductRepository productRepository;
    @Mock private FavoriteProductRepository favoriteProductRepository;
    @Mock private LimitedStockRepository limitedStockRepository;

    @Mock private UserProductMapper userProductMapper;
    @Mock private UserMapper userMapper;
    @Mock private NotificationService notificationService;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    private UserProductService userProductService;
    private User user;
    private UserBaseDTO userBaseDTO;
    private Product product;
    private ProductBaseDTO productBaseDTO;
    private UserProduct userProduct;
    private UserProductBaseDTO userProductBaseDTO;
    private LimitedStock limitedStock;

    @Before
    public void setup(){
        userProductService = new UserProductService(userRepository, productRepository, userProductRepository, userProductMapper, userMapper, favoriteProductRepository, limitedStockRepository);
        userBaseDTO = new UserBaseDTO(1, "user", "user", "User1234.", "user@user.com", "Str. User", "0752324212", "admin", 3800.0);
        user = new User("user", "user", "User1234.", "user@user.com", "Str. User", "0752324212", "admin", 3800.0, null);
        user.setIduser(1);
        product = new Product("Laptop", "Asus", 2800.0, 5, 4.82);
        product.setIdproduct(1);
        productBaseDTO = new ProductBaseDTO(1,"Laptop", "Asus", 2800.0, 5, 4.82);
        userProduct = new UserProduct(user, product, new Date(System.currentTimeMillis()));
        userProduct.setId(1);
        userProductBaseDTO = new UserProductBaseDTO(1, userBaseDTO, productBaseDTO, new Date(System.currentTimeMillis()));
        limitedStock = new LimitedStock(1, 5, 15);
    }

    @Test
    public void getAllWithParamTest(){
        when(userProductRepository.findByUser(user)).thenReturn(new ArrayList<>());
        when(userProductMapper.mapUserProductBaseDTOList(new ArrayList<>())).thenReturn(new ArrayList<>());
        when(userMapper.mapUser(userBaseDTO)).thenReturn(user);

        List<UserProductBaseDTO> userProductBaseDTOS = userProductService.getAll(userBaseDTO);

        Assert.assertEquals(userProductBaseDTOS, new ArrayList<>());

        verify(userProductRepository, times(1)).findByUser(any());
        verify(userProductMapper, times(1)).mapUserProductBaseDTOList(new ArrayList<>());
        verify(userMapper, times(1)).mapUser(any());
    }

    @Test
    public void getAllTest(){
        when(userProductRepository.findAll()).thenReturn(new ArrayList<>());
        when(userProductMapper.mapUserProductBaseDTOList(new ArrayList<>())).thenReturn(new ArrayList<>());

        List<UserProductBaseDTO> userProductBaseDTOS = userProductService.getAll();

        Assert.assertEquals(userProductBaseDTOS, new ArrayList<>());

        verify(userProductRepository, times(1)).findAll();
        verify(userProductMapper, times(1)).mapUserProductBaseDTOList(new ArrayList<>());
    }

    @Test
    public void addUserProductTest(){
        when(userRepository.getOne(anyInt())).thenReturn(user);
        when(productRepository.getOne(anyInt())).thenReturn(product);
        when(userProductRepository.save(any())).thenReturn(userProduct);
        when(limitedStockRepository.getOne(anyInt())).thenReturn(limitedStock);
        when(favoriteProductRepository.findAll()).thenReturn(new ArrayList<>());

        int status = userProductService.addUserProduct(1, 1);

        Assert.assertEquals(0, status);

        verify(userRepository, times(2)).getOne(any());
        verify(productRepository, times(3)).getOne(any());
        verify(userProductRepository, times(1)).save(any());
        verify(limitedStockRepository, atLeast(1)).getOne(any());
        verify(favoriteProductRepository, times(1)).findAll();
    }

    @Test
    public void deleteUserProductTest(){
        when(userRepository.getOne(anyInt())).thenReturn(user);

        int status = userProductService.deleteUserProduct(userProduct.getId());

        Assert.assertEquals(status, 1);

        verify(userRepository, times(1)).getOne(any());
    }
}
