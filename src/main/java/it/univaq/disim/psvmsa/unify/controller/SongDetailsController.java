package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.*;
import it.univaq.disim.psvmsa.unify.model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class SongDetailsController implements Initializable, DataInitializable<UserWithData> {

    private Image DEFAULT_IMAGE = null;
    private final SongService songService;

    private final AlbumService albumService;
    private final ArtistService artistService;
    private final GenreService genreService;
    private final PictureService pictureService;

    @FXML
    private TextField songName;

    @FXML
    private TextArea songLyrics;

    @FXML
    private Label genresLabel;
    @FXML
    private Label artistLabel;
    @FXML
    private Label albumLabel;

    @FXML
    private ImageView songImage;

    private Picture picture;
    private Picture inputPicture;

    private InputStream songInputStream;

    private Song song;
    private User user;


    public SongDetailsController() {
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.songService = factoryInstance.getSongService();
        this.artistService = factoryInstance.getArtistService();
        this.albumService = factoryInstance.getAlbumService();
        this.genreService = factoryInstance.getGenreService();
        this.pictureService = factoryInstance.getPictureService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //this.genresLabel.setText("");
    }

    public void initializeData(UserWithData data) {
        song = (Song) data.getData();
        user = data.getUser();

        this.songName.setText(song.getName());
        this.songLyrics.setText(song.getLyrics());
        this.artistLabel.setText(song.getArtist().getName());
        this.albumLabel.setText(song.getAlbum().getName());
        for(Genre g : song.getGenres()){
            this.genresLabel.setText(this.genresLabel.getText() + g.getName() + "\n");
        }
        this.songImage.setImage(new Image(song.getPicture().toStream()));

    }
}