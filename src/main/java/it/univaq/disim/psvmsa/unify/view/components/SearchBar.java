package it.univaq.disim.psvmsa.unify.view.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class SearchBar extends HBox {
    public SearchBar() {
        super();
        init();
    }

    public void init() {
        try {
            HBox root = FXMLLoader.load(getClass().getResource("/ui/components/SearchBar.fxml"));
            HBox.setHgrow(root, Priority.ALWAYS);
            getChildren().add(root);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
