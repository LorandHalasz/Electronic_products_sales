package ro.utcluj;

import javafx.collections.FXCollections;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import ro.utcluj.api.dto.ProductBaseDTO;
import ro.utcluj.mapper.ProductMapper;
import ro.utcluj.repository.ProductRepository;
import ro.utcluj.service.ProductService;

import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    private ProductService productService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private ProductBaseDTO productExpected;

    @Before
    public void setup(){
        productService = new ProductService(productRepository, productMapper);
        productExpected = new ProductBaseDTO("", "", 0.0, 0, 0.0);
    }

    @Test
    public void getAllTest(){
        when(productRepository.findAll()).thenReturn(FXCollections.observableArrayList());

        List<ProductBaseDTO> productReturned = productService.getProducts();

        verify(productRepository, times(1)).findAll();

        Assert.assertEquals(productReturned, FXCollections.observableArrayList());
    }

    @Test
    public void filterProducts(){
        when(productRepository.findAllByNameAndDescription(anyString(), anyString())).thenReturn(FXCollections.observableArrayList());

        List<ProductBaseDTO> filterProducts = productService.filterProducts("Laptop", "Asus");

        verify(productRepository, times(1)).findAllByNameAndDescription("Laptop", "Asus");

        Assert.assertEquals(filterProducts, FXCollections.observableArrayList());
    }
}
