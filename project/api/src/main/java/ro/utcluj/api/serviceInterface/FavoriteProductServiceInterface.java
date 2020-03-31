package ro.utcluj.api.serviceInterface;

import ro.utcluj.api.dto.FavoriteProductBaseDTO;
import ro.utcluj.api.dto.UserBaseDTO;

import java.util.List;

public interface FavoriteProductServiceInterface {

    List<FavoriteProductBaseDTO> getAll(UserBaseDTO user);
    List<FavoriteProductBaseDTO> getAll();
    int addFavoriteProduct(Integer userid, Integer productid);
    int deleteFavoriteProduct(Integer idUser, Integer idProduct);
}
