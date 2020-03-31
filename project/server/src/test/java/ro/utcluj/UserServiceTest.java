package ro.utcluj;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import ro.utcluj.api.dto.UserBaseDTO;
import ro.utcluj.entity.User;
import ro.utcluj.mapper.UserMapper;
import ro.utcluj.repository.UserRepository;
import ro.utcluj.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private UserService userService;
    private UserBaseDTO userBaseDTO;
    private User user;

    @Before
    public void setup(){
        userService = new UserService(userRepository, userMapper);
        userBaseDTO = new UserBaseDTO(1, "user", "user", "User1234.", "user#user.com", "Str. User", "0752324212", "admin", 3800.0);
        user = new User("user", "user", "User1234.", "user#user.com", "Str. User", "0752324212", "admin", 3800.0, null);
        user.setIduser(1);
    }

    @Test
    public void loginUserTest(){
        when(userRepository.findByUsernameAndPassword(anyString(), anyString())).thenReturn(user);
        when(userMapper.mapUserBaseDTO(user)).thenReturn(userBaseDTO);

        UserBaseDTO expectedUser = userService.loginUser("user", "User1234");

        Assert.assertEquals(userBaseDTO, expectedUser);

        verify(userRepository, times(1)).findByUsernameAndPassword(anyString(), anyString());
        verify(userMapper, times(1)).mapUserBaseDTO(user);
    }

    @Test
    public void addUserTest(){
        when(userRepository.findByUsernameAndPassword(anyString(), anyString())).thenReturn(user);

        int status = userService.addUser("user", "user", "User1234.", "user#user.com", "Str. User", "0752324212", "admin", 3800.0);

        Assert.assertEquals(status, 1);

        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void deleteUserTest(){
        int status = userService.deleteUser(1);

        Assert.assertEquals(status, 1);

        verify(userRepository, times(1)).deleteById(any());
    }

    @Test
    public void updateUserTest(){
        when(userRepository.getOne(1)).thenReturn(user);
        when(userMapper.mapUserBaseDTO(user)).thenReturn(userBaseDTO);

        UserBaseDTO expectedUser = userService.updateUser(user.getIduser(), user.getName(), user.getUsername(), user.getPassword(), user.getEmail(), user.getAddress(), user.getPhone_number(), user.getUser_role(), user.getBalance_account());

        Assert.assertEquals(expectedUser, userBaseDTO);

        verify(userRepository, times(1)).getOne(1);
        verify(userMapper, times(1)).mapUserBaseDTO(user);
    }

    @Test
    public void printUsersTest(){
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        userService.printUsers();

        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void getAllTest(){
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        when(userMapper.mapUserBaseDTOList(new ArrayList<>())).thenReturn(new ArrayList<>());

        List<UserBaseDTO> userBaseDTOList = userService.getAll();

        Assert.assertEquals(userBaseDTOList, new ArrayList<>());

        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).mapUserBaseDTOList(new ArrayList<>());
    }

    @Test
    public void getUser(){
        when(userRepository.findByIduser(anyInt())).thenReturn(user);
        when(userMapper.mapUserBaseDTO(user)).thenReturn(userBaseDTO);

        UserBaseDTO expectedUser = userService.getUser(1);

        Assert.assertEquals(userBaseDTO, expectedUser);

        verify(userRepository, times(1)).findByIduser(anyInt());
        verify(userMapper, times(1)).mapUserBaseDTO(user);
    }
}
