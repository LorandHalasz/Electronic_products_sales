package ro.utcluj.api.serviceInterface;

import ro.utcluj.api.dto.UserBaseDTO;

import java.util.List;

public interface UserServiceInterface {

     UserBaseDTO loginUser(String userName, String password);
    int addUser(String name, String userName, String password, String email, String address, String phoneNumber, String userRole, Double balanceAccount);
    int deleteUser(Integer id);
    UserBaseDTO updateUser(Integer id, String name, String userName, String password, String email, String address, String phoneNumber, String userRole, Double balanceAccount);
    void printUsers();
    List<UserBaseDTO> getAll();
    UserBaseDTO getUser(Integer id);
}
