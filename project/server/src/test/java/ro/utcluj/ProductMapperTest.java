package ro.utcluj;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import ro.utcluj.api.dto.ProductBaseDTO;
import ro.utcluj.entity.Product;
import ro.utcluj.mapper.ProductMapper;

import java.util.ArrayList;
import java.util.List;

public class ProductMapperTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private ProductMapper productMapper;
    private Product product;
    private ProductBaseDTO productBaseDTO;

    @Before
    public void setup(){
        productMapper = new ProductMapper();
        product = new Product("Laptop", "Asus", 2800.0, 5, 4.82);
        product.setIdproduct(1);
        productBaseDTO = new ProductBaseDTO(1,"Laptop", "Asus", 2800.0, 5, 4.82);
    }

    @Test
    public void productBaseDTOTest(){
        ProductBaseDTO productBaseDTOExpected = productMapper.productBaseDTO(product);

        Assert.assertEquals(productBaseDTO.getName(), productBaseDTOExpected.getName());
    }

    @Test
    public void mapProductBaseDTOListTest(){
        List<ProductBaseDTO> productBaseDTOSExpected = productMapper.mapProductBaseDTOList(new ArrayList<>());

        Assert.assertEquals(new ArrayList<>(), productBaseDTOSExpected);
    }
}
