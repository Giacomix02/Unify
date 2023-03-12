package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.controller.UserWithData;
import it.univaq.disim.psvmsa.unify.model.Picture;
import it.univaq.disim.psvmsa.unify.model.Song;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.lang.module.Configuration;
import java.util.function.Consumer;

public class SingleSong extends VBox {

    private Label label;

    private ImageView image;

    private Song song;

    private Button playSong;

    private MusicPlayer musicPlayer;


    public SingleSong(Song song) {
        super();
        this.song = song;
        this.musicPlayer = MusicPlayer.getInstance();
        init();
    }

    public void init(){
        try{
            VBox root = FXMLLoader.load(getClass().getResource("/ui/components/SingleSong.fxml"));
            VBox.setVgrow(root, Priority.ALWAYS);
            getChildren().add(root);

            label = (Label) root.lookup("#label");
            image = (ImageView) root.lookup("#image");
            playSong = (Button) root.lookup("#playSong");

            this.label.setText(song.getName());
            Picture pic = song.getPicture();
            if(pic == null) return;
            Image image = new Image(pic.toStream());
            Rectangle rectangle = new Rectangle(0, 0, 100, 100);
            rectangle.setArcWidth(14);
            rectangle.setArcHeight(14);
            ImagePattern pattern = new ImagePattern(image);
            rectangle.setFill(pattern);
            playSong.setGraphic(rectangle);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setOnSongClick(Consumer<Song> consumer) {
        this.playSong.setOnMouseClicked(event -> {
            consumer.accept(song);
        });
    }

    /*
    public void viewSong() {
        this.playSong.setOnMouseClicked(event -> {
            ViewDispatcher.getInstance().navigateTo(Pages.SONGSLIST, new UserWithData<>(use, null));
        });
    }

     */

}
