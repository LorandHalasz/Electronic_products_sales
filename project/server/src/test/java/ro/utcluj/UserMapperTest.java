package ro.utcluj;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import ro.utcluj.api.dto.UserBaseDTO;
import ro.utcluj.entity.User;
import ro.utcluj.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

public class UserMapperTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private UserMapper userMapper;
    private User user;
    private UserBaseDTO userBaseDTO;

    @Before
    public void setup(){
        userMapper = new UserMapper();
        userBaseDTO = new UserBaseDTO(1, "user", "user", "User1234.", "user#user.com", "Str. User", "0752324212", "admin", 3800.0);
        user = new User("user", "user", "User1234.", "user#user.com", "Str. User", "0752324212", "admin", 3800.0, null);
        user.setIduser(1);
    }

    @Test
    public void mapUserBaseDTOTest(){
        UserBaseDTO userBaseDTOExpected = userMapper.mapUserBaseDTO(user);

        Assert.assertEquals(userBaseDTO.getIduser(), userBaseDTOExpected.getIduser());
    }

    @Test
    public void mapUserBaseDTOListTest(){
        List<UserBaseDTO> userBaseDTOSExpected = userMapper.mapUserBaseDTOList(new ArrayList<>());

        Assert.assertEquals(new ArrayList<>(), userBaseDTOSExpected);
    }

    @Test
    public void mapUserTest(){
        User userExpected = userMapper.mapUser(userBaseDTO);

        Assert.assertEquals(user.getIduser(), userExpected.getIduser());
    }
}
