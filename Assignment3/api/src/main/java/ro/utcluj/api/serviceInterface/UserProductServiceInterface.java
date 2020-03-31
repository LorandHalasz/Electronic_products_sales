package ro.utcluj.api.serviceInterface;

import ro.utcluj.api.dto.UserBaseDTO;
import ro.utcluj.api.dto.UserProductBaseDTO;

import java.util.List;

public interface UserProductServiceInterface {

    public List<UserProductBaseDTO> getAll(UserBaseDTO user);

    public List<UserProductBaseDTO> getAll();

    public Integer addUserProduct(Integer userid, Integer productid);

    public void report(String filePath, String reportType);

    public int deleteUserProduct(Integer id);

}
