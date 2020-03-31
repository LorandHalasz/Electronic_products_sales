package ro.utcluj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import ro.utcluj.api.serviceInterface.FavoriteProductServiceInterface;
import ro.utcluj.api.serviceInterface.ProductServiceInterface;
import ro.utcluj.api.serviceInterface.UserProductServiceInterface;
import ro.utcluj.api.serviceInterface.UserServiceInterface;
import ro.utcluj.service.FavoriteProductService;
import ro.utcluj.service.ProductService;
import ro.utcluj.service.UserProductService;
import ro.utcluj.service.UserService;


import java.sql.SQLException;

/**
 * Hello world!
 */
@SpringBootApplication
public class Server {

    public static void main(String[] args) throws SQLException {
        ApplicationContext context = SpringApplication.run(Server.class, args);
    }

    @Bean(name = "/userService")
    public HttpInvokerServiceExporter userServiceExporter(UserService userService){
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(userService);
        exporter.setServiceInterface(UserServiceInterface.class);
        return exporter;
    }

    @Bean(name = "/productService")
    public HttpInvokerServiceExporter productServiceExporter(ProductService productService){
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(productService);
        exporter.setServiceInterface(ProductServiceInterface.class);
        return exporter;
    }

    @Bean(name = "/userProductService")
    public HttpInvokerServiceExporter userProductServiceExporter(UserProductService userProductService){
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(userProductService);
        exporter.setServiceInterface(UserProductServiceInterface.class);
        return exporter;
    }

    @Bean(name = "/favoriteProductService")
    public HttpInvokerServiceExporter favoriteProductServiceExporter(FavoriteProductService favoriteProductService){
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(favoriteProductService);
        exporter.setServiceInterface(FavoriteProductServiceInterface.class);
        return exporter;
    }


}
