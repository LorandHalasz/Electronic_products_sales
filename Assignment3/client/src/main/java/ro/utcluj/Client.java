package ro.utcluj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import ro.utcluj.api.serviceInterface.FavoriteProductServiceInterface;
import ro.utcluj.api.serviceInterface.ProductServiceInterface;
import ro.utcluj.api.serviceInterface.UserProductServiceInterface;
import ro.utcluj.api.serviceInterface.UserServiceInterface;

@SpringBootApplication
public class Client extends Application {

    private FXMLLoader fxmlLoader;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void init() throws Exception{
        ApplicationContext context = SpringApplication.run(Client.class);
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(context::getBean);
    }

    @Override
    public void start(Stage stage) throws Exception {
        fxmlLoader.setLocation(getClass().getResource("/login.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.setTitle("Welcome");
        stage.setScene(scene);
        stage.show();
    }

    @Bean
    public HttpInvokerProxyFactoryBean userServiceProxy(){
        HttpInvokerProxyFactoryBean proxy = new HttpInvokerProxyFactoryBean();
        proxy.setServiceInterface(UserServiceInterface.class);
        proxy.setServiceUrl("http://localhost:1234/userService");
        return proxy;
    }

    @Bean
    public HttpInvokerProxyFactoryBean productServiceProxy(){
        HttpInvokerProxyFactoryBean proxy = new HttpInvokerProxyFactoryBean();
        proxy.setServiceInterface(ProductServiceInterface.class);
        proxy.setServiceUrl("http://localhost:1234/productService");
        return proxy;
    }

    @Bean
    public HttpInvokerProxyFactoryBean userProductServiceProxy(){
        HttpInvokerProxyFactoryBean proxy = new HttpInvokerProxyFactoryBean();
        proxy.setServiceInterface(UserProductServiceInterface.class);
        proxy.setServiceUrl("http://localhost:1234/userProductService");
        return proxy;
    }

    @Bean
    public HttpInvokerProxyFactoryBean favoriteProductServiceProxy(){
        HttpInvokerProxyFactoryBean proxy = new HttpInvokerProxyFactoryBean();
        proxy.setServiceInterface(FavoriteProductServiceInterface.class);
        proxy.setServiceUrl("http://localhost:1234/favoriteProductService");
        return proxy;
    }

}
