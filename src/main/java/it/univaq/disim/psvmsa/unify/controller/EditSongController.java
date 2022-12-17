package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.*;
import it.univaq.disim.psvmsa.unify.model.*;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
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


public class EditSongController implements Initializable, DataInitializable<Song> {

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
    private Picture inputPicture;

    private InputStream songInputStream;

    private Song song;


    public EditSongController() {
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.songService = factoryInstance.getSongService();
        this.artistService = factoryInstance.getArtistService();
        this.albumService = factoryInstance.getAlbumService();
        this.genreService = factoryInstance.getGenreService();
        this.pictureService = factoryInstance.getPictureService();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {    } //not used
    public void initializeData(Song data) {
        this.song = data;
        saveSongLabel.setVisible(false);
        List<Genre> genres = genreService.getGenres();
        List<Artist> artists = artistService.getArtists();
        List<Album> albums = albumService.getAlbums();
        genreBoxChoice.setConverter(new StringConverter<>() {
            @Override
            public String toString(Genre genre) {
                if (genre == null) return "";
                return genre.getName();
            }

            @Override
            public Genre fromString(String s) {
                for(Genre genre : genres){
                    if(genre.getName().equals(s)) return genre;
                }
                return null;
            }
        });
        artistBoxChoice.setConverter(new StringConverter<>() {
            @Override
            public String toString(Artist artist) {
                if (artist == null) return "";
                return artist.getName();

            }
            @Override
            public Artist fromString(String s) {
                for (Artist artist : artists) {
                    if (artist.getName().equals(s)) return artist;
                }
                return null;
            }
        });

        albumBoxChoice.setConverter(new StringConverter<>() {
            @Override
            public String toString(Album album) {
                if (album == null) return "";
                return album.getName();
            }
            @Override
            public Album fromString(String s) {
                for (Album album : albums) {
                    if (album.getName().equals(s)) return album;
                }
                return null;
            }
        });
        genreBoxChoice.getItems().addAll(genres);
        artistBoxChoice.getItems().addAll(artists);
        albumBoxChoice.getItems().addAll(albums);
        if(song != null){
            songNameInput.setText(song.getName());
            songLyricsInput.setText(song.getLyrics());
            inputPicture = song.getPicture();
            Image image = new Image(inputPicture.toStream());
            songImage.setImage(image);
            picture = inputPicture;
            songInputStream = song.toStream();
            for (Genre genre : song.getGenres()) {
                genreBoxChoice.getCheckModel().check(genre); //set previous song genres
            }
            albumBoxChoice.setValue(song.getAlbum());
            artistBoxChoice.setValue(song.getArtist());
        }
    }

    public void uploadImage() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        fileChooser.setTitle("Choose a picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        FileInputStream inputStream = new FileInputStream(file);
        picture = new Picture(inputStream.readAllBytes());
        songImage.setImage(new Image(picture.toStream()));
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
        songInputStream = new FileInputStream(file);
        //TODO song input stream

    }

    public void delete() {
        try{
            songService.deleteById(song.getId());
        }catch(Exception e){
            e.printStackTrace();
        }
        clear();
    }

    public void saveSong() {
        ArrayList<Genre> genres = new ArrayList<>(genreBoxChoice.getCheckModel().getCheckedItems());
        Picture picture = pictureService.add(this.picture);
        try{
            Song song = new Song(
                    songNameInput.getText(),
                    albumService.getById(albumBoxChoice.getSelectionModel().getSelectedItem().getId()),
                    artistService.getById(artistBoxChoice.getSelectionModel().getSelectedItem().getId()),
                    songLyricsInput.getText(),
                    picture,
                    genres,
                    songInputStream.readAllBytes(),
                    this.song.getId()
            );
            if(this.song != null) {
                songService.update(song);
            }else{
                songService.add(song);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void clear(){
        songNameInput.clear();
        songLyricsInput.clear();
        songImage.setImage(null);
        genreBoxChoice.getCheckModel().clearChecks();
        albumBoxChoice.setValue(null);
        artistBoxChoice.setValue(null);
    }
}
