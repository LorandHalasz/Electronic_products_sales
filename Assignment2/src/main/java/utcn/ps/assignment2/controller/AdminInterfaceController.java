package utcn.ps.assignment2.controller;

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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import utcn.ps.assignment2.entity.Product;
import utcn.ps.assignment2.entity.User;
import utcn.ps.assignment2.entity.UserProduct;
import utcn.ps.assignment2.service.ProductService;
import utcn.ps.assignment2.service.UserProductService;
import utcn.ps.assignment2.service.UserService;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@Service
public class AdminInterfaceController {

    private UserService userService;
    private ProductService productService;
    private UserProductService userProductService;

    private static User loggedUser;
    private static User selectedUser;
    private ApplicationContext applicationContext;

    @FXML RadioButton radioPDFButton;
    @FXML RadioButton radioDocButton;

    @FXML private TableView<User> tableViewUser;
    @FXML private TableColumn<User,String> nameColumn;
    @FXML private TableColumn<User,String> usernameColumn;
    @FXML private TableColumn<User,String> passwordColumn;
    @FXML private TableColumn<User,String> emailColumn;
    @FXML private TableColumn<User,String> addressColumn;
    @FXML private TableColumn<User,String> phoneNumberColumn;
    @FXML private TableColumn<User,String> userRoleColumn;
    @FXML private TableColumn<User,Double> balanceAccountColumn;

    @FXML
    public TableView<Product> tableViewProduct;
    @FXML public TableColumn<Product,String> productNameColumn;
    @FXML public TableColumn<Product,String> descriptionColumn;
    @FXML public TableColumn<Product,Integer> quantityColumn;
    @FXML public TableColumn<Product,Double> ratingColumn;
    @FXML public TableColumn<Product,Double> priceColumn;

    @FXML
    public TableView<UserProduct> tableViewUserProduct;
    @FXML public TableColumn<UserProduct,String> userProductUsernameColumn;
    @FXML public TableColumn<UserProduct,String> userProductNameColumn;
    @FXML public TableColumn<UserProduct,String> userProductDescriptionColumn;
    @FXML public TableColumn<UserProduct,Double> userProductRatingColumn;
    @FXML public TableColumn<UserProduct,Double> userProductPriceColumn;
    @FXML public TableColumn<UserProduct,Date> userProductDateColumn;

    @FXML public TextField nameField;
    @FXML public TextField usernameField;
    @FXML public PasswordField passwordField;
    @FXML public TextField emailField;
    @FXML public TextField addressField;
    @FXML public TextField phoneNumberField;
    @FXML public TextField balanceAccountField;

    @FXML public Button createUserButton;
    @FXML public Button updateModeUserButton;
    @FXML public Button updateUserButton;
    @FXML public Button cancelUpdateUserButton;
    @FXML public Button deleteUserButton;

    @FXML public ComboBox<String> userRoleComboBox;

    @FXML public Text currentUsernameText;
    @FXML public Text errorMessageText;

    @Autowired
    public AdminInterfaceController(UserService userService, ProductService productService, UserProductService userProductService, ApplicationContext applicationContext){
        this.userService = userService;
        this.productService = productService;
        this.userProductService = userProductService;
        this.applicationContext = applicationContext;
    }

    void init(User user) {
        loggedUser = user;
        System.out.println(loggedUser);
        currentUsernameText.setText("Hi, " + loggedUser.getUsername() + "!");

        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<User, String>("address"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<User, String>("phone_number"));
        userRoleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("user_role"));
        balanceAccountColumn.setCellValueFactory(new PropertyValueFactory<User, Double>("balance_account"));
        tableViewUser.setItems(userService.getAll());

        nameField.setText(loggedUser.getName());
        usernameField.setText(loggedUser.getUsername());
        passwordField.setText(loggedUser.getPassword());
        emailField.setText(loggedUser.getEmail());
        addressField.setText(loggedUser.getAddress());
        phoneNumberField.setText(loggedUser.getPhone_number());
        balanceAccountField.setText(String.valueOf(loggedUser.getBalance_account()));

