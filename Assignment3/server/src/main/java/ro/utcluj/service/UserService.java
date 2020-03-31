package ro.utcluj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.utcluj.api.dto.UserBaseDTO;
import ro.utcluj.api.serviceInterface.UserServiceInterface;
import ro.utcluj.entity.User;
import ro.utcluj.mapper.UserMapper;
import ro.utcluj.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Component
@Transactional
public class UserService implements UserServiceInterface {

    private UserRepository repository;

    private UserMapper userMapper;

    @Autowired
    public UserService(UserRepository repository, UserMapper userMapper) {
        this.repository = repository;
        this.userMapper = userMapper;
    }

    public UserBaseDTO loginUser(String userName, String password){
        User user = repository.findByUsernameAndPassword(userName, password);
        if (user != null) {
            return userMapper.mapUserBaseDTO(user);
        }
        return null;
    }

    public int addUser(String name, String userName, String password, String email, String address, String phoneNumber, String userRole, Double balanceAccount){
        try {
            System.out.println(name);
            repository.save(new User(name, userName, password, email, address, phoneNumber, userRole, balanceAccount, new HashSet<>()));
            return 1;
        }catch (Exception ex){
            return 0;
        }
    }

    public int deleteUser(Integer id){
        try {
            repository.deleteById(id);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public UserBaseDTO updateUser(Integer id, String name, String userName, String password, String email, String address, String phoneNumber, String userRole, Double balanceAccount){
        try {
            User user = repository.getOne(id);
            user.setName(name);
            user.setUsername(userName);
            user.setPassword(password);
            user.setEmail(email);
            user.setAddress(address);
            user.setPhone_number(phoneNumber);
            user.setUser_role(userRole);
            user.setBalance_account(balanceAccount);
            return userMapper.mapUserBaseDTO(user);
        } catch (Exception e) {
            return null;
        }
    }

    public void printUsers(){
        List<User> students = repository.findAll();

        for (User student: students){
            System.out.println(student.getName());
            System.out.println(student.getEmail());
        }
    }

    public List<UserBaseDTO> getAll(){
        List<UserBaseDTO> users = new ArrayList<UserBaseDTO>();
        users.addAll(userMapper.mapUserBaseDTOList(repository.findAll()));
        return users;
    }

    public UserBaseDTO getUser(Integer id){
        return userMapper.mapUserBaseDTO(repository.findByIduser(id));
    }

}
