package ro.utcluj.api.serviceInterface;

import ro.utcluj.api.dto.ProductBaseDTO;

import java.util.List;

public interface ProductServiceInterface {

    List<ProductBaseDTO> getProducts();
    List<ProductBaseDTO> filterProducts(String name, String description);
}
