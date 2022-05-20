package it.univaq.disim.psvmsa.unify.controller;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewNavigation;
import it.univaq.disim.psvmsa.unify.view.ViewNavigationException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void login() {
        ViewNavigation viewNavigation = ViewNavigation.getInstance();
        try{
            viewNavigation.loggedIn();
            viewNavigation.navigateTo(Pages.ALBUMS);
        }catch(ViewNavigationException e){
            System.out.println("Error logging in");
        }
    }
}