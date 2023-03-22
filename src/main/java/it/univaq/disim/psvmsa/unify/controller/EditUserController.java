package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.business.UserService;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditUserController implements Initializable, DataInitializable<User> {

    private final UserService userService;

    private User user;

    @FXML
    private Button actionButton;

    @FXML
    private Button removeButton;

    @FXML
    private Label exceptionLabel;

    @FXML
    private TextField usernameInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Label label;


    public EditUserController() {
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.userService = factoryInstance.getUserService();
    }


    public void initializeData(User data) {
        this.user = data;
        usernameInput.setText(user.getUsername());
        passwordInput.setText(user.getPassword());
        removeButton.visibleProperty().set(true);
        label.setText("Edit user");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        removeButton.visibleProperty().set(false);
        exceptionLabel.setText("");
        label.setText("Create user");
        this.actionButton
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
            if(user != null){
                user.setUsername(usernameInput.getText());
                user.setPassword(passwordInput.getText());
                userService.update(user);
                exceptionLabel.setText("User modified");
            }else{
                User user = new User(usernameInput.getText(), passwordInput.getText());
                userService.add(user);
                exceptionLabel.setText("User added");
                usernameInput.clear();
                passwordInput.clear();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeUser(){
        try{
            userService.delete(user);
            exceptionLabel.setText("User removed");
            user = null;
            usernameInput.clear();
            passwordInput.clear();
            removeButton.visibleProperty().set(false);
            ViewDispatcher.getInstance().navigateTo(Pages.MANAGE_USERS, this.user);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
