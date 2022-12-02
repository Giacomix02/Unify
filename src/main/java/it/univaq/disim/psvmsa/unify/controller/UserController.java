package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.business.UserService;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.components.AddLinkButton;
import it.univaq.disim.psvmsa.unify.view.components.ViewUser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable, DataInitializable {

    @FXML
    private VBox viewList;

    @FXML
    private HBox addBox;

    private UserService userService;

    private ViewUser viewUser;


    public UserController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.userService = factoryInstance.getUserService();
    }


    public void initialize(URL location, ResourceBundle resources){
        List<User> users = userService.getUsers();

        addBox.getChildren().add(new AddLinkButton(Pages.ADDUSER));

        for (User user : users) {
            viewUser = new ViewUser(user);
            viewList.getChildren().add(viewUser);

        }
    }
}