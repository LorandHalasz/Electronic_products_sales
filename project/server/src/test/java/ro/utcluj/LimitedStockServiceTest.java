package ro.utcluj;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import ro.utcluj.api.dto.LimitedStockBaseDTO;
import ro.utcluj.entity.LimitedStock;
import ro.utcluj.entity.Product;
import ro.utcluj.mapper.LimitedStockMapper;
import ro.utcluj.repository.LimitedStockRepository;
import ro.utcluj.repository.ProductRepository;
import ro.utcluj.service.LimitedStockService;

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class LimitedStockServiceTest {
    @Mock private LimitedStockRepository limitedStockRepository;
    @Mock private LimitedStockMapper limitedStockMapper;
    @Mock private ProductRepository productRepository;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    private LimitedStockService limitedStockService;
    private LimitedStock limitedStock;
    private LimitedStockBaseDTO limitedStockBaseDTO;
    private Product product;


    @Before
    public void setup(){
        limitedStockService = new LimitedStockService(limitedStockRepository, limitedStockMapper, productRepository);
        limitedStock = new LimitedStock(1, 5, 15);
        limitedStockBaseDTO = new LimitedStockBaseDTO(1, 6, 15);
        product = new Product("Laptop", "Asus", 2800.0, 5, 4.82);
        product.setIdproduct(1);
    }

    @Test
    public void getLimitedStockMapperTest(){
        when(limitedStockRepository.getOne(1)).thenReturn(limitedStock);
        when(limitedStockMapper.mapLimitedStockBaseDTO(limitedStock)).thenReturn(limitedStockBaseDTO);

        LimitedStockBaseDTO limitedStock = limitedStockService.getLimitedStockMapper();

        Assert.assertEquals(limitedStock, limitedStockBaseDTO);

        verify(limitedStockRepository, times(1)).getOne(any());
        verify(limitedStockMapper, times(1)).mapLimitedStockBaseDTO(any());
    }

    @Test
    public void updateLimitedStockTest(){
        when(limitedStockRepository.getOne(anyInt())).thenReturn(limitedStock);
        when(productRepository.findAll()).thenReturn(new ArrayList<>());
        when(productRepository.getOne(anyInt())).thenReturn(product);
        when(limitedStockMapper.mapLimitedStockBaseDTO(limitedStock)).thenReturn(limitedStockBaseDTO);

        LimitedStockBaseDTO limitedStock = limitedStockService.updateLimitedStock(6, 15);

        Assert.assertEquals(limitedStock, limitedStockBaseDTO);

        verify(limitedStockRepository, times(1)).getOne(any());
        verify(productRepository, atLeast(1)).findAll();
        verify(productRepository, atMost(productRepository.findAll().size())).getOne(any());
        verify(limitedStockMapper, times(1)).mapLimitedStockBaseDTO(any());
    }
}
