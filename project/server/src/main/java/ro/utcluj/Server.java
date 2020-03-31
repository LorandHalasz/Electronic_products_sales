package ro.utcluj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import ro.utcluj.api.serviceInterface.*;
import ro.utcluj.service.*;


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

    @Bean(name = "/messageService")
    public HttpInvokerServiceExporter messageServiceExporter(MessageService messageService){
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(messageService);
        exporter.setServiceInterface(MessageServiceInterface.class);
        return exporter;
    }

    @Bean(name = "/limitedStockService")
    public HttpInvokerServiceExporter limitedStockServiceExporter(LimitedStockService limitedStockService){
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(limitedStockService);
        exporter.setServiceInterface(LimitedStockServiceInterface.class);
        return exporter;
    }

    @Bean(name = "/reportService")
    public HttpInvokerServiceExporter reportServiceExporter(ReportService reportService){
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(reportService);
        exporter.setServiceInterface(ReportServiceInterface.class);
        return exporter;
    }


}
