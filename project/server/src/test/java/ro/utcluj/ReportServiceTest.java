package ro.utcluj;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import ro.utcluj.api.dto.UserBaseDTO;
import ro.utcluj.entity.User;
import ro.utcluj.mapper.UserMapper;
import ro.utcluj.repository.*;
import ro.utcluj.service.ReportService;

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ReportServiceTest {

    @Mock private UserProductRepository userProductRepository;
    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    private ReportService reportService;
    private User user;
    private UserBaseDTO userBaseDTO;


    @Before
    public void setup(){
        reportService = new ReportService(userRepository, userProductRepository, userMapper);
        userBaseDTO = new UserBaseDTO(1, "user", "user", "User1234.", "user@user.com", "Str. User", "0752324212", "admin", 3800.0);
        user = new User("user", "user", "User1234.", "user@user.com", "Str. User", "0752324212", "admin", 3800.0, null);
        user.setIduser(1);
    }

    @Test
    public void reportPDFTest(){
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        when(userMapper.mapUserBaseDTO(user)).thenReturn(userBaseDTO);
        when(userProductRepository.findByUser(user)).thenReturn(new ArrayList<>());

        reportService.report("C:\\Documents", "pdf");

        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(userRepository.findAll().size())).mapUserBaseDTO(any());
        verify(userProductRepository, times(userRepository.findAll().size())).findByUser(any());
    }

    @Test
    public void reportTXTTest(){
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        when(userMapper.mapUserBaseDTO(user)).thenReturn(userBaseDTO);
        when(userProductRepository.findByUser(user)).thenReturn(new ArrayList<>());

        reportService.report("C:\\Documents", "txt");

        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(userRepository.findAll().size())).mapUserBaseDTO(any());
        verify(userProductRepository, times(userRepository.findAll().size())).findByUser(any());
    }
}
