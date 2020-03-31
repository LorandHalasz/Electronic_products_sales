package utcn.ps.assignment2;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import utcn.ps.assignment2.entity.User;
import utcn.ps.assignment2.entity.UserProduct;
import utcn.ps.assignment2.repository.UserRepository;
import utcn.ps.assignment2.service.UserService;

import java.util.HashSet;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private User userExpected;

    @Before
    public void setup(){
        userService = new UserService(userRepository);
        userExpected = new User("", "", "", "", "", "", "", 0.0, new HashSet<UserProduct>());
    }

    @Test
    public void testLoginUser(){
        when(userRepository.findByUsernameAndPassword(anyString(), anyString())).thenReturn(userExpected);

        User userReturned = userService.loginUser(userExpected.getUsername(), userExpected.getPassword());

        verify(userRepository, times(1)).findByUsernameAndPassword(anyString(), anyString());
        Assert.assertEquals(userReturned, userExpected);
    }

    @Test
    public void updateUser(){
        when(userRepository.getOne(anyInt())).thenReturn(userExpected);

        User user = userService.updateUser(userExpected.getIduser(), "userresu", userExpected.getUsername(), userExpected.getPassword(), userExpected.getEmail(), userExpected.getAddress(), userExpected.getPhone_number(), userExpected.getUser_role(), userExpected.getBalance_account());

        verify(userRepository, times(1)).getOne(anyInt());

        Assert.assertEquals(user.getName(), userExpected.getName());
    }

    @Test
    public void getAllTest(){
        when(userRepository.findAll()).thenReturn(FXCollections.observableArrayList());

        ObservableList<User> userReturned = userService.getAll();

        verify(userRepository, times(1)).findAll();

        Assert.assertEquals(userReturned, FXCollections.observableArrayList());
    }
}
