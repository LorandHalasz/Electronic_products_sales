package utcn.ps.assignment2.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn.CellDataFeatures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import utcn.ps.assignment2.entity.Product;
import utcn.ps.assignment2.entity.User;
import utcn.ps.assignment2.entity.UserProduct;
import utcn.ps.assignment2.service.ProductService;
import utcn.ps.assignment2.service.UserProductService;
import utcn.ps.assignment2.service.UserService;

import java.io.IOException;
import java.sql.Date;
import java.util.regex.Pattern;

@Service
public class UserInterfaceController {

    private UserService userService;
    private ProductService productService;
    private UserProductService userProductService;

    private static User loggedUser;
    private static Product selectedProduct;

    private ApplicationContext applicationContext;

    @FXML private Text currentUsernameText;
    @FXML private Text currentSoldText;
    @FXML private Text errorMessageText;

    @FXML private TextField nameFilterField;
    @FXML private TextField descriptionFilterField;

    @FXML private TextField nameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField addressField;
    @FXML private TextField emailField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField balanceAccountField;

    @FXML
    public TableView<Product> tableViewProduct;
    @FXML public TableColumn<Product,String> nameColumn;
    @FXML public TableColumn<Product,String> descriptionColumn;
    @FXML public TableColumn<Product,Integer> quantityColumn;
    @FXML public TableColumn<Product,Double> ratingColumn;
    @FXML public TableColumn<Product,Double> priceColumn;

    @FXML
    public TableView<UserProduct> tableViewUserProduct;
    @FXML public TableColumn<UserProduct,String> userProductNameColumn;
    @FXML public TableColumn<UserProduct,String> userProductDescriptionColumn;
    @FXML public TableColumn<UserProduct,Double> userProductRatingColumn;
    @FXML public TableColumn<UserProduct,Double> userProductPriceColumn;
    @FXML public TableColumn<UserProduct,Date> userProductDateColumn;

    @Autowired
    public UserInterfaceController(UserService userService, ProductService productService, UserProductService userProductService, ApplicationContext applicationContext){
        this.userService = userService;
        this.productService = productService;
        this.userProductService = userProductService;
        this.applicationContext = applicationContext;
    }

    void init(User user) {
        loggedUser = user;
        System.out.println(loggedUser);
        currentUsernameText.setText("Hi, " + loggedUser.getUsername() + "!");
        currentSoldText.setText(String.valueOf(user.getBalance_account()));

        nameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("rating"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        tableViewProduct.setItems(productService.getProducts());

        userProductNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getProduct().getName()));
        userProductDescriptionColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getProduct().getDescription()));
        userProductRatingColumn.setCellValueFactory(param -> new SimpleDoubleProperty(((CellDataFeatures<UserProduct, Double>) param).getValue().getProduct().getRating()).asObject());
        userProductPriceColumn.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getProduct().getPrice()).asObject());
        userProductDateColumn.setCellValueFactory(new PropertyValueFactory<UserProduct, Date>("date"));
        tableViewUserProduct.setItems(userProductService.getAll(loggedUser));

        nameField.setText(loggedUser.getName());
        usernameField.setText(loggedUser.getUsername());
        passwordField.setText(loggedUser.getPassword());
        addressField.setText(loggedUser.getAddress());
        emailField.setText(loggedUser.getEmail());
        phoneNumberField.setText(loggedUser.getPhone_number());
        balanceAccountField.setText(String.valueOf(loggedUser.getBalance_account()));
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
    private void handleFilterButton(){
        tableViewProduct.setItems(productService.filterProducts(nameFilterField.getText(), descriptionFilterField.getText()));
    }

    private Boolean areNotUserFieldsEmpty(){
        return !nameField.getText().isEmpty() && !usernameField.getText().isEmpty() && !passwordField.getText().isEmpty() &&
                !addressField.getText().isEmpty() && !phoneNumberField.getText().isEmpty() && !balanceAccountField.getText().isEmpty();
    }

    @FXML
    private void handleEditDataButton(Event event){
        try{
            if (areNotUserFieldsEmpty()) {
                if (phoneNumberField.getText().matches(".*[a-z].*") || phoneNumberField.getText().matches(".*[A-Z].*") || phoneNumberField.getText().length() != 10) {
                    errorMessageText.setText("Phone number is incorrect!");
                    errorMessageText.setFill(Color.RED);
                }
                else {
                    String regex = "^(?=.*?\\p{Lu})(?=.*?\\p{Ll})(?=.*?\\d)" +
                            "(?=.*?[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]).*$";
                    if (!Pattern.compile(regex).matcher(passwordField.getText()).matches() && !passwordField.getText().equals(loggedUser.getPassword())) {
                        errorMessageText.setText("Password doesn't contain at least 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character!");
                        errorMessageText.setFill(Color.RED);
                    }
                    else {
                        if(Double.parseDouble(balanceAccountField.getText()) >= 0){
                            loggedUser = userService.updateUser(loggedUser.getIduser(), nameField.getText(), usernameField.getText(), passwordField.getText(), emailField.getText(),
                                addressField.getText(), phoneNumberField.getText(), "user", Double.parseDouble(balanceAccountField.getText()));
                            errorMessageText.setText("Update Successful!");
                            errorMessageText.setFill(Color.GREEN);
                            currentSoldText.setText(String.valueOf(loggedUser.getBalance_account()));
                        }
                        else{
                            errorMessageText.setText("Balance Account can't be a negative number!");
                            errorMessageText.setFill(Color.RED);
                        }
                    }
                }
            }
            else {
                errorMessageText.setText("All fields must be completed!");
                errorMessageText.setFill(Color.RED);
            }
        }catch (NumberFormatException e){
            errorMessageText.setText("Balance account must be a number!");
            errorMessageText.setFill(Color.RED);
        }
    }

    @FXML
    private void handleDeleteButton(Event event){
        userService.deleteUser(loggedUser.getIduser());
        sendToAnotherPage(event,"/login.fxml", "Welcome!");
    }

    @FXML
    private void handleProductSelected(Event event){
        selectedProduct = tableViewProduct.getSelectionModel().selectedItemProperty().get();
    }

    @FXML
    private void handleBuyProductButton(Event event){
        Integer result = userProductService.addUserProduct(loggedUser.getIduser(), selectedProduct.getIdproduct());
        System.out.println(result);
        if (result == -1){
            errorMessageText.setText("Out of Stock");
            errorMessageText.setFill(Color.RED);
        }
        else
            if (result == -2) {
                errorMessageText.setText("You don't have enough money!");
                errorMessageText.setFill(Color.RED);
            }
            else
                if(result == 0){
                    errorMessageText.setText("Successful order!");
                    errorMessageText.setFill(Color.GREEN);
                    tableViewProduct.setItems(productService.getProducts());
                    tableViewUserProduct.setItems(userProductService.getAll(loggedUser));
                    loggedUser = userService.getUser(loggedUser.getIduser());
                    currentSoldText.setText(String.valueOf(loggedUser.getBalance_account()));
                    balanceAccountField.setText(String.valueOf(loggedUser.getBalance_account()));
                }
    }

    @FXML
    private void handleLogoutButton(Event event){
        sendToAnotherPage(event,"/login.fxml", "Welcome");
    }
}
