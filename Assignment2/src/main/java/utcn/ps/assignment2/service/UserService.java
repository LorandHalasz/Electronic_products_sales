package utcn.ps.assignment2.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utcn.ps.assignment2.entity.User;
import utcn.ps.assignment2.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;


@Component
@Transactional
public class UserService {

    private UserRepository repository;
    private static User currentUser;

    @Autowired
    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public User loginUser(String userName, String password){
        User user = repository.findByUsernameAndPassword(userName, password);
        if (user != null) {
            return user;
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

    public User updateUser(Integer id, String name, String userName, String password, String email, String address, String phoneNumber, String userRole, Double balanceAccount){
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
            return user;
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

    public ObservableList<User> getAll(){
        ObservableList<User> users = FXCollections.observableArrayList();
        users.addAll(repository.findAll());
        return users;
    }

    public User getUser(Integer id){
        return repository.findByIduser(id);
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        UserService.currentUser = currentUser;
    }


}
