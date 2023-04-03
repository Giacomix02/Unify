package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.business.UserService;
import it.univaq.disim.psvmsa.unify.model.Admin;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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

    @FXML
    private CheckBox adminCheckBox;


    public EditUserController() {
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.userService = factoryInstance.getUserService();
    }


    public void initializeData(User data) {
        this.user = data;
        usernameInput.setText(user.getUsername());
        passwordInput.setText(user.getPassword());
        removeButton.visibleProperty().set(true);
        adminCheckBox.setSelected(user instanceof Admin);
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

    public void updateUser() {
        try {
            if (user != null) {
                user.setUsername(usernameInput.getText());
                user.setPassword(passwordInput.getText());
                if (adminCheckBox.isSelected()) {
                    user = new Admin(user.getUsername(), user.getPassword(), user.getId());
                } else {
                    user = new User(user.getUsername(), user.getPassword(), user.getId());
                }
                userService.update(user);
                exceptionLabel.setText("User modified");
            } else {
                if (adminCheckBox.isSelected()) {
                    Admin admin = new Admin(usernameInput.getText(), passwordInput.getText());
                    userService.add(admin);
                } else {
                    user = new User(usernameInput.getText(), passwordInput.getText());
                    userService.add(user);
                }
                exceptionLabel.setText("User added");
                usernameInput.clear();
                passwordInput.clear();
                adminCheckBox.setSelected(false);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }


    public void removeUser() {
        try {
            userService.delete(user);
            exceptionLabel.setText("User removed");
            user = null;
            usernameInput.clear();
            passwordInput.clear();
            removeButton.visibleProperty().set(false);
            ViewDispatcher.getInstance().navigateTo(Pages.MANAGE_USERS, this.user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
