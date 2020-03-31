package ro.utcluj.api.serviceInterface;

import ro.utcluj.api.dto.LimitedStockBaseDTO;

public interface LimitedStockServiceInterface {

    LimitedStockBaseDTO getLimitedStockMapper();
    LimitedStockBaseDTO updateLimitedStock(Integer stock, Integer discount);
}
