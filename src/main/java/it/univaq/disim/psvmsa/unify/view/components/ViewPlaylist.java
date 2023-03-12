package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.controller.UserWithData;
import it.univaq.disim.psvmsa.unify.model.Playlist;
import it.univaq.disim.psvmsa.unify.model.Song;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.function.Consumer;

public class ViewPlaylist extends HBox {


    private Label label;

    private Button editButton;

    private HBox hBox;

    private Button playButton;

    private MusicPlayer musicPlayer;

    private Playlist playlist;


    public ViewPlaylist(Playlist playlist) {
        super();
        this.playlist = playlist;
        this.musicPlayer = MusicPlayer.getInstance();
        init();
    }

    public void init(){

        try {
            HBox root = FXMLLoader.load(getClass().getResource("/ui/components/viewPlaylist.fxml"));
            HBox.setHgrow(root, Priority.ALWAYS);
            getChildren().add(root);

            label = (Label) root.lookup("#label");
            editButton = (Button) root.lookup("#editButton");
            playButton = (Button) root.lookup("#playButton");

            label.setText(playlist.getName());

        }catch (Exception e){
            e.printStackTrace();
        }

        playPlaylist();

    }
    public void setOnPlaylistClicked(Consumer<Playlist> consumer){
        this.label.setOnMouseClicked(mouseEvent -> {
            consumer.accept(playlist);
        });
    }
    public void setOnEditClicked(Consumer<Playlist> consumer){
        this.editButton.setOnMouseClicked(mouseEvent -> {
            consumer.accept(playlist);
        });
    }
    public void playPlaylist() {
        this.playButton.setOnMouseClicked(event -> {
            musicPlayer.setQueue(playlist.getSongs());
            musicPlayer.startQueuePlayback();
        });
    }

}
