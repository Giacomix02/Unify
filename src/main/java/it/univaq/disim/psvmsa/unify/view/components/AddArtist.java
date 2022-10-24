package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class AddArtist extends HBox {

    private Button addButton;

    public AddArtist() {
        super();
        init();
    }

    public void init() {
        try {
            HBox root = FXMLLoader.load(getClass().getResource("/ui/components/Add.fxml"));
            HBox.setHgrow(root, Priority.ALWAYS);
            getChildren().add(root);

            addButton = (Button) root.lookup("#button");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        addArtist();
    }

    public void addArtist(){

        this.addButton.setOnAction(event -> {
            try{
                ViewDispatcher.getInstance().navigateTo(Pages.ADDARTIST);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

    }

}
