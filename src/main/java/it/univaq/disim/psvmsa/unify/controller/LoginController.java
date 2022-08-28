package it.univaq.disim.psvmsa.unify.controller;
import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.business.UserService;
import it.univaq.disim.psvmsa.unify.business.impl.ram.RAMUserServiceImpl;
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
    }

    @FXML
    private void login() {
        ViewDispatcher viewDispatcher = ViewDispatcher.getInstance();
        try{
            User user = this.userService.validate(username.getText(),password.getText());
            viewDispatcher.loggedIn(user);
            viewDispatcher.navigateTo(Pages.HOME);
        }catch(ViewDispatcherException e){
            System.out.println(e.getMessage());
            logLabel.setText("Error logging in");
        }catch (BusinessException businessException){
            logLabel.setText(businessException.getMessage());
        }
    }
}