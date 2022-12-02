package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.business.UserService;
import it.univaq.disim.psvmsa.unify.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManageUserController implements Initializable, DataInitializable {

    @FXML
    private VBox viewList;

    /*
    TODO: waiting for a generic AddController? I'm not going to create an AddUsersController.
    @FXML
    private HBox addBox;
     */

    private UserService userService;

    /*
    TODO: Define ViewUser to edit users.
    private ViewUser viewUser;
    */


    public ManageUserController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.userService = factoryInstance.getUserService();
    }


    public void initialize(URL location, ResourceBundle resources){
        List<User> users = userService.getUsers();

        /*
        TODO: Define ViewUser to edit users.
        for (User user : users) {
            viewUser = new ViewUsers(user);
            viewList.getChildren().add(viewUser)
         */

    }
}