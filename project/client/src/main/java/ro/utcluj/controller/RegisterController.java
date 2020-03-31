package ro.utcluj.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ro.utcluj.api.serviceInterface.UserServiceInterface;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

@Service
public class RegisterController {

    private UserServiceInterface userService;

    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;

    @FXML
    public TextField nameField;
    @FXML
    public PasswordField confirmPasswordField;
    @FXML
    public TextField emailField;
    @FXML
    public TextField addressField;
    @FXML
    public TextField phoneNumberField;
    @FXML
    public TextField balanceAccountField;
    @FXML
    public Text errorMessageText;

    @FXML
    public Text passwordDoesNotMatchText;
    @FXML
    public Text passwordStrongEnounghText;

    private ApplicationContext applicationContext;

    @Autowired
    public RegisterController(UserServiceInterface userService, ApplicationContext applicationContext){
        this.userService = userService;
        this.applicationContext = applicationContext;
    }
    private Boolean ok = false;

    @FXML
    public void passwordConf() {
        if (!passwordField.getText().isEmpty() && !confirmPasswordField.getText().isEmpty()) {
            if (!passwordField.getText().equals(confirmPasswordField.getText())) {
                passwordDoesNotMatchText.setText("Password doesn't match");
                passwordDoesNotMatchText.setFill(Color.RED);
            } else {
                if (ok) {
                    passwordDoesNotMatchText.setText("Password matches");
                    passwordDoesNotMatchText.setFill(Color.GREEN);
                }
            }
        }
        else
        if(!passwordField.getText().isEmpty()){
            ok = true;
            if (passwordField.getText().length() > 8) {
                String regex = "^(?=.*?\\p{Lu})(?=.*?\\p{Ll})(?=.*?\\d)" +
                        "(?=.*?[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]).*$";
                if (!Pattern.compile(regex).matcher(passwordField.getText()).matches()) {
                    passwordStrongEnounghText.setText("Password doesn't contain at least 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character!");
                    passwordStrongEnounghText.setFill(Color.RED);
                }
                else
                    passwordStrongEnounghText.setText("");
            }
            else {
                passwordStrongEnounghText.setText("Password isn't strong enough");
                passwordStrongEnounghText.setFill(Color.RED);
            }
        }
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

            (((Node)event.getSource())).getScene().getWindow().hide();
            stage.setTitle(pageTitle);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void buttonRegisteredPressed(Event event) throws IOException, SQLException {
        try {
            if (!nameField.getText().isEmpty() && !usernameField.getText().isEmpty() && !passwordField.getText().isEmpty() &&
                    !confirmPasswordField.getText().isEmpty() && !emailField.getText().isEmpty() && !addressField.getText().isEmpty() && !phoneNumberField.getText().isEmpty() &&
                    !balanceAccountField.getText().isEmpty()) {
                if(phoneNumberField.getText().matches(".*[a-z].*") || phoneNumberField.getText().matches(".*[A-Z].*"))
                    errorMessageText.setText("Phone number is incorrect");
                else
                {
                    if (passwordField.getText().equals(confirmPasswordField.getText())) {
                        if (ok) {
                            userService.addUser(nameField.getText(), usernameField.getText(), passwordField.getText(), emailField.getText(),
                                    addressField.getText(), phoneNumberField.getText(), "user", Double.parseDouble(balanceAccountField.getText()));
                            sendToAnotherPage(event,"/login.fxml", "Welcome");
                            errorMessageText.setText("");
                        }
                    } else {
                        errorMessageText.setText("Password doesn't match");
                        errorMessageText.setFill(Color.RED);
                    }
                }
            }
            else {
                errorMessageText.setText("All fields must be completed");
                errorMessageText.setFill(Color.RED);
            }
        }catch (NumberFormatException e){
            errorMessageText.setText("Balance account must be a number");
        }
    }

    @FXML
    public void buttonBackPressed(Event event) throws IOException {
        sendToAnotherPage(event,"/login.fxml", "Welcome");
    }
}
