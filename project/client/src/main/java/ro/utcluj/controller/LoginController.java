package ro.utcluj.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ro.utcluj.api.dto.UserBaseDTO;
import ro.utcluj.api.serviceInterface.UserServiceInterface;
import ro.utcluj.notification.NotificationService;

import java.io.IOException;


@Service
public class LoginController {

    private UserServiceInterface userService;

    @Autowired
    public static UserBaseDTO loggedUser;

    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Text loginErrorMessage;

    private ApplicationContext applicationContext;
    private NotificationService notificationService;

    @Autowired
    public LoginController(UserServiceInterface userService, ApplicationContext applicationContext, NotificationService notificationService){
        this.userService = userService;
        this.applicationContext = applicationContext;
        this.notificationService = notificationService;
    }

    public LoginController(UserServiceInterface userService, TextField usernameField, PasswordField passwordField, Text loginErrorMessage, ApplicationContext applicationContext, NotificationService notificationService) {
        this.userService = userService;
        this.usernameField = usernameField;
        this.passwordField = passwordField;
        this.loginErrorMessage = loginErrorMessage;
        this.applicationContext = applicationContext;
        this.notificationService = notificationService;
    }

    private void sendToAnotherPage(Event event, String pageName, String pageTitle){
        Parent root = null;
        try {
            FXMLLoader fxmlLoader;
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            fxmlLoader.setLocation(getClass().getResource(pageName));

            root = fxmlLoader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);

            if (pageName.contains("user") && loggedUser.getUser_role().equalsIgnoreCase("user")){
                UserInterfaceController controller = fxmlLoader.getController();
                controller.init(loggedUser);
            }

            if (pageName.contains("admin") && loggedUser.getUser_role().equalsIgnoreCase("admin")){
                AdminInterfaceController controller = fxmlLoader.getController();
                controller.init(loggedUser);
            }

            (((Node)event.getSource())).getScene().getWindow().hide();
            stage.setTitle(pageTitle);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleLoginButton(Event event){

        String userName = usernameField.getText();
        String password = passwordField.getText();
        UserBaseDTO user = userService.loginUser(userName,password);
        if (user != null) {
            LoginController.loggedUser = user;
            if(user.getUser_role().equalsIgnoreCase("admin"))
                sendToAnotherPage(event,"/adminInterface.fxml", "Admin");
            else
                sendToAnotherPage(event,"/userInterface.fxml", "User");

            notificationService.connectToNotificationServer(loggedUser.getName());
            try {
                notificationService.sendMessageToServer("Login " + loggedUser.getIduser());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {
            loginErrorMessage.setText("Username or password incorrect");
        }
    }

    @FXML
    private void handleRegisterButton(Event event){
        sendToAnotherPage(event,"/register.fxml", "Welcome");
    }

}
