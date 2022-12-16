package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.model.Playlist;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ViewPlaylist extends HBox {

    private User user;
    private Label label;
    private Button editButton;

    private Playlist playlist;

    public ViewPlaylist(Playlist playlist) {
        super();
        this.playlist = playlist;
        init();
    }

    public void init(){
        try {
            HBox root = FXMLLoader.load(getClass().getResource("/ui/components/viewPlaylist.fxml"));
            HBox.setHgrow(root, Priority.ALWAYS);
            getChildren().add(root);

            label = (Label) root.lookup("#label");
            editButton = (Button) root.lookup("#editButton");


            this.editButton.setOnMouseClicked(event -> {
                try {
                    ViewDispatcher.getInstance().navigateTo(Pages.EDITPLAYLIST,playlist);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            label.setText(playlist.getName());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
