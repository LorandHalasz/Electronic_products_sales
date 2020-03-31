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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ro.utcluj.api.dto.*;
import ro.utcluj.api.serviceInterface.*;
import ro.utcluj.notification.NotificationService;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@Service
public class AdminInterfaceController {

    private UserServiceInterface userService;
    private ProductServiceInterface productService;
    private UserProductServiceInterface userProductService;

    @Autowired
    private static UserBaseDTO loggedUser;
    @Autowired
    private static UserBaseDTO selectedUser;
    @Autowired
    private static MessageBaseDTO selectedMessage;

    private ApplicationContext applicationContext;
    private NotificationService notificationService;
    private MessageServiceInterface messageService;
    private LimitedStockServiceInterface limitedStockService;
    private ReportServiceInterface reportService;

    @FXML RadioButton radioPDFButton;
    @FXML RadioButton radioDocButton;

    @FXML private TableView<UserBaseDTO> tableViewUser;
    @FXML private TableColumn<UserBaseDTO,String> nameColumn;
    @FXML private TableColumn<UserBaseDTO,String> usernameColumn;
    @FXML private TableColumn<UserBaseDTO,String> passwordColumn;
    @FXML private TableColumn<UserBaseDTO,String> emailColumn;
    @FXML private TableColumn<UserBaseDTO,String> addressColumn;
    @FXML private TableColumn<UserBaseDTO,String> phoneNumberColumn;
    @FXML private TableColumn<UserBaseDTO,String> userRoleColumn;
    @FXML private TableColumn<UserBaseDTO,Double> balanceAccountColumn;

    @FXML
    public TableView<ProductBaseDTO> tableViewProduct;
    @FXML public TableColumn<ProductBaseDTO ,String> productNameColumn;
    @FXML public TableColumn<ProductBaseDTO ,String> descriptionColumn;
    @FXML public TableColumn<ProductBaseDTO ,Integer> quantityColumn;
    @FXML public TableColumn<ProductBaseDTO ,Double> ratingColumn;
    @FXML public TableColumn<ProductBaseDTO ,Double> priceColumn;

    @FXML
    public TableView<UserProductBaseDTO> tableViewUserProduct;
    @FXML public TableColumn<UserProductBaseDTO ,String> userProductUsernameColumn;
    @FXML public TableColumn<UserProductBaseDTO ,String> userProductNameColumn;
    @FXML public TableColumn<UserProductBaseDTO ,String> userProductDescriptionColumn;
    @FXML public TableColumn<UserProductBaseDTO ,Double> userProductRatingColumn;
    @FXML public TableColumn<UserProductBaseDTO ,Double> userProductPriceColumn;
    @FXML public TableColumn<UserProductBaseDTO ,Date> userProductDateColumn;

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

    @FXML public TextField limitedStockField;
    @FXML public TextField discountField;

    @FXML
    public TableView<MessageBaseDTO> tableViewMessage;
    @FXML public TableColumn<MessageBaseDTO, String> usernameMessageColumn;
    @FXML public TableColumn<MessageBaseDTO, String> messageColumn;

    @Autowired
    public AdminInterfaceController(UserServiceInterface userService, ProductServiceInterface productService, UserProductServiceInterface userProductService,
                                    ApplicationContext applicationContext, NotificationService notificationService, MessageServiceInterface messageService,
                                    LimitedStockServiceInterface limitedStockService, ReportServiceInterface reportService){
        this.userService = userService;
        this.productService = productService;
        this.userProductService = userProductService;
        this.messageService = messageService;
        this.applicationContext = applicationContext;
        this.notificationService = notificationService;
        this.limitedStockService = limitedStockService;
        this.reportService = reportService;
    }

