package it.univaq.disim.psvmsa.unify.controller;
import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.business.UserService;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcherException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable, DataInitializable{
    @FXML
    private Label logLabel;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;
    UserService userService;
    public LoginController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.userService = factoryInstance.getUserService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loginButton
                .disableProperty()
                .bind(username
                        .textProperty()
                        .isEmpty()
                        .or(password.textProperty().isEmpty())
                );
        this.registerButton
                .disableProperty()
                .bind(this.loginButton.disableProperty());
    }

    @FXML
    private void login() {
        try{
            User user = this.userService.validate(username.getText(),password.getText());
            ViewDispatcher viewDispatcher = ViewDispatcher.getInstance();
            viewDispatcher.loggedIn(user);
            viewDispatcher.navigateTo(Pages.HOME);
        }catch(ViewDispatcherException e){
            System.out.println(e.getMessage());
            logLabel.setText("Error logging in");
        }catch (BusinessException businessException){
            logLabel.setText(businessException.getMessage());
        }
    }

    @FXML
    private void register(){
        try{
            User user = new User(username.getText(),password.getText());
            User registeredUser = this.userService.add(user);
            ViewDispatcher viewDispatcher = ViewDispatcher.getInstance();
            viewDispatcher.loggedIn(registeredUser);
        }catch(BusinessException e){
            logLabel.setText(e.getMessage());
        }catch(ViewDispatcherException e){
            logLabel.setText("Error logging in");
        }
    }
}