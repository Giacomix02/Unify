package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.ArtistService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.view.components.AddArtist;
import it.univaq.disim.psvmsa.unify.view.components.SearchBar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ArtistController implements Initializable, DataInitializable {

    @FXML
    private HBox searchBox;

    @FXML
    private HBox addBox;

    @FXML
    private SearchBar searchBar;

    private AddArtist addArtist;

    private ArtistService artistService;

    public ArtistController() {
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.artistService = factoryInstance.getArtistService();
    }

    public void initialize(URL location, ResourceBundle resources) {
        searchBar = new SearchBar("Search by Artist");
        addArtist = new AddArtist();

        searchBox.getChildren().add(searchBar);
        addBox.getChildren().add(addArtist);
    }
}
