package it.univaq.disim.psvmsa.unify.controller;


import it.univaq.disim.psvmsa.unify.business.PlaylistService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.User;

import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import it.univaq.disim.psvmsa.unify.view.components.AddLinkButton;
import it.univaq.disim.psvmsa.unify.view.components.SearchBar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistController implements Initializable, DataInitializable<User>{
    private User user;
    private PlaylistService playlistService;

    public PlaylistController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.playlistService = factoryInstance.getPlaylistService();
    }


    public void initializeData(User data) {
        this.user = data;

        try {
            playlistService.getPlaylistsByUser(data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void addPlaylist(){
        try {
            ViewDispatcher.getInstance().navigateTo(Pages.ADDPLAYLIST, this.user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