    private ObservableList<UserBaseDTO> mapToObservableUser(List<UserBaseDTO> list){
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

    private ObservableList<MessageBaseDTO> mapToObservableMessage(List<MessageBaseDTO> list){
        ObservableList<MessageBaseDTO> mappedList = FXCollections.observableArrayList();
        mappedList.addAll(list);
        return mappedList;
    }

    void init(UserBaseDTO user) {
        loggedUser = user;
        System.out.println(loggedUser);
        currentUsernameText.setText("Hi, " + loggedUser.getUsername() + "!");

        nameColumn.setCellValueFactory(new PropertyValueFactory<UserBaseDTO, String>("name"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<UserBaseDTO, String>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<UserBaseDTO, String>("password"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<UserBaseDTO, String>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<UserBaseDTO, String>("address"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<UserBaseDTO, String>("phone_number"));
        userRoleColumn.setCellValueFactory(new PropertyValueFactory<UserBaseDTO, String>("user_role"));
        balanceAccountColumn.setCellValueFactory(new PropertyValueFactory<UserBaseDTO, Double>("balance_account"));
        tableViewUser.setItems(mapToObservableUser(userService.getAll()));

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

        productNameColumn.setCellValueFactory(new PropertyValueFactory<ProductBaseDTO , String>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<ProductBaseDTO , String>("description"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<ProductBaseDTO , Integer>("quantity"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<ProductBaseDTO , Double>("rating"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<ProductBaseDTO , Double>("price"));
        tableViewProduct.setItems(mapToObservableProduct(productService.getProducts()));

        userProductUsernameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUser().getUsername()));
        userProductNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getProduct().getName()));
        userProductDescriptionColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getProduct().getDescription()));
        userProductRatingColumn.setCellValueFactory(param -> new SimpleDoubleProperty(((TableColumn.CellDataFeatures<UserProductBaseDTO , Double>) param).getValue().getProduct().getRating()).asObject());
        userProductPriceColumn.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getProduct().getPrice()).asObject());
        userProductDateColumn.setCellValueFactory(new PropertyValueFactory<UserProductBaseDTO , Date>("date"));
        tableViewUserProduct.setItems(mapToObservable(userProductService.getAll()));

        usernameMessageColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUsername()));
        messageColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMessageText()));
        tableViewMessage.setItems(mapToObservableMessage(messageService.getMessages()));

        updateUserButton.setDisable(true);
        cancelUpdateUserButton.setDisable(true);

        limitedStockField.setText(String.valueOf(limitedStockService.getLimitedStockMapper().getStock()));
        discountField.setText(String.valueOf(limitedStockService.getLimitedStockMapper().getDiscount()));
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
        nameField.setText(selectedUser.getName());
        usernameField.setText(selectedUser.getUsername());
        passwordField.setText(selectedUser.getPassword());
        emailField.setText(selectedUser.getEmail());
        addressField.setText(selectedUser.getAddress());
        phoneNumberField.setText(selectedUser.getPhone_number());
        balanceAccountField.setText(String.valueOf(selectedUser.getBalance_account()));
        userRoleComboBox.setValue(selectedUser.getUser_role());
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

        reportService.report(pathToSave, reportType);
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
                    tableViewUser.setItems(mapToObservableUser(userService.getAll()));
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
        emailField.setText("");
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
        emailField.setText("");
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
                    userService.updateUser(selectedUser.getIduser(), nameField.getText(), usernameField.getText(), passwordField.getText(), emailField.getText(),
                            addressField.getText(), phoneNumberField.getText(), userRoleComboBox.getValue(), Double.parseDouble(balanceAccountField.getText()));
                    errorMessageText.setText("");
                    tableViewUser.setItems(mapToObservableUser(userService.getAll()));
                    createUserButton.setDisable(false);
                    updateModeUserButton.setDisable(false);
                    updateUserButton.setDisable(true);
                    cancelUpdateUserButton.setDisable(true);
                    deleteUserButton.setDisable(false);
                    nameField.setText("");
                    usernameField.setText("");
                    passwordField.setText("");
                    emailField.setText("");
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
        tableViewUser.setItems(mapToObservableUser(userService.getAll()));
        tableViewUserProduct.setItems(mapToObservable(userProductService.getAll()));
    }

    @FXML
    private void handleLogoutButton(Event event){
        try {
            notificationService.sendMessageToServer("Logout " + loggedUser.getIduser());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AdminInterfaceController.loggedUser = null;
        sendToAnotherPage(event,"/login.fxml", "Welcome");
    }

    @FXML
    public void selectMessage() {
        selectedMessage = tableViewMessage.getSelectionModel().selectedItemProperty().get();
    }

    @FXML
    public void buttonDeleteMessagePressed() {
        messageService.deleteMessage(selectedMessage.getId());
        tableViewMessage.setItems(mapToObservableMessage(messageService.getMessages()));
    }

    @FXML
    private void handleRefreshButton(Event event){
        tableViewProduct.setItems(mapToObservableProduct(productService.getProducts()));
        tableViewUserProduct.setItems(mapToObservable(userProductService.getAll()));
        tableViewUser.setItems(mapToObservableUser(userService.getAll()));
        tableViewMessage.setItems(mapToObservableMessage(messageService.getMessages()));
    }

    @FXML
    private void handleUpdateLimitedStock(Event event){
        LimitedStockBaseDTO limitedStockBaseDTO = limitedStockService.updateLimitedStock(Integer.parseInt(limitedStockField.getText()), Integer.parseInt(discountField.getText()));

        limitedStockField.setText(String.valueOf(limitedStockBaseDTO.getStock()));
        discountField.setText(String.valueOf(limitedStockBaseDTO.getDiscount()));
    }
}
