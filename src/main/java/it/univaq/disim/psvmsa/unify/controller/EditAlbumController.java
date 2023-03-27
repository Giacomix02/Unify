package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.*;
import it.univaq.disim.psvmsa.unify.model.*;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.collections.ObservableList;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditAlbumController implements Initializable, DataInitializable<UserWithData<Album>> {

    private Image DEFAULT_IMAGE = null;

    private AlbumService albumService;

    private SongService songService;

    private ArtistService artistService;

    private GenreService genreService;
    private ViewDispatcher viewDispatcher;

    @FXML
    private Button editButton;
    @FXML
    private ChoiceBox<Artist> artistPicker;

    @FXML
    private Button removeButton;

    @FXML
    private TextField albumInput;

    @FXML
    private Label exceptionLabel;

    @FXML
    private Label label;

    private Album album;

    private User user;

    @FXML
    private Button saveButton;
    @FXML
    private ChoiceBox<Song> currentSongPicker;
    @FXML
    private CheckComboBox<Genre> songGenrePicker;


    @FXML
    private Label songFileLabel;

    @FXML
    private Button uploadSongButton;
    @FXML
    private ImageView songImage;

    private Song currentSong;

    private FileInputStream currentSongStream;

    private Picture currentSongPicture;
    @FXML
    private TextField songNameInput;
    @FXML
    private TextArea songLyricsInput;

    public EditAlbumController() {
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.albumService = factoryInstance.getAlbumService();
        this.songService = factoryInstance.getSongService();
        this.artistService = factoryInstance.getArtistService();
        this.genreService = factoryInstance.getGenreService();
    }


    public void initializeData(UserWithData<Album> data) {
        this.album = data.getData();
        this.user = data.getUser();
        if (this.album != null) {
            removeButton.visibleProperty().set(true);
            albumInput.setText(album.getName());
            artistPicker.setValue(album.getArtist());
            currentSongPicker.getItems().addAll(album.getSongs());
            label.setText("Edit album");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewDispatcher = ViewDispatcher.getInstance();
        exceptionLabel.setText("");
        removeButton.visibleProperty().set(false);
        label.setText("Add album");
        DEFAULT_IMAGE = songImage.getImage();
        this.editButton
                .disableProperty()
                .bind(albumInput
                        .textProperty()
                        .isEmpty());
        List<Artist> artists = artistService.getArtists();
        artistPicker.converterProperty().set(new StringConverter<>() {
            @Override
            public String toString(Artist object) {
                if (object == null) return "";
                return object.getName();
            }

            @Override
            public Artist fromString(String string) {
                for (Artist a : artists) {
                    if (a.getName().equals(string)) {
                        return a;
                    }
                }
                return null;
            }
        });
        currentSongPicker.converterProperty().set(new StringConverter<>() {
            @Override
            public String toString(Song object) {
                if (object == null) return "";
                return object.getName();
            }

            @Override
            public Song fromString(String string) {
                for (Song s : currentSongPicker.getItems()) {
                    if (s.getName().equals(string)) {
                        return s;
                    }
                }
                return null;
            }
        });
        List<Genre> genres = genreService.getGenres();
        songGenrePicker.setConverter(new StringConverter<>() {   // Convert Genre to String
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
        currentSongPicker.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadSong(newValue);
        });
        songGenrePicker.getItems().addAll(genres);
        artistPicker.getItems().addAll(artists);
    }

    @FXML
    public void updateAlbum() {
        try {
            List<Song> songs = currentSongPicker.getItems();
            Artist artist = artistPicker.getValue();
            if (this.album == null) {
                for(Song song: songs){
                    song.setArtist(artist);
                }
                this.album = new Album(albumInput.getText(),songs, artist);
                albumService.add(this.album);
                exceptionLabel.setText("Saved album");
                albumInput.clear();
            } else {
                this.album.setName(albumInput.getText());
                this.album.setSongs(songs);
                this.album.setArtist(artist);
                albumService.update(album);
                exceptionLabel.setText("Edited album");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCurrentImage(ImageView imageView, Picture picture) {
        currentSongPicture = picture;
        if(currentSongPicture == null) {
            imageView.setImage(DEFAULT_IMAGE);
        }else{
            imageView.setImage(new Image(picture.toStream()));
        }
    }
    @FXML
    public void removeAlbum() {
        try {
            albumService.delete(album);
            exceptionLabel.setText("Removed album");
            viewDispatcher.navigateTo(Pages.ALBUMS, user);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSong(Song song) {
        clearSong();
        if(song == null) return;
        songNameInput.setText(song.getName());
        songLyricsInput.setText(song.getLyrics());
        songGenrePicker.getCheckModel().clearChecks();
        for (Genre g : song.getGenres()) {
            songGenrePicker.getCheckModel().check(g);
        }
        setCurrentImage(songImage, song.getPicture());
        currentSong = song;
        saveButton.setText("Update song");
    }
    @FXML
    public void removeCurrentSongFromAlbum() {
        currentSongPicker.getItems().remove(currentSongPicker.getSelectionModel().getSelectedItem());
        clearSong();
        currentSongPicker.getSelectionModel().clearSelection();
        currentSong = null;
    }
    @FXML
    public void addCurrentSongToAlbum() {
        if(currentSong != null){
            currentSong.setName(songNameInput.getText());
            currentSong.setLyrics(songLyricsInput.getText());
            currentSong.setGenres(new ArrayList<>(songGenrePicker.getCheckModel().getCheckedItems()));
            currentSong.setPicture(currentSongPicture);
            currentSong.setContent(currentSongStream);
            clearSong();
            currentSongPicker.getSelectionModel().clearSelection();
            saveButton.setText("Add song to album");
        }else{
            ArrayList<Genre> genres = new ArrayList<>(songGenrePicker.getCheckModel().getCheckedItems());
                Song song = new Song(
                        songNameInput.getText(),
                        artistService.getById(artistPicker.getSelectionModel().getSelectedItem().getId()),
                        songLyricsInput.getText(),
                        this.currentSongPicture,
                        genres,
                        currentSongStream
                );
                currentSongPicker.getItems().add(song);
                clearSong();
        }
    }

    @FXML
    public void addNewSongToAlbum() {
        clearSong();
        currentSongPicker.getSelectionModel().clearSelection();
        currentSong = null;
    }

    @FXML
    public void onSongFilePick() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Choose a song");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Song Files", "*.mp3")
        );
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        currentSongStream = new FileInputStream(file);
        songFileLabel.setText(file.getName());
    }

    @FXML
    public void onSongImagePick() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Choose a picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        setCurrentImage(songImage, new Picture(new FileInputStream(file)));
    }

    private void clearSong() {
        songNameInput.clear();
        songLyricsInput.clear();
        setCurrentImage(songImage, null);
        songGenrePicker.getCheckModel().clearChecks();
        songFileLabel.setText("");
        saveButton.setText("Add song to album");
    }

}
