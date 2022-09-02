package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.model.Genre;
import it.univaq.disim.psvmsa.unify.model.Song;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

import java.util.List;

public class SongRow extends HBox {

    private ImageView picture;
    private Text songName;

    private Text genreName;

    private Text artistName;

    public SongRow (Song song){
        super();
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

            List<Genre> genres = song.getGenres();
            String allGenres = "";
            for (Genre genre : genres) {
                allGenres = allGenres + "," + genre.getName();
            }

            this.songName.setText(song.getName());
            this.genreName.setText(allGenres);
            this.artistName.setText(song.getArtist().getName());

            Image image = new Image(song.getPicture().getImageStream());
            this.picture.setImage(image);

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }



        /*
        this.songName = new Text();
        this.songName.setText(song.getName());

        List<Genre> genres = song.getGenres();
        String allGenres = "";
        for (Genre genre : genres) {
            allGenres = allGenres + "," + genre.getName();
        }

        this.genreName = new Text();
        this.genreName.setText(allGenres);

        this.artistName = new Text();
        this.artistName.setText(song.getArtist().getName());

        Image image = new Image(song.getPicture().getImageStream());
        this.picture.setImage(image);

        this.getChildren().addAll(picture, songName, genreName, artistName);

        this.getChildren().addAll(songName);

         */
    }

}
