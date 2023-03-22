package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.ArtistService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Admin;
import it.univaq.disim.psvmsa.unify.model.Artist;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.components.AddLinkButton;
import it.univaq.disim.psvmsa.unify.view.components.SearchBar;
import it.univaq.disim.psvmsa.unify.view.components.ViewAlbum;
import it.univaq.disim.psvmsa.unify.view.components.ViewArtist;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ArtistController implements Initializable, DataInitializable<User> {

    @FXML
    private HBox searchBox;

    @FXML
    private HBox addBox;

    @FXML
    private SearchBar searchBar;

    private ArtistService artistService;

    private User user;

    @FXML
    private ListView<Artist> viewList;

    public ArtistController() {
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.artistService = factoryInstance.getArtistService();
    }

    @Override
    public void initializeData(User data) {
        this.user = data;
        if (user instanceof Admin) {
            addBox.getChildren().add(new AddLinkButton(Pages.EDITARTIST));
        }
        viewList.setCellFactory(artist -> new ListCell<>(){
            @Override
            protected void updateItem(Artist artist, boolean empty) {
                super.updateItem(artist, empty);
                if (empty || artist == null) {
                    setGraphic(null);
                } else {
                    setGraphic(new ViewArtist(new UserWithData<>(user, artist), user instanceof Admin));
                }
            }
        });
        viewList.setItems(FXCollections.observableArrayList(artistService.getArtists()));
    }

    public void initialize(URL location, ResourceBundle resources) {
        searchBar = new SearchBar("Search an Artist");
        searchBar.setOnSearch(search -> {
            List<Artist> artists = artistService.searchArtistsByName(search);
            viewList.getItems().clear();
            viewList.getItems().addAll(artists);
        });
        searchBox.getChildren().add(searchBar);
    }

}
