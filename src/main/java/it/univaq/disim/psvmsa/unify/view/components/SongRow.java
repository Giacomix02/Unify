package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.model.Genre;
import it.univaq.disim.psvmsa.unify.model.Song;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.List;

public class SongRow extends HBox {

    private ImageView picture;
    private Text songName;

    private Text genreName;

    private Text artistName;
    public SongRow (Song song){



        this.songName = new Text();
        this.songName.setText(song.getName());
/*
        List<Genre> genres = song.getGenres();
        String allGenres = "";
        for (Genre genre:genres){
            allGenres = allGenres + ","+ genre.getName();
        }

        this.genreName = new Text();
        this.genreName.setText(allGenres);

        this.artistName = new Text();
        this.artistName.setText(song.getArtist().getName());

        Image image = new Image(song.getPicture().getImageStream());
        this.picture.setImage(image);

        this.getChildren().addAll(picture,songName,genreName,artistName);

 */
        this.getChildren().addAll(songName);
    }
}
