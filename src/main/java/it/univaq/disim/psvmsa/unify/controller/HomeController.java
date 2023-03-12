package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.*;
import it.univaq.disim.psvmsa.unify.model.*;
import it.univaq.disim.psvmsa.unify.view.components.SingleAlbum;
import it.univaq.disim.psvmsa.unify.view.components.SingleArtistHome;
import it.univaq.disim.psvmsa.unify.view.components.ViewPlaylist;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class HomeController implements DataInitializable<User>, Initializable {

    @FXML
    private GridPane playlistsPane;

    @FXML
    private FlowPane genresPane;

    @FXML
    private HBox randomArtist;

    @FXML
    private VBox album;
    @FXML
    private ListView<Playlist> viewList;

    @FXML
    private ListView<Artist> randomArtistsList;

    private User user;

    private ArtistService artistService;

    private PlaylistService playlistService;

    private GenreService genreService;

    private AlbumService albumService;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        artistService = factoryInstance.getArtistService();
        playlistService = factoryInstance.getPlaylistService();
        genreService = factoryInstance.getGenreService();
        albumService = factoryInstance.getAlbumService();
    }

    public void initializeData(User user) {

        this.user = user;

        try {

            List<Playlist> playlists = playlistService.getPlaylistsByUser(user);
            List<Artist> artists = artistService.getArtists();
            List<Album> albums = albumService.getAlbums();

            viewList.setCellFactory(playlist -> new ListCell<>(
                    ) {
                        @Override
                        protected void updateItem(Playlist playlist, boolean empty) {
                            super.updateItem(playlist, empty);
                            if (empty || playlist == null) {
                                setGraphic(null);
                            } else {
                                setGraphic(new ViewPlaylist(playlist, user));
                            }
                        }
                    }
            );


            viewList.setItems(FXCollections.observableArrayList(playlistService.getPlaylistsByUser(user)));

            for(int i=0;i<5&&i<artists.size();i++){
                Artist a = artists.get(i);
                randomArtist.getChildren().add(new SingleArtistHome(a,user));
            }

            for(int i=0;i<5&&i<albums.size();i++){
                Album a = albums.get(i);
                UserWithData<Album> data = new UserWithData<>(user,a);
                album.getChildren().add(new SingleAlbum(data));
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
