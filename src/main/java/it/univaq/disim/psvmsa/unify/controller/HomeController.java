package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.ArtistService;
import it.univaq.disim.psvmsa.unify.business.PlaylistService;
import it.univaq.disim.psvmsa.unify.model.Artist;
import it.univaq.disim.psvmsa.unify.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements DataInitializable<User>, Initializable {
    @FXML
    private GridPane playlistsPane;

    @FXML
    private FlowPane genresPane;

    @FXML
    private ListView<Artist> randomArtistsList;

    private User user;
    private ArtistService artistService;
    private PlaylistService playlistService;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initializeData(User user){
        this.user = user;
    }
}
