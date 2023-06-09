
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
import java.io.FileInputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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

    private User user;


    public AlbumDetailsController() {
    }


    public void initializeData(UserWithData<Album> data) {
        this.album = data.getData();
        this.user = data.getUser();
        albumName.setText(album.getName());

        if(album.getSongs().size() > 0) {
            albumImagePreview.setImage(new Image(album.getSongs().get(0).getPicture().toStream()));
        }else{
            try {
                Picture picture = new Picture(new FileInputStream("src/main/resources/ui/images/album-placeholder.png"));
                albumImagePreview.setImage(new Image(picture.toStream()));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        artistLabel.setText("Artist: " + album.getArtist().getName());
        genreLabel.setText("Genre: " + album.getGenre().getName());
        updateSongs(album.getSongs());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void updateSongs(List<Song> songs) {
        albumSongs.getChildren().clear();
        try{
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
