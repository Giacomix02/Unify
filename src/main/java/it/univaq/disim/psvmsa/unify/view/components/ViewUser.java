package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ViewUser extends HBox {

    @FXML
    private Label label;

    @FXML
    private Button editButton;

    private User user;


    public ViewUser(User user) {
        super();
        this.user = user;
        init();
    }


    public void init() {
        try {
            HBox root = FXMLLoader.load(getClass().getResource("/ui/components/viewUser.fxml"));
            HBox.setHgrow(root, Priority.ALWAYS);
            getChildren().add(root);

            label = (Label) root.lookup("#username");
            editButton = (Button) root.lookup("#editButton");

            label.setText(user.getUsername());

            } catch (Exception e) {
                e.printStackTrace();
            }
        editUser(user);
        }

    @FXML
    public void editUser(User user){
        this.editButton.setOnAction(event -> {
            try{
                ViewDispatcher.getInstance().navigateTo(Pages.EDITUSER, user);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
