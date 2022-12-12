package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.model.Genre;
import it.univaq.disim.psvmsa.unify.model.Song;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcherException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SongRow extends HBox {

    private ImageView picture;
    private Text songName;

    private boolean editable;
    private Text genreName;

    private Text artistName;

    private Button editButton;

    public SongRow (Song song, boolean editable){
        super();
        this.editable=editable;
        init(song);
    }
    public void init(Song song) {
        try {
            HBox root = FXMLLoader.load(getClass().getResource("/ui/components/SongRow.fxml"));
            HBox.setHgrow(root, Priority.ALWAYS);
            getChildren().add(root);

            songName = (Text) root.lookup("#songName");
            genreName = (Text) root.lookup("#genreName");
            artistName = (Text) root.lookup("#artistName");
            picture = (ImageView) root.lookup("#picture");
            editButton = (Button) root.lookup("#editButton");


            editButton.visibleProperty().set(editable);


            List<Genre> genres = song.getGenres();


            ArrayList<String> genresNames = new ArrayList<>();

            for (Genre genre : genres) {
                if (genre != null) {
                    genresNames.add(genre.getName());
                }
            }
            String allGenres = String.join(", ", genresNames);

            this.songName.setText(song.getName());
            this.genreName.setText(allGenres);
            this.artistName.setText(song.getArtist().getName());

            Image image = new Image(song.getPicture().toStream());
            Rectangle rectangle = new Rectangle(0, 0, 50, 50);
            rectangle.setArcWidth(14);   // Corner radius
            rectangle.setArcHeight(14);
            ImagePattern pattern = new ImagePattern(image);
            rectangle.setFill(pattern);
            picture.setClip(rectangle);
            this.picture.setImage(image);

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        editSong(song);
    }

    public void editSong(Song song){
        this.editButton.setOnMouseClicked(event -> {
            try {
                ViewDispatcher.getInstance().navigateTo(Pages.EDITSONG,song);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

}
