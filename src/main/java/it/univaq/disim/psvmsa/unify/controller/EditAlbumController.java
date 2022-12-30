package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.AlbumService;
import it.univaq.disim.psvmsa.unify.business.ArtistService;
import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Album;
import it.univaq.disim.psvmsa.unify.model.Artist;
import it.univaq.disim.psvmsa.unify.model.Song;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditAlbumController implements Initializable, DataInitializable<UserWithData<Album>> {

    private AlbumService albumService;
    private SongService songService;
    private ArtistService artistService;

    private ViewDispatcher viewDispatcher;

    @FXML
    private Button editButton;

    @FXML
    private CheckComboBox<Song> songsPicker;

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


    public EditAlbumController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.albumService = factoryInstance.getAlbumService();
        this.songService = factoryInstance.getSongService();
        this.artistService = factoryInstance.getArtistService();
    }


    public void initializeData(UserWithData<Album> data){
        this.album = data.getData();
        this.user = data.getUser();
        System.out.println(album);
        if(this.album != null){
            removeButton.visibleProperty().set(true);
            albumInput.setText(album.getName());
            artistPicker.setValue(album.getArtist());
            try {
                songsPicker.getItems().addAll(songService.searchByArtist(album.getArtist()));
                for (Song song : album.getSongs()) {
                    songsPicker.getCheckModel().check(song);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            label.setText("Edit album");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewDispatcher = ViewDispatcher.getInstance();
        exceptionLabel.setText("");
        removeButton.visibleProperty().set(false);
        label.setText("Add album");
        this.editButton
                .disableProperty()
                .bind(albumInput
                        .textProperty()
                        .isEmpty());
        List<Artist> artists = artistService.getArtists();
        artistPicker.converterProperty().set(new StringConverter<Artist>() {
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
        songsPicker.converterProperty().set(new StringConverter<>() {
            @Override
            public String toString(Song object) {
                if (object == null) return "";
                return object.getName();
            }

            @Override
            public Song fromString(String string) {
                for (Song s : songsPicker.getItems()) {
                    if (s.getName().equals(string)) {
                        return s;
                    }
                }
                return null;
            }
        });
        artistPicker.getItems().addAll(artists);
        artistPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try{
                    List<Song> songs = songService.searchByArtist(newValue);
                    songsPicker.getItems().clear();
                    songsPicker.getItems().addAll(songs);
                }catch (Exception e){
                    exceptionLabel.setText(e.getMessage());
                    e.printStackTrace();
                }

            }
        });
    }
    @FXML
    public void updateAlbum(){
        try{
            if(this.album == null){
                this.album = new Album(albumInput.getText(), songsPicker.getItems(),artistPicker.getValue());
               albumService.add(this.album);
               exceptionLabel.setText("Saved album");
               albumInput.clear();
            }else{
                this.album.setName(albumInput.getText());
                this.album.setSongs(songsPicker.getItems());
                this.album.setArtist(artistPicker.getValue());
                albumService.update(album);
                exceptionLabel.setText("Edited album");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void removeAlbum(){
        try{
            albumService.delete(album);
            exceptionLabel.setText("Removed album");
            viewDispatcher.navigateTo(Pages.ALBUMS, user);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
