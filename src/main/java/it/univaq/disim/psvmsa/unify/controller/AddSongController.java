package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.*;
import it.univaq.disim.psvmsa.unify.model.*;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.beans.value.ObservableBooleanValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class AddSongController implements Initializable, DataInitializable {
    private Image DEFAULT_IMAGE = null;
    private final SongService songService;

    private final AlbumService albumService;
    private final ArtistService artistService;
    private final GenreService genreService;
    private final PictureService pictureService;

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
    private Button uploadSongButton;

    @FXML
    private ChoiceBox<Album> albumBoxChoice;

    @FXML
    private ChoiceBox<Artist> artistBoxChoice;

    @FXML
    private CheckComboBox<Genre> genreBoxChoice;

    @FXML
    private Label saveSongLabel;

    @FXML
    private ImageView songImage;

    private Picture picture;

    private FileInputStream songFileInputStream;


    public AddSongController() {
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.songService = factoryInstance.getSongService();

        this.artistService = factoryInstance.getArtistService();
        this.albumService = factoryInstance.getAlbumService();
        this.genreService = factoryInstance.getGenreService();
        this.pictureService = factoryInstance.getPictureService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        saveButton.disableProperty().bind(
                songNameInput.textProperty().isEmpty()
                        .or(songNameInput.textProperty().isEmpty())
                        .or(albumBoxChoice.valueProperty().isNull())
                        .or(artistBoxChoice.valueProperty().isNull())
                        .or(songLyricsInput.textProperty().isEmpty())
        );

        DEFAULT_IMAGE = songImage.getImage();
        saveSongLabel.setVisible(false);
        genreBoxChoice.setConverter(new StringConverter<>() {
            @Override
            public String toString(Genre genre) {
                if(genre == null) return "";
                return genre.getName();
            }

            @Override
            public Genre fromString(String s) {
                for(Genre g: genreBoxChoice.getItems()){
                    if(g.getName().equals(s)) return g;
                }
                return null;
            }
        });
        artistBoxChoice.setConverter(new StringConverter<>() {
            @Override
            public String toString(Artist artist) {
                //TODO might add the ID at the beginning to help with name clashing
                if(artist == null) return "";
                return artist.getName();
            }

            @Override
            public Artist fromString(String s) {
                for(Artist a: artistBoxChoice.getItems()){
                    if(a.getName().equals(s)) return a;
                }
                return null;
            }
        });

        albumBoxChoice.setConverter(new StringConverter<>() {
            @Override
            public String toString(Album album) {
                if(album == null) return "";
                return album.getName();
            }

            @Override
            public Album fromString(String s) {
                for(Album a: albumBoxChoice.getItems()){
                    if(a.getName().equals(s)) return a;
                }
                return null;
            }
        });
        genreBoxChoice.getItems().addAll(genreService.getGenres());
        artistBoxChoice.getItems().addAll(artistService.getArtists());
        albumBoxChoice.getItems().addAll(albumService.getAlbums());
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
        try(FileInputStream inputStream = new FileInputStream(file)) {
            picture = new Picture(inputStream.readAllBytes());
            songImage.setImage(new Image(picture.toStream()));
        }catch(IOException e){
            e.printStackTrace();
        }

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
            e.printStackTrace();
        }
    }

    public void saveSong() {
        List<Genre> genres = genreBoxChoice.getCheckModel().getCheckedItems();
        Picture picture = pictureService.add(this.picture);
        try{
            Song song = new Song(
                    songNameInput.getText(),
                    albumService.getById(albumBoxChoice.getSelectionModel().getSelectedItem().getId()),
                    artistService.getById(artistBoxChoice.getSelectionModel().getSelectedItem().getId()),
                    songLyricsInput.getText(),
                    picture,
                    genres,
                    songFileInputStream.readAllBytes()

            );
            songService.add(song);
            songNameInput.clear();
            songLyricsInput.clear();
            songImage.setImage(DEFAULT_IMAGE);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
