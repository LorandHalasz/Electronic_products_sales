package ro.utcluj.mapper;

import org.springframework.stereotype.Component;
import ro.utcluj.api.dto.ProductBaseDTO;
import ro.utcluj.entity.Product;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {

    public ProductBaseDTO productBaseDTO(Product product){
        return new ProductBaseDTO(product.getIdproduct(), product.getName(), product.getDescription(), product.getPrice(), product.getQuantity(), product.getRating());
    }

    public List<ProductBaseDTO> mapProductBaseDTOList(List<Product> list){
        List<ProductBaseDTO> mappedList = new ArrayList<>();
        for(Product product : list){
            mappedList.add(productBaseDTO(product));
        }

        return mappedList;
    }

}
