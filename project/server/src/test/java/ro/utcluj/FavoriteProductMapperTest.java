package ro.utcluj;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import ro.utcluj.api.dto.FavoriteProductBaseDTO;
import ro.utcluj.api.dto.ProductBaseDTO;
import ro.utcluj.api.dto.UserBaseDTO;
import ro.utcluj.entity.FavoriteProduct;
import ro.utcluj.entity.Product;
import ro.utcluj.entity.User;
import ro.utcluj.mapper.FavoriteProductMapper;
import ro.utcluj.mapper.ProductMapper;
import ro.utcluj.mapper.UserMapper;
import ro.utcluj.repository.FavoriteProductRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class FavoriteProductMapperTest {

    @Mock private UserMapper userMapper;
    @Mock private ProductMapper productMapper;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private FavoriteProductMapper favoriteProductMapper;
    private User user;
    private UserBaseDTO userBaseDTO;
    private Product product;
    private ProductBaseDTO productBaseDTO;
    private FavoriteProduct favoriteProduct;
    private FavoriteProductBaseDTO favoriteProductBaseDTO;

    @Before
    public void setup(){
        favoriteProductMapper = new FavoriteProductMapper();
        userBaseDTO = new UserBaseDTO(1, "user", "user", "User1234.", "user@user.com", "Str. User", "0752324212", "admin", 3800.0);
        user = new User("user", "user", "User1234.", "user@user.com", "Str. User", "0752324212", "admin", 3800.0, null);
        user.setIduser(1);
        product = new Product("Laptop", "Asus", 2800.0, 5, 4.82);
        product.setIdproduct(1);
        productBaseDTO = new ProductBaseDTO(1, "Laptop", "Asus", 2800.0, 5, 4.82);
        favoriteProduct = new FavoriteProduct(user, product);
        favoriteProduct.setId(1);
        favoriteProductBaseDTO = new FavoriteProductBaseDTO(userBaseDTO, productBaseDTO);
        favoriteProductBaseDTO.setId(1);
    }

    @Test
    public void mapFavoriteProductBaseDTOTest(){
        when(userMapper.mapUserBaseDTO(favoriteProduct.getUser())).thenReturn(userBaseDTO);
        when(productMapper.productBaseDTO(favoriteProduct.getProduct())).thenReturn(productBaseDTO);

        FavoriteProductBaseDTO favoriteProductBase = favoriteProductMapper.mapFavoriteProductBaseDTO(favoriteProduct);

        Assert.assertEquals(favoriteProductBaseDTO.getId(), favoriteProductBase.getId());

        verify(userMapper, atMost(1)).mapUserBaseDTO(any());
        verify(productMapper, atMost(1)).productBaseDTO(any());
    }

    @Test
    public void mapFavoriteProductBaseDTOListTest(){
        List<FavoriteProductBaseDTO> favoriteProductBaseDTOS = favoriteProductMapper.mapFavoriteProductBaseDTOList(new ArrayList<>());

        Assert.assertEquals(new ArrayList<>(), favoriteProductBaseDTOS);
    }
}
