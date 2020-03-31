package ro.utcluj.api.serviceInterface;

import ro.utcluj.api.dto.UserBaseDTO;

import java.util.List;

public interface UserServiceInterface {

    public UserBaseDTO loginUser(String userName, String password);
    public int addUser(String name, String userName, String password, String email, String address, String phoneNumber, String userRole, Double balanceAccount);

    public int deleteUser(Integer id);
    public UserBaseDTO updateUser(Integer id, String name, String userName, String password, String email, String address, String phoneNumber, String userRole, Double balanceAccount);

    public void printUsers();

    public List<UserBaseDTO> getAll();
    public UserBaseDTO getUser(Integer id);
}
