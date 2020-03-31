package ro.utcluj;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import ro.utcluj.api.dto.ProductBaseDTO;
import ro.utcluj.entity.Product;
import ro.utcluj.mapper.ProductMapper;
import ro.utcluj.repository.ProductRepository;
import ro.utcluj.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock private ProductRepository productRepository;
    @Mock private ProductMapper productMapper;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    private ProductService productService;

    @Before
    public void setup(){
        productService = new ProductService(productRepository, productMapper);
     }

    @Test
    public void getProductsTest(){
        when(productRepository.findAll()).thenReturn(new ArrayList<>());
        when(productMapper.mapProductBaseDTOList(anyList())).thenReturn(new ArrayList<>());

        List<ProductBaseDTO> products = productService.getProducts();

        Assert.assertEquals(products, new ArrayList<>());

        verify(productRepository, times(1)).findAll();
        verify(productMapper, times(1)).mapProductBaseDTOList(anyList());
    }

    @Test
    public void filterProductsTest(){
        when(productRepository.findAllByNameAndDescription(anyString(), anyString())).thenReturn(new ArrayList<>());
        when(productRepository.findAllByName(anyString())).thenReturn(new ArrayList<>());
        when(productRepository.findAllByDescription(anyString())).thenReturn(new ArrayList<>());
        when(productRepository.findAll()).thenReturn(new ArrayList<>());
        when(productMapper.mapProductBaseDTOList(anyList())).thenReturn(new ArrayList<>());

        List<ProductBaseDTO> filterProducts = productService.filterProducts("Laptop", "Asus");

        Assert.assertEquals(filterProducts, new ArrayList<>());

        verify(productRepository, atMost(1)).findAllByNameAndDescription(anyString(), anyString());
        verify(productRepository, atMost(1)).findAllByDescription(anyString());
        verify(productRepository, atMost(1)).findAllByName(anyString());
        verify(productRepository, atMost(1)).findAll();
        verify(productMapper, times(1)).mapProductBaseDTOList(anyList());
    }
}
