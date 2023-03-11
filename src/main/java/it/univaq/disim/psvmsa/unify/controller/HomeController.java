package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.ArtistService;
import it.univaq.disim.psvmsa.unify.business.GenreService;
import it.univaq.disim.psvmsa.unify.business.PlaylistService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Artist;
import it.univaq.disim.psvmsa.unify.model.Genre;
import it.univaq.disim.psvmsa.unify.model.Playlist;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.components.ViewGenre;
import it.univaq.disim.psvmsa.unify.view.components.ViewPlaylist;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements DataInitializable<User>, Initializable {
    @FXML
    private GridPane playlistsPane;

    @FXML
    private FlowPane genresPane;

    @FXML
    private HBox randomArtist;

     @FXML
     private VBox playlistsBox;

     @FXML
     private HBox genresBox;

    @FXML
    private ListView<Artist> randomArtistsList;

    private User user;
    private ArtistService artistService;
    private PlaylistService playlistService;

    private GenreService genreService;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        artistService = factoryInstance.getArtistService();
        playlistService = factoryInstance.getPlaylistService();
        genreService = factoryInstance.getGenreService();
    }

    public void initializeData(User user){

        this.user = user;
        try {
            List<Playlist> playlists = playlistService.getPlaylistsByUser(user);
            List<Artist> artists = artistService.getArtists();
            List<Genre> genres = genreService.getGenres();

            for(Playlist playlist : playlists){
                playlistsBox.getChildren().add(new ViewPlaylist(playlist, user));
            }



        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
