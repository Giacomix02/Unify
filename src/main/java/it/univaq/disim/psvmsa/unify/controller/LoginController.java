package it.univaq.disim.psvmsa.unify.controller;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcherException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Label logLabel;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void login() {
        ViewDispatcher viewDispatcher = ViewDispatcher.getInstance();
        try{
            viewDispatcher.loggedIn();
            viewDispatcher.navigateTo(Pages.ALBUMS);
        }catch(ViewDispatcherException e){
            System.out.println(e.getMessage());
            logLabel.setText("Error logging in");
        }
    }
}