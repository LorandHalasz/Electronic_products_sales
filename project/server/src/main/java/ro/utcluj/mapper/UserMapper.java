package ro.utcluj.mapper;

import org.springframework.stereotype.Component;
import ro.utcluj.api.dto.UserBaseDTO;
import ro.utcluj.entity.User;

import java.util.ArrayList;
import java.util.List;


@Component
public class UserMapper {

    public UserBaseDTO mapUserBaseDTO(User user){
        if (user != null)
            return new UserBaseDTO(user.getIduser(),user.getName(),user.getUsername(),user.getPassword(),user.getEmail(), user.getAddress(), user.getPhone_number(), user.getUser_role(), user.getBalance_account());
        else
            return null;
    }

    public List<UserBaseDTO> mapUserBaseDTOList(List<User> list){
        List<UserBaseDTO> mappedList = new ArrayList<>();
        for(User user : list){
            mappedList.add(mapUserBaseDTO(user));
        }

        return mappedList;
    }

    public User mapUser(UserBaseDTO userBaseDTO) {
        User user = new User();
        user.setIduser(userBaseDTO.getIduser());
        user.setName(userBaseDTO.getName());
        user.setUsername(userBaseDTO.getUsername());
        user.setPassword(userBaseDTO.getPassword());
        user.setEmail(userBaseDTO.getEmail());
        user.setAddress(userBaseDTO.getAddress());
        user.setPhone_number(userBaseDTO.getPhone_number());
        user.setUser_role(userBaseDTO.getUser_role());
        user.setBalance_account(userBaseDTO.getBalance_account());

        return user;
    }
}
