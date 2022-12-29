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


public class EditSongController implements Initializable, DataInitializable<UserWithData> {

    private Image DEFAULT_IMAGE = null;
    private final SongService songService;

    private final AlbumService albumService;
    private final ArtistService artistService;
    private final GenreService genreService;
    private final PictureService pictureService;

    private ViewDispatcher viewDispatcher;

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

    private User user;

    public EditSongController() {
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.songService = factoryInstance.getSongService();
        this.artistService = factoryInstance.getArtistService();
        this.albumService = factoryInstance.getAlbumService();
        this.genreService = factoryInstance.getGenreService();
        this.pictureService = factoryInstance.getPictureService();
        viewDispatcher = ViewDispatcher.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    public void initializeData(UserWithData data) {
        this.user = data.getUser();

        if(data.getData()!=null) {
            this.song = (Song) data.getData();
            exit.visibleProperty().set(true);
        }else {
            exit.visibleProperty().set(false);
        }
        saveSongLabel.setVisible(false);

        List<Genre> genres = genreService.getGenres();
        List<Artist> artists = artistService.getArtists();


        genreBoxChoice.setConverter(new StringConverter<>() {   // Convert Genre to String
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
        artistBoxChoice.setConverter(new StringConverter<>() {  // Convert Artist to String
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

        genreBoxChoice.getItems().removeAll(genreBoxChoice.getItems());
        artistBoxChoice.getItems().removeAll(artistBoxChoice.getItems());

        genreBoxChoice.getItems().addAll(genres);
        artistBoxChoice.getItems().addAll(artists);


        if(song != null){
            songNameInput.setText(song.getName());
            songLyricsInput.setText(song.getLyrics());
            inputPicture = song.getPicture();
            try{
                Image image = new Image(inputPicture.toStream());
                songImage.setImage(image);
                picture = inputPicture;
                songInputStream = song.toStream();
            }catch (Exception e){
                e.printStackTrace();
            }
            for (Genre genre : song.getGenres()) {
                genreBoxChoice.getCheckModel().check(genre); //set previous song genres
            }
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
        picture = new Picture(new FileInputStream(file));
        songImage.setImage(new Image(picture.toStream()));
    }

    public void uploadSong() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Choose a song");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Song Files", "*.mp3", "*.wav")
        );
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        songInputStream = new FileInputStream(file);
        saveSongLabel.setVisible(true);
    }

    public void delete() {
        try{
            songService.deleteById(song.getId());
            viewDispatcher.navigateTo(Pages.SONGS, user);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void saveSong() {
        ArrayList<Genre> genres = new ArrayList<>(genreBoxChoice.getCheckModel().getCheckedItems());
        Picture picture = pictureService.add(this.picture); //TODO maybe move in service
        try{
            Song song = new Song(
                    songNameInput.getText(),
                    artistService.getById(artistBoxChoice.getSelectionModel().getSelectedItem().getId()),
                    songLyricsInput.getText(),
                    picture,
                    genres,
                    songInputStream
            );
            if(this.song != null) {
                song.setId(this.song.getId());
                songService.update(song);
                viewDispatcher.navigateTo(Pages.SONGS, user);
            }else{
                songService.add(song);
                clear();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        saveSongLabel.setVisible(false);
    }
    private void clear(){
        songNameInput.clear();
        songLyricsInput.clear();
        songImage.setImage(null);
        genreBoxChoice.getCheckModel().clearChecks();
        artistBoxChoice.setValue(null);
    }
}
