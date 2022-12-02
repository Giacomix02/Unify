package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.business.UserService;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUserController implements Initializable, DataInitializable {

    @FXML
    private TextField usernameInput;

    @FXML
    private TextField passwordInput;

    @FXML
    private Button saveButton;

    @FXML
    private Label exceptionLabel;

    private final UserService userService;


    public AddUserController() {
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.userService = factoryInstance.getUserService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        exceptionLabel.setText("");

        this.saveButton
                .disableProperty()
                .bind(usernameInput
                        .textProperty()
                        .isEmpty()
                        .and(passwordInput
                                .textProperty()
                                .isEmpty()
                            )
                );
    }

    public void saveUser(){
        User user = new User(usernameInput.getText(), passwordInput.getText());
        try {
            userService.add(user);
            usernameInput.clear();
            passwordInput.clear();
            exit();
        }
        catch (BusinessException e){
            exceptionLabel.setText(e.getMessage());
        }
    }

    public void exit() {
        try {
            ViewDispatcher.getInstance().navigateTo(Pages.MANAGE_USERS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void textFieldClick() {
        exceptionLabel.setText("");
    }
}
