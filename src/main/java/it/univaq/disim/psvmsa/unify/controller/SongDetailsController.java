package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.*;
import it.univaq.disim.psvmsa.unify.model.*;
import it.univaq.disim.psvmsa.unify.view.components.MusicPlayer;
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
import java.util.stream.Collectors;


public class SongDetailsController implements Initializable, DataInitializable<UserWithData<Song>> {

    private Image DEFAULT_IMAGE = null;

    private PlaylistService playlistService;

    private SongService songService;

    @FXML
    private Label songName;

    @FXML
    private Label songLyrics;

    @FXML
    private Label genresLabel;

    @FXML
    private Label artistLabel;

    @FXML
    private Button playButton;

    @FXML
    private Label albumLabel;

    @FXML
    private Button savePlaylistButton;

     @FXML
     private Label playlistLabel;

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

    public void initializeData(UserWithData<Song> data) {
        song = data.getData();
        user = data.getUser();
        playlistLabel.setText("Playlist updated");
        playlistLabel.setVisible(false);

        try {
            userPlaylists = playlistService.getPlaylistsByUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.songName.setText(song.getName());
        this.songLyrics.setText(song.getLyrics());
        this.artistLabel.setText(song.getArtist().getName());
        this.genresLabel.setText(song.getGenre().getName());
        try{
            this.songImage.setImage(new Image(song.getPicture().toStream()));
        }catch (Exception e){
            e.printStackTrace();
        }

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

        playlistsChoice.addEventHandler(ComboBox.ON_SHOWING, event -> {
            playlistLabel.setVisible(false);
        });
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

        playlistLabel.setVisible(true);

    }

    @FXML
    public void playSong(){
        MusicPlayer musicPlayer = MusicPlayer.getInstance();
        musicPlayer.playOne(song);
    }
}