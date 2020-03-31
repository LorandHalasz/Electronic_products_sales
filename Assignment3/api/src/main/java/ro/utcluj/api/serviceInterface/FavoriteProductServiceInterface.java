package ro.utcluj.api.serviceInterface;

import ro.utcluj.api.dto.FavoriteProductBaseDTO;
import ro.utcluj.api.dto.UserBaseDTO;

import java.util.List;

public interface FavoriteProductServiceInterface {
    public List<FavoriteProductBaseDTO> getAll(UserBaseDTO user);

    public List<FavoriteProductBaseDTO> getAll();

    public void addFavoriteProduct(Integer userid, Integer productid);
    public int deleteFavoriteProduct(Integer idUser, Integer idProduct);
}
