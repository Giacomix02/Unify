package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.*;
import it.univaq.disim.psvmsa.unify.view.components.SongRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;


import java.net.URL;
import java.util.ResourceBundle;

public class SongsController implements Initializable, DataInitializable {

    @FXML
    private ListView listView;

    private final SongService songService;

    public SongsController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.songService = factoryInstance.getSongService();
    }

    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Song> songs = FXCollections.observableList(songService.getAllSongs());

        songs.add(new Song("aa"));

        listView.setItems(songs);
        listView.setCellFactory(song -> new SongRowCell());


    }
}
