package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.*;
import it.univaq.disim.psvmsa.unify.model.*;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class AddSongController implements Initializable, DataInitializable {

    private final SongService songService;

    private final AlbumService albumService;
    private final ArtistService artistService;
    private final GenreService genreService;

    @FXML
    private TextField songNameInput;

    @FXML
    private Button uploadImageButton;
    @FXML
    private TextArea songLyricsInput;

    @FXML
    private Button exit;

    @FXML
    private Button saveButton;

    @FXML
    private Label saveImageLabel;

    @FXML
    private Button uploadSongButton;

    @FXML
    private ChoiceBox<Album> albumBoxChoice;

    @FXML
    private ChoiceBox<Artist> artistBoxChoice;
    @FXML
    private CheckComboBox<Genre> genreBoxChoice;

    @FXML
    private Label saveSongLabel;




    private Picture picture;

    private FileInputStream songFileInputStream;


    public AddSongController() {
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.songService = factoryInstance.getSongService();

        this.artistService = factoryInstance.getArtistService();
        this.albumService = factoryInstance.getAlbumService();
        this.genreService = factoryInstance.getGenreService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        saveSongLabel.setVisible(false);
        saveImageLabel.setVisible(false);

        List<Genre> genres = genreService.getGenres();
        List <Artist> artists = artistService.getArtists();
        List <Album> albums = albumService.getAlbums();


        genreBoxChoice.setConverter(new StringConverter<>() {
            @Override
            public String toString(Genre genre) {
                if (genre != null) {
                    return genre.getName();
                }
                return "";
            }

            @Override
            public Genre fromString(String s) {
                List<Genre> genres = genreService.searchByName(s);
                return genres.get(0);
            }
        });
        artistBoxChoice.setConverter(new StringConverter<>() {
            @Override
            public String toString(Artist artist) {
                if (artist != null) {
                    return artist.getName();
                }
                return "";
            }

            @Override
            public Artist fromString(String s) {
                List<Artist> a = artistService.searchArtistsByName(s);
                return a.get(0);
            }
        });

        albumBoxChoice.setConverter(new StringConverter<>() {
            @Override
            public String toString(Album album) {
                if (album != null) {
                    return album.getName();
                }
                return "";
            }
            @Override
            public Album fromString(String s) {
                List<Album> a = albumService.searchAlbumsByName(s);
                return a.get(0);
            }
        });


        for (Genre genre : genres) {
            genreBoxChoice.getItems().add(genre);
        }

        for (Artist artist : artists) {
            artistBoxChoice.getItems().add(artist);
        }

        for (Album album : albums) {
            albumBoxChoice.getItems().add(album);
        }



    }

    public void uploadImage() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        fileChooser.setTitle("Choose a picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        FileInputStream inputStream = new FileInputStream(file);
        picture = new Picture(inputStream);

        saveImageLabel.setVisible(true);    // advise the user that the image has been uploaded
        //TODO exeption?
    }

    public void uploadSong() throws FileNotFoundException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        fileChooser.setTitle("Choose a song");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.mp3", "*.wav")
        );
        Stage stage = new Stage();

        File file = fileChooser.showOpenDialog(stage);
        songFileInputStream = new FileInputStream(file);
        //TODO song input stream

    }

    public void exit() {

        try {
            ViewDispatcher.getInstance().navigateTo(Pages.SONGS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveSong() {
        Song song = new Song(songNameInput.getText());
        song.setLyrics(songLyricsInput.getText());
        song.setPicture(picture);

        ArrayList<Genre> genres = new ArrayList<>(genreBoxChoice.getCheckModel().getCheckedItems());
        song.setGenres(genres);


        song.setArtist(artistService.getById(artistBoxChoice.getSelectionModel().getSelectedItem().getId()));
        song.setAlbum(albumService.getById(albumBoxChoice.getSelectionModel().getSelectedItem().getId()));

        songService.add(song);
        songNameInput.clear();
        saveImageLabel.setVisible(false);
    }
}
