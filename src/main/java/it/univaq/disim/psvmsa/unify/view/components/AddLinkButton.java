package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.controller.UserWithData;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class AddLinkButton extends HBox {
    public AddLinkButton(Pages page) {
        super();
        try {
            HBox root = FXMLLoader.load(getClass().getResource("/ui/components/Add.fxml"));
            HBox.setHgrow(root, Priority.ALWAYS);
            getChildren().add(root);
            Button addButton = (Button) root.lookup("#button");
            addButton.setOnAction(event -> {
                try{
                    ViewDispatcher.getInstance().navigateTo(page);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public AddLinkButton(Pages page, UserWithData userWithData) {
        super();
        try {
            HBox root = FXMLLoader.load(getClass().getResource("/ui/components/Add.fxml"));
            HBox.setHgrow(root, Priority.ALWAYS);
            getChildren().add(root);
            Button addButton = (Button) root.lookup("#button");
            addButton.setOnAction(event -> {
                try{
                    ViewDispatcher.getInstance().navigateTo(page,userWithData);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}
