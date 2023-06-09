package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.*;
import it.univaq.disim.psvmsa.unify.model.*;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditAlbumController implements Initializable, DataInitializable<UserWithData<Album>> {

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
    private ChoiceBox<Genre> genrePicker;

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
    private ChoiceBox<Genre> songGenrePicker;

    @FXML
    private Label songFileLabel;

    @FXML
    private Button uploadSongButton;

    @FXML
    private ImageView songImage;

    private Song currentSong;

    private SimpleObjectProperty<InputStream> currentSongStream = new SimpleObjectProperty<>();


    private SimpleObjectProperty<Picture> currentSongPicture = new SimpleObjectProperty<>();

    @FXML
    private TextField songNameInput;

    @FXML
    private TextArea songLyricsInput;

    private Image image;

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
            genrePicker.setValue(album.getGenre());
            currentSongPicker.getItems().addAll(album.getSongs());
            label.setText("Edit album");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewDispatcher = ViewDispatcher.getInstance();
        exceptionLabel.setText("");
        songFileLabel.setText("");
        removeButton.visibleProperty().set(false);
        label.setText("Add album");
        image = songImage.getImage();
        this.editButton
                .disableProperty()
                .bind(albumInput
                        .textProperty()
                        .isEmpty().or(artistPicker.valueProperty().isNull())
                        .or(genrePicker.valueProperty().isNull()));

        this.saveButton
                .disableProperty()
                .bind(
                        songNameInput.textProperty().isEmpty()
                    .or(songLyricsInput.textProperty().isEmpty())
                    .or(songGenrePicker.valueProperty().isNull())
                    .or(songFileLabel.textProperty().isEmpty())
                    .or(currentSongPicture.isNull())
                    .or(currentSongStream.isNull())
                );

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
        songGenrePicker.setConverter(new StringConverter<>() {
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

        genrePicker.converterProperty().set(new StringConverter<>() {
            @Override
            public String toString(Genre object) {
                if (object == null) return "";
                return object.getName();
            }

            @Override
            public Genre fromString(String string) {
                for (Genre g : genres) {
                    if (g.getName().equals(string)) {
                        return g;
                    }
                }
                return null;
            }
        });
        currentSongPicker.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadSong(newValue);
        });
        songGenrePicker.getItems().addAll(genres);
        genrePicker.getItems().addAll(genres);
        artistPicker.getItems().addAll(artists);
    }

    @FXML
    public void saveAlbum() {
        try {
            List<Song> songs = currentSongPicker.getItems();
            Artist artist = artistPicker.getValue();
            if (this.album == null) {
                this.album = new Album(albumInput.getText(),songs, artist, genrePicker.getSelectionModel().getSelectedItem());
                albumService.add(this.album);
                exceptionLabel.setText("Saved album");
                albumInput.clear();
                genrePicker.getSelectionModel().clearSelection();
                artistPicker.getSelectionModel().clearSelection();
            } else {
                this.album.setName(albumInput.getText());
                this.album.setSongs(songs);
                this.album.setArtist(artist);
                albumService.update(this.album);
                exceptionLabel.setText("Edited album");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        clearSong();
    }

    private void setCurrentImage(ImageView imageView, Picture picture) {
        currentSongPicture.set(picture);
        if(currentSongPicture.getValue() == null) {
            imageView.setImage(image);
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
        songGenrePicker.setValue(song.getGenre());
        setCurrentImage(songImage, song.getPicture());
        currentSongStream.set(song.toStream());
        songFileLabel.setText("Internal audio file loaded");
        currentSong = song;
        saveButton.setText("Update song");
    }
    @FXML
    public void removeCurrentSongFromAlbum() {
        currentSongPicker.getItems().remove(currentSongPicker.getValue());
        clearSong();
        currentSongPicker.getSelectionModel().clearSelection();
        currentSong = null;
    }
    @FXML
    public void addCurrentSongToAlbum() {
        if(currentSong != null){
            currentSong.setName(songNameInput.getText());
            currentSong.setLyrics(songLyricsInput.getText());
            currentSong.setGenre(songGenrePicker.getValue());
            currentSong.setPicture(currentSongPicture.getValue());
            currentSong.setContent(currentSongStream.getValue());
            clearSong();
            currentSongPicker.getSelectionModel().clearSelection();
            saveButton.setText("Add song to album");
        }else{
                Song song = new Song(
                        songNameInput.getText(),
                        artistService.getById(artistPicker.getValue().getId()),
                        songLyricsInput.getText(),
                        currentSongPicture.getValue(),
                        songGenrePicker.getValue(),
                        currentSongStream.getValue()
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
        currentSongStream.setValue(new FileInputStream(file));
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
        currentSongStream.setValue(null);
        songGenrePicker.getSelectionModel().clearSelection();
        songFileLabel.setText("");
        saveButton.setText("Add song to album");
    }

}
