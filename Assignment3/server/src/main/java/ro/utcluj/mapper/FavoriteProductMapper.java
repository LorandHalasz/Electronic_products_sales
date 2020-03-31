package ro.utcluj.mapper;

import org.springframework.stereotype.Component;
import ro.utcluj.api.dto.FavoriteProductBaseDTO;
import ro.utcluj.entity.FavoriteProduct;

import java.util.ArrayList;
import java.util.List;

@Component
public class FavoriteProductMapper {
    UserMapper userMapper;
    ProductMapper productMapper;

    public FavoriteProductMapper(){
        this.userMapper = new UserMapper();
        this.productMapper = new ProductMapper();
    }

    public FavoriteProductBaseDTO mapFavoriteProductBaseDTO(FavoriteProduct favoriteProduct){
        if (favoriteProduct != null)
            return new FavoriteProductBaseDTO(favoriteProduct.getId(), userMapper.mapUserBaseDTO(favoriteProduct.getUser()), productMapper.productBaseDTO(favoriteProduct.getProduct()));
        else
            return null;
    }

    public List<FavoriteProductBaseDTO> mapFavoriteProductBaseDTOList(List<FavoriteProduct> list){
        List<FavoriteProductBaseDTO> mappedList = new ArrayList<>();
        for(FavoriteProduct favoriteProduct : list){
            mappedList.add(mapFavoriteProductBaseDTO(favoriteProduct));
        }
        return mappedList;
    }

    public FavoriteProduct mapFavoriteProduct(FavoriteProductBaseDTO userBaseDTO) {
        return new FavoriteProduct();
    }

}
