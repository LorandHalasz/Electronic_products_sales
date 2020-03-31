package ro.utcluj.mapper;

import org.springframework.stereotype.Component;
import ro.utcluj.api.dto.LimitedStockBaseDTO;
import ro.utcluj.entity.LimitedStock;

@Component
public class LimitedStockMapper {

    public LimitedStockBaseDTO mapLimitedStockBaseDTO(LimitedStock limitedStock){
        if (limitedStock != null)
            return new LimitedStockBaseDTO(limitedStock.getId(),limitedStock.getStock(),limitedStock.getDiscount());
        else
            return null;
    }

    public LimitedStock mapLimitedStock(LimitedStockBaseDTO limitedStockBaseDTO) {
        LimitedStock limitedStock = new LimitedStock();
        limitedStock.setId(limitedStockBaseDTO.getId());
        limitedStock.setStock(limitedStockBaseDTO.getStock());
        limitedStock.setDiscount(limitedStockBaseDTO.getDiscount());

        return limitedStock;
    }
}
