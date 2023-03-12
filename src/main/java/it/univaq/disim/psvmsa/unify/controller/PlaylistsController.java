package it.univaq.disim.psvmsa.unify.controller;


import it.univaq.disim.psvmsa.unify.business.PlaylistService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Playlist;
import it.univaq.disim.psvmsa.unify.model.User;

import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import it.univaq.disim.psvmsa.unify.view.components.ViewPlaylist;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PlaylistsController implements Initializable, DataInitializable<User>{
    private User user;
    private PlaylistService playlistService;

    private List<Playlist> playlists;

    @FXML
    private VBox playlistsBox;

    public PlaylistsController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.playlistService = factoryInstance.getPlaylistService();
    }


    public void initializeData(User data) {
        this.user = data;

        try {
            playlists = playlistService.getPlaylistsByUser(data);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (playlists!=null) {
            for (Playlist playlist : playlists) {
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
                        ViewDispatcher.getInstance().navigateTo(Pages.SONGSLIST, playlist.getSongs());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                playlistsBox.getChildren().add(viewPlaylist);
            }
        }
    }

    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void addPlaylist(){
        try {
            UserWithData userWithData = new UserWithData(user,null);
            ViewDispatcher.getInstance().navigateTo(Pages.EDITPLAYLIST, userWithData);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
