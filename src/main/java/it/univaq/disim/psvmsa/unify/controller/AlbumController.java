package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.AlbumService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Admin;
import it.univaq.disim.psvmsa.unify.model.Album;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.components.AddLinkButton;
import it.univaq.disim.psvmsa.unify.view.components.SearchBar;
import it.univaq.disim.psvmsa.unify.view.components.ViewAlbum;
import it.univaq.disim.psvmsa.unify.view.components.ViewUser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AlbumController implements Initializable, DataInitializable<User> {

    @FXML
    private HBox searchBox;

    @FXML
    private HBox addBox;

    @FXML
    private VBox viewList;

    @FXML
    private SearchBar searchBar;

    private User user;
    private AlbumService albumService;

    private ViewAlbum viewAlbum;

    public AlbumController() {
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.albumService = factoryInstance.getAlbumService();
    }

    @Override
    public void initializeData(User data) {
        this.user = data;
        if (user instanceof Admin){
            addBox.getChildren().add(new AddLinkButton(Pages.EDITALBUM));
        }

        List<Album> albums = albumService.getAlbums();

        for (Album album : albums) {
            UserWithData userWithData = new UserWithData(user, album);
            viewAlbum = new ViewAlbum(userWithData,user instanceof Admin);
            viewList.getChildren().add(viewAlbum);
        }

    }

    public void initialize(URL location, ResourceBundle resources) {
        searchBar = new SearchBar("Search by Album");

        searchBar.setOnSearch(text ->{
            showSearch(text);
        });

        searchBox.getChildren().add(searchBar);
    }

    public void showSearch(String text) {

        List<Album> albums = albumService.searchAlbumsByName(text);

        viewList.getChildren().clear();

        for (Album album : albums) {
            UserWithData userWithData = new UserWithData(user, album);
            viewAlbum = new ViewAlbum(userWithData, user instanceof Admin);
            viewList.getChildren().add(viewAlbum);
        }
    }
}

