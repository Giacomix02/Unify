package it.univaq.disim.psvmsa.unify.view.components;
import it.univaq.disim.psvmsa.unify.controller.UserWithData;
import it.univaq.disim.psvmsa.unify.model.Genre;
import it.univaq.disim.psvmsa.unify.model.Song;
import it.univaq.disim.psvmsa.unify.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SongRow extends HBox {

    private ImageView picture;

    private Button pictureButton;

    private Label songName;

    private Button playButton;


    private Song song;


    private boolean editable;

    private Text genreName;

    private Text artistName;

    private Button editButton;


    public SongRow (Song song,  boolean editable){
        super();
        this.editable = editable;
        this.song = song;
        init(song);
    }

    public void init(Song song) {
        try {
            HBox root = FXMLLoader.load(getClass().getResource("/ui/components/SongRow.fxml"));
            HBox.setHgrow(root, Priority.ALWAYS);
            getChildren().add(root);

            pictureButton = (Button) root.lookup("#pictureButton");
            songName = (Label) root.lookup("#songName");
            genreName = (Text) root.lookup("#genreName");
            artistName = (Text) root.lookup("#artistName");
            editButton = (Button) root.lookup("#editButton");
            playButton = (Button) root.lookup("#playButton");

            editButton.visibleProperty().set(editable);

            this.songName.setText(song.getName());
            this.genreName.setText(song.getGenre().getName());
            this.artistName.setText(song.getArtist().getName());

            Image image = new Image(song.getPicture().toStream());
            Rectangle rectangle = new Rectangle(0, 0, 50, 50);
            rectangle.setArcWidth(14);   // Corner radius
            rectangle.setArcHeight(14);
            ImagePattern pattern = new ImagePattern(image);
            rectangle.setFill(pattern);
            RoundedImage roundedImage = new RoundedImage(image,20,50);
            roundedImage.fitHeightProperty().set(50);
            roundedImage.fitWidthProperty().set(50);
            pictureButton.setGraphic(roundedImage);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setOnEditClicked(Consumer<Song> consumer){
        this.editButton.setOnMouseClicked(event -> {
            consumer.accept(song);
        });
    }

    public void setOnSongClicked(Consumer<Song> consumer){
        this.pictureButton.setOnMouseClicked(event -> {
            consumer.accept(song);
        });
    }

    public void setOnPlayButtonClicked(Consumer<Song> consumer){
        this.playButton.setOnMouseClicked(event -> {
            consumer.accept(song);
        });
    }

}