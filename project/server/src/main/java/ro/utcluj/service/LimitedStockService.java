package ro.utcluj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.utcluj.api.dto.LimitedStockBaseDTO;
import ro.utcluj.api.serviceInterface.LimitedStockServiceInterface;
import ro.utcluj.entity.LimitedStock;
import ro.utcluj.entity.Product;
import ro.utcluj.mapper.LimitedStockMapper;
import ro.utcluj.repository.LimitedStockRepository;
import ro.utcluj.repository.ProductRepository;

@Component
@Transactional
public class LimitedStockService implements LimitedStockServiceInterface {

    private LimitedStockRepository limitedStockRepository;
    private LimitedStockMapper limitedStockMapper;
    private ProductRepository productRepository;

    @Autowired
    public LimitedStockService(LimitedStockRepository limitedStockRepository, LimitedStockMapper limitedStockMapper, ProductRepository productRepository) {
        this.limitedStockRepository = limitedStockRepository;
        this.limitedStockMapper = limitedStockMapper;
        this.productRepository = productRepository;
    }

    public LimitedStockBaseDTO getLimitedStockMapper(){
        return limitedStockMapper.mapLimitedStockBaseDTO(limitedStockRepository.getOne(1));
    }

    public LimitedStockBaseDTO updateLimitedStock(Integer stock, Integer discount){
        try {
            LimitedStock limitedStock = limitedStockRepository.getOne(1);
            for (Product product : productRepository.findAll())
                if (product.getQuantity() < limitedStock.getStock())
                    productRepository.getOne(product.getIdproduct()).setPrice(productRepository.getOne(product.getIdproduct()).getPrice() / (100 - limitedStock.getDiscount()) * 100);

            for (Product product : productRepository.findAll())
                if (product.getQuantity() < stock)
                    productRepository.getOne(product.getIdproduct()).setPrice(productRepository.getOne(product.getIdproduct()).getPrice() * (100 - discount) / 100);

            limitedStock.setStock(stock);
            limitedStock.setDiscount(discount);
            return limitedStockMapper.mapLimitedStockBaseDTO(limitedStock);
        } catch (Exception e) {
            return null;
        }
    }

}
