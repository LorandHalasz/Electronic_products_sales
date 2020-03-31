package ro.utcluj.api.serviceInterface;

import ro.utcluj.api.dto.UserBaseDTO;
import ro.utcluj.api.dto.UserProductBaseDTO;

import java.util.List;

public interface UserProductServiceInterface {

    List<UserProductBaseDTO> getAll(UserBaseDTO user);
    List<UserProductBaseDTO> getAll();
    Integer addUserProduct(Integer userid, Integer productid);
    //void report(String filePath, String reportType);
    int deleteUserProduct(Integer id);
}
