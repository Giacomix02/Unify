package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.ArtistService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Artist;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.components.AddLinkButton;
import it.univaq.disim.psvmsa.unify.view.components.SearchBar;
import it.univaq.disim.psvmsa.unify.view.components.ViewArtist;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ArtistController implements Initializable, DataInitializable {

    @FXML
    private HBox searchBox;

    @FXML
    private HBox addBox;

    @FXML
    private SearchBar searchBar;

    private ArtistService artistService;

    private ViewArtist viewArtist;

    @FXML
    private VBox viewList;

    public ArtistController() {
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.artistService = factoryInstance.getArtistService();
    }

    public void initialize(URL location, ResourceBundle resources) {
        searchBar = new SearchBar("Search by Artist");
        searchBox.getChildren().add(searchBar);
        addBox.getChildren().add(new AddLinkButton(Pages.ADDARTIST));
        List<Artist> artists = artistService.getArtists();
        for(Artist artist : artists) {
            viewArtist = new ViewArtist(artist);
            viewList.getChildren().add(viewArtist);
    }

}
}
