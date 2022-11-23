package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.*;
import it.univaq.disim.psvmsa.unify.view.components.AddSong;
import it.univaq.disim.psvmsa.unify.view.components.SearchBar;
import it.univaq.disim.psvmsa.unify.view.components.SongRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SongController implements Initializable, DataInitializable {

    @FXML
    private ListView listView;
    @FXML
    private HBox searchBox;
    @FXML
    private HBox addBox;


    private SearchBar searchBar;
    private AddSong addSong;

    private SongService songService;

    public SongController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.songService = factoryInstance.getSongService();
    }

    public void initialize(URL location, ResourceBundle resources) {

        searchBar = new SearchBar("Search by Song");
        addSong = new AddSong();

        searchBox.getChildren().add(searchBar);
        addBox.getChildren().add(addSong);

        searchBar.setOnSearch(text ->{
            showSearch(text);
        });

        try{
            ObservableList<Song> songs = FXCollections.observableList(songService.getAllSongs());
            listView.setItems(songs);
            listView.setCellFactory(song -> new SongRowCell());
        }catch(BusinessException e){
            e.printStackTrace();
        }
    }

    public void showSearch(String text){
        try{
            List<Song> searchedSongs = new ArrayList<>(songService.searchByName(text));
            listView.setItems(FXCollections.observableList(searchedSongs));


        }catch(BusinessException e){
            e.printStackTrace();
        }

    }
}
