package ro.utcluj;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import ro.utcluj.api.dto.LimitedStockBaseDTO;

import ro.utcluj.entity.LimitedStock;
import ro.utcluj.mapper.LimitedStockMapper;


public class LimitedStockMapperTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private LimitedStockMapper limitedStockMapper;
    private LimitedStock limitedStock;
    private LimitedStockBaseDTO limitedStockBaseDTO;

    @Before
    public void setup(){
        limitedStockMapper = new LimitedStockMapper();
        limitedStock = new LimitedStock(1, 5, 15);
        limitedStockBaseDTO = new LimitedStockBaseDTO(1, 5, 15);
    }

    @Test
    public void mapLimitedStockTest(){
        LimitedStock limitedStockExpected = limitedStockMapper.mapLimitedStock(limitedStockBaseDTO);

        Assert.assertEquals(limitedStock.getId(), limitedStockExpected.getId());
    }

    @Test
    public void mapLimitedStockBaseDTOTest(){
        LimitedStockBaseDTO limitedStockBaseDTOExpected = limitedStockMapper.mapLimitedStockBaseDTO(limitedStock);

        Assert.assertEquals(limitedStockBaseDTO.getId(), limitedStockBaseDTOExpected.getId());
    }
}
