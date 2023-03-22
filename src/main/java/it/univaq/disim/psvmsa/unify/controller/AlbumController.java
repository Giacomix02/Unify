package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.AlbumService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Admin;
import it.univaq.disim.psvmsa.unify.model.Album;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import it.univaq.disim.psvmsa.unify.view.components.AddLinkButton;
import it.univaq.disim.psvmsa.unify.view.components.MusicPlayer;
import it.univaq.disim.psvmsa.unify.view.components.SearchBar;
import it.univaq.disim.psvmsa.unify.view.components.ViewAlbum;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

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

    private MusicPlayer musicPlayer;


    public AlbumController() {
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.albumService = factoryInstance.getAlbumService();
        this.musicPlayer = MusicPlayer.getInstance();
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
                    ViewAlbum viewAlbum = new ViewAlbum(album, user instanceof Admin);
                    viewAlbum.setOnAlbumClicked(a -> {
                        try{
                            ViewDispatcher.getInstance().navigateTo(Pages.ALBUMDETAILS, new UserWithData<>(user, album));
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }
                    });

                    viewAlbum.setOnEditClicked(a -> {
                        try{
                            UserWithData<Album> data = new UserWithData<>(user, a);
                            ViewDispatcher.getInstance().navigateTo(Pages.EDITALBUM, data);
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }
                    });

                    viewAlbum.setOnPlayClicked(a -> {
                        musicPlayer.setQueue(album.getSongs());
                        musicPlayer.startQueuePlayback();
                    });

                    setGraphic(viewAlbum);
                }
            }
        });

    }

    public void initialize(URL location, ResourceBundle resources) {
        searchBar = new SearchBar("Search an Album");
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

