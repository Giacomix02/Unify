package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.business.UserService;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditUserController implements Initializable, DataInitializable {

    private final UserService userService;

    private User user;

    @FXML
    private Button editButton;

    @FXML
    private Button removeButton;

    @FXML
    private Label exceptionLabel;

    @FXML
    private TextField usernameInput;

    @FXML
    private TextField passwordInput;


    public EditUserController() {
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.userService = factoryInstance.getUserService();
    }


    public void initializeData(Object data) {
        this.user = (User) data;
        usernameInput.setText(user.getUsername());
        passwordInput.setText(user.getPassword());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        exceptionLabel.setText("");

        this.editButton
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

    public void updateUser(){
        try{
            user.setUsername(usernameInput.getText());
            user.setPassword(passwordInput.getText());
            userService.update(user);
            exit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeUser(){
        try{
            userService.delete(user);
            exit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void exit() {
        try {
            ViewDispatcher.getInstance().navigateTo(Pages.MANAGE_USERS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
