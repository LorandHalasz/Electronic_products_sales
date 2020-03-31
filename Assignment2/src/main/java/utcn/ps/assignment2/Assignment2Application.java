package utcn.ps.assignment2;


import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.*;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import utcn.ps.assignment2.service.UserService;


@SpringBootApplication
public class Assignment2Application extends Application {
    private FXMLLoader fxmlLoader;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception{
        ApplicationContext context = SpringApplication.run(Assignment2Application.class);
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(context::getBean);
    }

    @Override
    public void start(Stage stage) throws Exception {
        fxmlLoader.setLocation(getClass().getResource("/login.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.setTitle("Welcome!");
        stage.setScene(scene);
        stage.show();
    }

}
