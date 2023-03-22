
package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.model.*;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcherException;
import it.univaq.disim.psvmsa.unify.view.components.MusicPlayer;
import it.univaq.disim.psvmsa.unify.view.components.SongsListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AlbumDetailsController implements Initializable, DataInitializable<UserWithData<Album>> {

    @FXML
    private VBox albumSongs;

    @FXML
    private ImageView albumImagePreview;

    @FXML
    private Label songLabel;

    @FXML
    private Label albumName;

    @FXML
    private Label artistLabel;

    @FXML
    private Label genreLabel;

    private Album album;

    private String joinGenre;

    private User user;


    public AlbumDetailsController() {
    }


    public void initializeData(UserWithData<Album> data) {
        this.album = data.getData();
        this.user = data.getUser();
        albumName.setText(album.getName());

        if(album.getSongs() != null) {
            albumImagePreview.setImage(new Image(album.getSongs().get(0).getPicture().toStream()));
        }

        artistLabel.setText("Artist: " + album.getArtist().getName());

        for (Song song : album.getSongs()) {
            joinGenre = song.getGenres().stream().map(genre->genre.getName()).collect(Collectors.joining(", "));
        }

        genreLabel.setText("Genres: " + joinGenre);

        setSongs();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void setSongs() {
        albumSongs.getChildren().clear();
        try{
            List<Song> songs = new ArrayList<>(album.getSongs());
            SongsListView songsListView = new SongsListView(songs, user, false);
            albumSongs.getChildren().add(songsListView);

            songsListView.setOnDetailsClicked(song -> {
                try {
                    ViewDispatcher.getInstance().navigateTo(Pages.SONGDETAILS, new UserWithData<>(user, song));
                } catch (ViewDispatcherException e) {
                    throw new RuntimeException(e);
                }
            });

            songsListView.setOnPlayClicked(song -> {
                MusicPlayer.getInstance().playOne(song);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void playAlbum(){
        MusicPlayer musicPlayer = MusicPlayer.getInstance();
        musicPlayer.setQueue(album.getSongs());
        musicPlayer.startQueuePlayback();
    }
}
