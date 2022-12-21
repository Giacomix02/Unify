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
    private PlaylistService playlistService;
    private SongService songService;

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
    private Button savePlaylistButton;

    @FXML
    private CheckComboBox<Playlist> playlistsChoice;
    @FXML
    private ImageView songImage;

    private Picture picture;
    private Picture inputPicture;

    private InputStream songInputStream;

    private Song song;
    private User user;
    private List<Playlist> userPlaylists;


    public SongDetailsController() {
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.playlistService = factoryInstance.getPlaylistService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void initializeData(UserWithData data) {
        song = (Song) data.getData();
        user = data.getUser();

        try {
            userPlaylists = playlistService.getPlaylistsByUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.songName.setText(song.getName());
        this.songLyrics.setText(song.getLyrics());
        this.artistLabel.setText(song.getArtist().getName());
        this.albumLabel.setText(song.getAlbum().getName());
        for(Genre g : song.getGenres()){
            this.genresLabel.setText(this.genresLabel.getText() + g.getName() + "\n");
        }
        this.songImage.setImage(new Image(song.getPicture().toStream()));

        playlistsChoice.setConverter(new StringConverter<>() {   // Convert Genre to String
            @Override
            public String toString(Playlist playlist) {
                if (playlist == null) return "";
                return playlist.getName();
            }

            @Override
            public Playlist fromString(String s) {
                for(Playlist playlist : userPlaylists){
                    if(playlist.getName().equals(s)) return playlist;
                }
                return null;
            }
        });

        this.playlistsChoice.getItems().addAll(userPlaylists);
        for(Playlist p : userPlaylists ){
            if(p.getSongs().contains(song)){
                this.playlistsChoice.getCheckModel().check(p);
            }
        }
    }

    public void savePlaylists(){
        ArrayList<Playlist> playlists = new ArrayList<>(playlistsChoice.getCheckModel().getCheckedItems());
        List<Playlist> p = userPlaylists;
        for (Playlist playlist : playlists) {
            try {
                if(!playlist.getSongs().contains(song)){
                    playlist.addSong(song);
                    playlistService.update(playlist);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        p.removeAll(playlists);
        for (Playlist playlist : p){
            playlist.removeSong(song);
            try {
                playlistService.update(playlist);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}