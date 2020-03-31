package ro.utcluj.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ro.utcluj.api.dto.FavoriteProductBaseDTO;
import ro.utcluj.api.dto.ProductBaseDTO;
import ro.utcluj.api.dto.UserBaseDTO;
import ro.utcluj.api.dto.UserProductBaseDTO;
import ro.utcluj.api.serviceInterface.FavoriteProductServiceInterface;
import ro.utcluj.api.serviceInterface.ProductServiceInterface;
import ro.utcluj.api.serviceInterface.UserProductServiceInterface;
import ro.utcluj.api.serviceInterface.UserServiceInterface;
import ro.utcluj.notification.NotificationService;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserInterfaceController {

    private UserServiceInterface userService;
    private ProductServiceInterface productService;
    private UserProductServiceInterface userProductService;
    private FavoriteProductServiceInterface favoriteProductService;
    private NotificationService notificationService;

    @Autowired
    private static UserBaseDTO loggedUser;

    private static ProductBaseDTO selectedProduct;
    private static ProductBaseDTO favProduct;


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
    public TableView<ProductBaseDTO> tableViewProduct;
    @FXML public TableColumn<ProductBaseDTO,String> nameColumn;
    @FXML public TableColumn<ProductBaseDTO,String> descriptionColumn;
    @FXML public TableColumn<ProductBaseDTO,Integer> quantityColumn;
    @FXML public TableColumn<ProductBaseDTO,Double> ratingColumn;
    @FXML public TableColumn<ProductBaseDTO,Double> priceColumn;

    @FXML
    public TableView<UserProductBaseDTO> tableViewUserProduct;
    @FXML public TableColumn<UserProductBaseDTO,String> userProductNameColumn;
    @FXML public TableColumn<UserProductBaseDTO,String> userProductDescriptionColumn;
    @FXML public TableColumn<UserProductBaseDTO,Double> userProductRatingColumn;
    @FXML public TableColumn<UserProductBaseDTO,Double> userProductPriceColumn;
    @FXML public TableColumn<UserProductBaseDTO,Date> userProductDateColumn;

    @FXML
    public TableView<FavoriteProductBaseDTO> tableViewFavoriteProduct;
    @FXML public TableColumn<FavoriteProductBaseDTO,String> userProductNameColumn1;
    @FXML public TableColumn<FavoriteProductBaseDTO,String> userProductDescriptionColumn1;
    @FXML public TableColumn<FavoriteProductBaseDTO,Double> userProductRatingColumn1;
    @FXML public TableColumn<FavoriteProductBaseDTO,Double> userProductPriceColumn1;
    @FXML public TableColumn<FavoriteProductBaseDTO,Date> userProductDateColumn1;

    @Autowired
    public UserInterfaceController(UserServiceInterface userService, ProductServiceInterface productService, FavoriteProductServiceInterface favoriteProductService,
                                   UserProductServiceInterface userProductService, ApplicationContext applicationContext, NotificationService notificationService){
        this.userService = userService;
        this.productService = productService;
        this.userProductService = userProductService;
        this.favoriteProductService = favoriteProductService;
        this.applicationContext = applicationContext;
        this.notificationService = notificationService;
    }

    public ObservableList<UserBaseDTO> mapToObservableUser(List<UserBaseDTO> list){
        ObservableList<UserBaseDTO> mappedList = FXCollections.observableArrayList();
        mappedList.addAll(list);
        return mappedList;
    }

    private ObservableList<ProductBaseDTO> mapToObservableProduct(List<ProductBaseDTO> list){
        ObservableList<ProductBaseDTO> mappedList = FXCollections.observableArrayList();
        mappedList.addAll(list);
        return mappedList;
    }

    private ObservableList<UserProductBaseDTO> mapToObservable(List<UserProductBaseDTO> list){
        ObservableList<UserProductBaseDTO> mappedList = FXCollections.observableArrayList();
        mappedList.addAll(list);
        return mappedList;
    }

    private ObservableList<FavoriteProductBaseDTO> mapToObservableFav(List<FavoriteProductBaseDTO> list){
        ObservableList<FavoriteProductBaseDTO> mappedList = FXCollections.observableArrayList();
        mappedList.addAll(list);
        return mappedList;
    }

    void init(UserBaseDTO user) {
        loggedUser = user;
        System.out.println(loggedUser);
        currentUsernameText.setText("Hi, " + loggedUser.getUsername() + "!");
        currentSoldText.setText(String.valueOf(user.getBalance_account()));

        nameColumn.setCellValueFactory(new PropertyValueFactory<ProductBaseDTO, String>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<ProductBaseDTO, String>("description"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<ProductBaseDTO, Integer>("quantity"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<ProductBaseDTO, Double>("rating"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<ProductBaseDTO, Double>("price"));
        tableViewProduct.setItems(mapToObservableProduct(productService.getProducts()));

        userProductNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getProduct().getName()));
        userProductDescriptionColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getProduct().getDescription()));
        userProductRatingColumn.setCellValueFactory(param -> new SimpleDoubleProperty(((CellDataFeatures<UserProductBaseDTO, Double>) param).getValue().getProduct().getRating()).asObject());
        userProductPriceColumn.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getProduct().getPrice()).asObject());
        userProductDateColumn.setCellValueFactory(new PropertyValueFactory<UserProductBaseDTO, Date>("date"));
        tableViewUserProduct.setItems(mapToObservable(userProductService.getAll(loggedUser)));

        userProductNameColumn1.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getProduct().getName()));
        userProductDescriptionColumn1.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getProduct().getDescription()));
        userProductRatingColumn1.setCellValueFactory(param -> new SimpleDoubleProperty(((CellDataFeatures<FavoriteProductBaseDTO, Double>) param).getValue().getProduct().getRating()).asObject());
        userProductPriceColumn1.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getProduct().getPrice()).asObject());
        userProductDateColumn1.setCellValueFactory(new PropertyValueFactory<FavoriteProductBaseDTO, Date>("date"));
        tableViewFavoriteProduct.setItems(mapToObservableFav(favoriteProductService.getAll(loggedUser)));

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
        tableViewProduct.setItems(mapToObservableProduct(productService.filterProducts(nameFilterField.getText(), descriptionFilterField.getText())));
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
    private void handleAddFavProductButton(Event event){
        favoriteProductService.addFavoriteProduct(loggedUser.getIduser(), selectedProduct.getIdproduct());
        tableViewFavoriteProduct.setItems(mapToObservableFav(favoriteProductService.getAll(loggedUser)));
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
            tableViewProduct.setItems(mapToObservableProduct(productService.getProducts()));
            tableViewUserProduct.setItems(mapToObservable(userProductService.getAll(loggedUser)));
            loggedUser = userService.getUser(loggedUser.getIduser());
            currentSoldText.setText(String.valueOf(loggedUser.getBalance_account()));
            balanceAccountField.setText(String.valueOf(loggedUser.getBalance_account()));
        }
    }

    @FXML
    private void handleFavProductButton(Event event){
        favProduct = tableViewFavoriteProduct.getSelectionModel().selectedItemProperty().get().getProduct();
    }

    @FXML
    private void handleDeleteProductButton(Event event){
        favoriteProductService.deleteFavoriteProduct(loggedUser.getIduser(), favProduct.getIdproduct());
        tableViewFavoriteProduct.setItems(mapToObservableFav(favoriteProductService.getAll(loggedUser)));
    }

    @FXML
    private void handleLogoutButton(Event event){
        try {
            notificationService.sendMessageToServer("Logout " + loggedUser.getIduser());
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserInterfaceController.loggedUser = null;
        sendToAnotherPage(event,"/login.fxml", "Welcome");
    }

    @FXML
    private void handleRefreshButton(Event event){
        tableViewProduct.setItems(mapToObservableProduct(productService.getProducts()));
        tableViewUserProduct.setItems(mapToObservable(userProductService.getAll(loggedUser)));
        tableViewFavoriteProduct.setItems(mapToObservableFav(favoriteProductService.getAll(loggedUser)));

    }
}
