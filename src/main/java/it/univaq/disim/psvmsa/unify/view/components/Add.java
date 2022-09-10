package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

public class Add extends HBox {
    private Button addButton;
    public Add() {
        super();
        init();
    }

    public void init(){
        try {
            HBox root = FXMLLoader.load(getClass().getResource("/ui/components/Add.fxml"));
            HBox.setHgrow(root, Priority.ALWAYS);
            getChildren().add(root);

            addButton = (Button) root.lookup("#button");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        addGenre();
    }


    @FXML
    public void addGenre(){

        this.addButton.setOnAction(event -> {
            try {
                ViewDispatcher.getInstance().navigateTo(Pages.ADDGENRE);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        });
    }
}