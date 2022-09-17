package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.controller.GenreController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import java.util.function.Consumer;


public class SearchBar extends HBox {

    private Button searchButton;
    private TextField searchField;

    private Consumer<String> consumer;

    public SearchBar(String placeHolder) {
        super();
        init();
        searchField.setPromptText(placeHolder);
    }

    public void init() {
        try {
            HBox root = FXMLLoader.load(getClass().getResource("/ui/components/SearchBar.fxml"));
            HBox.setHgrow(root, Priority.ALWAYS);
            getChildren().add(root);

            searchButton = (Button) root.lookup("#search");
            searchField = (TextField) root.lookup("#text");

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        buttonPress();
    }

    public void buttonPress(){
        this.searchButton.setOnAction(event -> {
            if(consumer != null){
                consumer.accept(searchField.getText().toLowerCase());
            }
        });

    }

    public void setOnSearch(Consumer<String> consumer){
        this.consumer = consumer;
    }
}
