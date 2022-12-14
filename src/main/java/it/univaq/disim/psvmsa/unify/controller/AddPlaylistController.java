package it.univaq.disim.psvmsa.unify.controller;


import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.GenreService;
import it.univaq.disim.psvmsa.unify.business.PlaylistService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Genre;
import it.univaq.disim.psvmsa.unify.model.Playlist;
import it.univaq.disim.psvmsa.unify.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class AddPlaylistController implements Initializable, DataInitializable<User>{

    @FXML
    private TextField playlistInput;
    @FXML
    private Button saveButton;

    private User user;
    @FXML
    private Label exceptionLabel;

    private PlaylistService playlistService;

    public AddPlaylistController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.playlistService = factoryInstance.getPlaylistService();
    }

    public void initializeData(User data) {
        this.user = data;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        exceptionLabel.setText("");

        this.saveButton
                .disableProperty()
                .bind(playlistInput
                        .textProperty()
                        .isEmpty()
                );
    }

    public void saveGenre(){
        Playlist playlist = new Playlist(playlistInput.getText(),user);
        playlistService.add(playlist);
        playlistInput.clear();
    }


    public void textFieldClick(){
        exceptionLabel.setText("");
    }
}
