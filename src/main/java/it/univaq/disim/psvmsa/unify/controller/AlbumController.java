package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.AlbumService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.view.components.AddAlbum;
import it.univaq.disim.psvmsa.unify.view.components.SearchBar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AlbumController implements Initializable, DataInitializable {

    @FXML
    private HBox searchBox;

    @FXML
    private HBox addBox;

    @FXML
    private SearchBar searchBar;

    private AddAlbum addAlbum;

    private AlbumService albumService;

    public AlbumController() {
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.albumService = factoryInstance.getAlbumService();
    }
    public void initialize(URL location, ResourceBundle resources) {
        searchBar = new SearchBar("Search by Album");
        addAlbum = new AddAlbum();

        searchBox.getChildren().add(searchBar);
        addBox.getChildren().add(addAlbum);
    }

}

