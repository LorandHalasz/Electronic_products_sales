package utcn.ps.assignment2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import utcn.ps.assignment2.entity.Product;
import utcn.ps.assignment2.entity.User;
import utcn.ps.assignment2.entity.UserProduct;
import utcn.ps.assignment2.repository.ProductRepository;
import utcn.ps.assignment2.repository.UserProductRepository;
import utcn.ps.assignment2.repository.UserRepository;
import utcn.ps.assignment2.service.UserProductService;

import java.sql.Date;
import java.util.HashSet;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserProductServiceTest {
    @Mock
    private UserProductRepository repository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;

    private UserProductService service;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private UserProduct userProduct;

    @Before
    public void setup(){
        service = new UserProductService(userRepository, productRepository, repository);
        userProduct = new UserProduct( new User("", "", "", "", "", "", "", 0.0, new HashSet<UserProduct>()), new Product("", "", 0.0, 0, 0.0), Date.valueOf("2019-01-01"));
    }

    @Test
    public void getAllTest(){
        when(repository.findAll()).thenReturn(FXCollections.observableArrayList());

        ObservableList<UserProduct> userReturned = service.getAll();

        verify(repository, times(1)).findAll();

        Assert.assertEquals(userReturned, FXCollections.observableArrayList());
    }
}
