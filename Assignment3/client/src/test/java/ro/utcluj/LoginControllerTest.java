package ro.utcluj;

import de.saxsys.javafx.test.JfxRunner;
import javafx.event.Event;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.context.ApplicationContext;
import ro.utcluj.api.dto.UserBaseDTO;
import ro.utcluj.api.serviceInterface.UserServiceInterface;
import ro.utcluj.controller.LoginController;
import ro.utcluj.notification.NotificationService;

import java.io.IOException;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JfxRunner.class)
public class LoginControllerTest {
    @Mock
    private UserServiceInterface userServiceInterface;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private Event event;

    @Mock
    private NotificationService notificationService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private LoginController loginController;

    @Before
    public void start(){
        loginController = new LoginController(userServiceInterface, new TextField(), new PasswordField(), new Text(), applicationContext, notificationService);
    }

    @Test
    public void loginTest() throws IOException {

        UserBaseDTO user = new UserBaseDTO(1, "user", "user", "uU123456.", "42@fsa", "dsadsa", "0472138213", "admin", 1.2);

        when(userServiceInterface.loginUser(anyString(),anyString())).thenReturn(user);
        loginController.usernameField.setText(anyString());
        loginController.passwordField.setText(anyString());

        LoginController.loggedUser = new UserBaseDTO(1, "user", "user", "uU123456.", "42@fsa", "dsadsa", "0472138213", "admin", 1.2);
        loginController.handleLoginButton(event);
        verify(userServiceInterface,times(1)).loginUser(anyString(),anyString());

    }
}
