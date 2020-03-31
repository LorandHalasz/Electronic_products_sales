package ro.utcluj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.utcluj.api.dto.ProductBaseDTO;
import ro.utcluj.api.serviceInterface.ProductServiceInterface;
import ro.utcluj.mapper.ProductMapper;
import ro.utcluj.repository.ProductRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class ProductService implements ProductServiceInterface {

    private ProductRepository repository;
    private ProductMapper productMapper;
    @Autowired
    public ProductService(ProductRepository repository, ProductMapper productMapper){
        this.repository = repository;
        this.productMapper = productMapper;
    }

    public List<ProductBaseDTO> getProducts(){
        List<ProductBaseDTO> products = new ArrayList<ProductBaseDTO>();
        products.addAll(productMapper.mapProductBaseDTOList(repository.findAll()));
        return products;
    }

    public List<ProductBaseDTO> filterProducts(String name, String description){
        List<ProductBaseDTO> products = new ArrayList<ProductBaseDTO>();
        if(!name.isEmpty() && !description.isEmpty())
            products.addAll(productMapper.mapProductBaseDTOList(repository.findAllByNameAndDescription(name, description)));
        else
            if(!name.isEmpty())
                products.addAll(productMapper.mapProductBaseDTOList(repository.findAllByName(name)));
            else
                if(!description.isEmpty())
                    products.addAll(productMapper.mapProductBaseDTOList(repository.findAllByDescription(description)));
                else
                    products.addAll(productMapper.mapProductBaseDTOList(repository.findAll()));
        return products;
    }
}
