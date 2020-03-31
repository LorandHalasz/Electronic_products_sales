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
import ro.utcluj.mapper.ProductMapper;
import ro.utcluj.mapper.UserMapper;
import ro.utcluj.mapper.UserProductMapper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class UserProductMapperTest {

    @Mock private UserMapper userMapper;
    @Mock private ProductMapper productMapper;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private UserProductMapper userProductMapper;

    private User user;
    private UserBaseDTO userBaseDTO;
    private Product product;
    private ProductBaseDTO productBaseDTO;
    private UserProduct userProduct;
    private UserProductBaseDTO userProductBaseDTO;
    private LimitedStock limitedStock;

    @Before
    public void setup(){
        userProductMapper = new UserProductMapper();
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
    public void mapUserProductBaseDTOTest(){
        UserProductBaseDTO userProductBaseDTOExpected = userProductMapper.mapUserProductBaseDTO(userProduct);

        Assert.assertEquals(userProductBaseDTO.getId(), userProductBaseDTOExpected.getId());
    }

    @Test
    public void mapUserProductBaseDTOListTest(){
        List<UserProductBaseDTO> userProductBaseDTOSExpected = userProductMapper.mapUserProductBaseDTOList(new ArrayList<>());

        Assert.assertEquals(new ArrayList<>(), userProductBaseDTOSExpected);
    }
}
