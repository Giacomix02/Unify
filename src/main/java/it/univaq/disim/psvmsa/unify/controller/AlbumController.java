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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
    private ListView<Album> viewList;

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

        try{
            viewList.setItems(FXCollections.observableList(albumService.getAlbums()));
        }catch (Exception e){
            e.printStackTrace();
        }

        viewList.setCellFactory(album -> new ListCell<>(){
            @Override
            protected void updateItem(Album album, boolean empty) {
                super.updateItem(album, empty);
                if (empty || album == null) {
                    setGraphic(null);
                } else {
                    setGraphic(new ViewAlbum(new UserWithData<>(user, album), user instanceof Admin));
                }
            }
        });

    }

    public void initialize(URL location, ResourceBundle resources) {
        searchBar = new SearchBar("Search by Album");
        searchBar.setOnSearch(text ->{
            showSearch(text);
        });
        searchBox.getChildren().add(searchBar);
    }

    public void showSearch(String text) {
        try{
            List<Album> albums = albumService.searchAlbumsByName(text);
            this.viewList.getItems().clear();
            this.viewList.getItems().addAll(albums);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

