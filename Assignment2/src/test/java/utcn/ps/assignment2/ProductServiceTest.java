package utcn.ps.assignment2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import utcn.ps.assignment2.entity.Product;
import utcn.ps.assignment2.repository.ProductRepository;
import utcn.ps.assignment2.service.ProductService;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private Product productExpected;

    @Before
    public void setup(){
        productService = new ProductService(productRepository);
        productExpected = new Product("", "", 0.0, 0, 0.0);
    }

    @Test
    public void getAllTest(){
        when(productRepository.findAll()).thenReturn(FXCollections.observableArrayList());

        ObservableList<Product> productReturned = productService.getProducts();

        verify(productRepository, times(1)).findAll();

        Assert.assertEquals(productReturned, FXCollections.observableArrayList());
    }@Test

    public void filterProducts(){
        when(productRepository.findAllByNameAndDescription(anyString(), anyString())).thenReturn(FXCollections.observableArrayList());

        ObservableList<Product> filterProducts = productService.filterProducts("Laptop", "Asus");

        verify(productRepository, times(1)).findAllByNameAndDescription("Laptop", "Asus");

        Assert.assertEquals(filterProducts, FXCollections.observableArrayList());
    }
}
