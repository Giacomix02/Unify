package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.*;
import it.univaq.disim.psvmsa.unify.model.*;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
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
import java.util.*;

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
                                ViewPlaylist viewPlaylist = new ViewPlaylist(playlist);
                                viewPlaylist.setOnEditClicked(playlist1 -> {
                                    try {
                                        ViewDispatcher.getInstance().navigateTo(Pages.EDITPLAYLIST, playlist1);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                                viewPlaylist.setOnPlaylistClicked(playlist1 -> {
                                    try {
                                        ViewDispatcher.getInstance().navigateTo(Pages.SONGSLIST, new UserWithData<>(user,playlist1.getSongs()));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                                setGraphic(viewPlaylist);
                            }
                        }
                    }
            );

            List<Playlist> playlists = playlistService.getPlaylistsByUser(user);

            viewList.setItems(FXCollections.observableArrayList(playlists));
            int max = 5;
            Collections.shuffle(artists);
            for(Artist a:artists){
                if(max-- <= 0 ) break;
                randomArtist.getChildren().add(new SingleArtistHome(a,user));
            }
            max = 5;
            Collections.shuffle(albums);
            for(Album a:albums){
                if(max-- <= 0 ) break;
                SingleAlbum singleAlbum = new SingleAlbum(a);
                singleAlbum.setOnAlbumClick(album -> {
                    try {
                        ViewDispatcher.getInstance().navigateTo(Pages.ALBUMDETAILS,new UserWithData<>(user,album));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
                album.getChildren().add(singleAlbum);
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