        ObservableList<String> cmbUserRole = FXCollections.observableArrayList();
        cmbUserRole.add("user");
        cmbUserRole.add("admin");
        userRoleComboBox.setItems(cmbUserRole);

        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("rating"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        tableViewProduct.setItems(productService.getProducts());

        userProductUsernameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUser().getUsername()));
        userProductNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getProduct().getName()));
        userProductDescriptionColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getProduct().getDescription()));
        userProductRatingColumn.setCellValueFactory(param -> new SimpleDoubleProperty(((TableColumn.CellDataFeatures<UserProduct, Double>) param).getValue().getProduct().getRating()).asObject());
        userProductPriceColumn.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getProduct().getPrice()).asObject());
        userProductDateColumn.setCellValueFactory(new PropertyValueFactory<UserProduct, Date>("date"));
        tableViewUserProduct.setItems(userProductService.getAll());

        updateUserButton.setDisable(true);
        cancelUpdateUserButton.setDisable(true);
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
    public void setRadioPDFButton(){
        if (radioPDFButton.isSelected())
            radioDocButton.setSelected(false);
        else
            radioDocButton.setSelected(true);
    }

    @FXML
    public void setRadioDocButton(){
        if (radioDocButton.isSelected())
            radioPDFButton.setSelected(false);
        else
            radioPDFButton.setSelected(true);
    }

    @FXML
    public void selectUser() {
        selectedUser = tableViewUser.getSelectionModel().selectedItemProperty().get();
        nameField.setText(loggedUser.getName());
        usernameField.setText(loggedUser.getUsername());
        passwordField.setText(loggedUser.getPassword());
        emailField.setText(loggedUser.getEmail());
        addressField.setText(loggedUser.getAddress());
        phoneNumberField.setText(loggedUser.getPhone_number());
        balanceAccountField.setText(String.valueOf(loggedUser.getBalance_account()));
        userRoleComboBox.setValue(loggedUser.getUser_role());
    }

    @FXML
    public void generateReportButton(Event event){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(new Stage());
        String pathToSave = selectedDirectory.getAbsolutePath();

        String reportType = "";

        if(radioPDFButton.isSelected())
            reportType = "PDF";
        else
            reportType = "TXT";

        userProductService.report(pathToSave, reportType);
        errorMessageText.setText("Generate successfully!");
        errorMessageText.setFill(Color.GREEN);
    }

    private Boolean areNotUserFieldsEmpty(){
        return !nameField.getText().isEmpty() && !usernameField.getText().isEmpty() && !passwordField.getText().isEmpty() &&
                !addressField.getText().isEmpty() && !phoneNumberField.getText().isEmpty() && !balanceAccountField.getText().isEmpty() &&
                !userRoleComboBox.getSelectionModel().isEmpty();
    }

    @FXML
    public void buttonCreateUserPressed() throws SQLException {
        try{
            if (areNotUserFieldsEmpty()) {
                if(phoneNumberField.getText().matches(".*[a-z].*") || phoneNumberField.getText().matches(".*[A-Z].*"))
                    errorMessageText.setText("Phone number is incorrect");
                else {
                    userService.addUser(nameField.getText(), usernameField.getText(), passwordField.getText(), emailField.getText(),
                            addressField.getText(), phoneNumberField.getText(), userRoleComboBox.getValue(), Double.parseDouble(balanceAccountField.getText()));
                    tableViewUser.setItems(userService.getAll());
                    errorMessageText.setText("");
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
    public void buttonUpdateUserModePressed() {
        createUserButton.setDisable(true);
        updateModeUserButton.setDisable(true);
        updateUserButton.setDisable(false);
        cancelUpdateUserButton.setDisable(false);
        deleteUserButton.setDisable(true);
        nameField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        addressField.setText("");
        phoneNumberField.setText("");
        balanceAccountField.setText("");
        balanceAccountField.setDisable(true);
        userRoleComboBox.setValue("");
    }

    @FXML
    public void buttonCancelUpdateUserPressed() {
        createUserButton.setDisable(false);
        updateModeUserButton.setDisable(false);
        updateUserButton.setDisable(true);
        cancelUpdateUserButton.setDisable(true);
        deleteUserButton.setDisable(false);
        nameField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        addressField.setText("");
        phoneNumberField.setText("");
        balanceAccountField.setText("");
        balanceAccountField.setDisable(false);
        userRoleComboBox.setValue("");
    }

    @FXML
    public void buttonUpdateUserPressed() throws SQLException {
        try{
            if (areNotUserFieldsEmpty()) {
                if(phoneNumberField.getText().matches(".*[a-z].*") || phoneNumberField.getText().matches(".*[A-Z].*"))
                    errorMessageText.setText("Phone number is incorrect");
                else {
                    userService.updateUser(loggedUser.getIduser(), nameField.getText(), usernameField.getText(), passwordField.getText(), emailField.getText(),
                            addressField.getText(), phoneNumberField.getText(), userRoleComboBox.getValue(), Double.parseDouble(balanceAccountField.getText()));
                    errorMessageText.setText("");
                    tableViewUser.setItems(userService.getAll());
                    createUserButton.setDisable(false);
                    updateModeUserButton.setDisable(false);
                    updateUserButton.setDisable(true);
                    cancelUpdateUserButton.setDisable(true);
                    deleteUserButton.setDisable(false);
                    nameField.setText("");
                    usernameField.setText("");
                    passwordField.setText("");
                    addressField.setText("");
                    phoneNumberField.setText("");
                    balanceAccountField.setText("");
                    balanceAccountField.setDisable(false);
                    userRoleComboBox.setValue("");
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
    public void buttonDeleteUserPressed() throws SQLException {

        userProductService.deleteUserProduct(selectedUser.getIduser());
        userService.deleteUser(selectedUser.getIduser());
        tableViewUser.setItems(userService.getAll());
        tableViewUserProduct.setItems(userProductService.getAll());
    }

    @FXML
    private void handleLogoutButton(Event event){
        sendToAnotherPage(event,"/login.fxml", "Welcome");
    }
}
