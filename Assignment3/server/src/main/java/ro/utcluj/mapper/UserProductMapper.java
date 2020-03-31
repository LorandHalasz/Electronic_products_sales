package ro.utcluj.mapper;

import org.springframework.stereotype.Component;
import ro.utcluj.api.dto.UserProductBaseDTO;
import ro.utcluj.entity.UserProduct;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserProductMapper {

    UserMapper userMapper;
    ProductMapper productMapper;

    public UserProductMapper(){
        this.userMapper = new UserMapper();
        this.productMapper = new ProductMapper();
    }

    public UserProductBaseDTO mapUserProductBaseDTO(UserProduct userProduct){
        if (userProduct != null)
            return new UserProductBaseDTO(userProduct.getId(), userMapper.mapUserBaseDTO(userProduct.getUser()), productMapper.productBaseDTO(userProduct.getProduct()), userProduct.getDate());
        else
            return null;
    }

    public List<UserProductBaseDTO> mapUserProductBaseDTOList(List<UserProduct> list){
        List<UserProductBaseDTO> mappedList = new ArrayList<>();
        for(UserProduct userProduct : list){
            mappedList.add(mapUserProductBaseDTO(userProduct));
        }
        return mappedList;
    }

    public UserProduct mapUserProduct(UserProductBaseDTO userBaseDTO) {
        return new UserProduct();
    }
}
