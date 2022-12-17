package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.*;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.components.AddLinkButton;
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

public class SongController implements Initializable, DataInitializable<User> {

    @FXML
    private ListView listView;
    @FXML
    private HBox searchBox;
    @FXML
    private HBox addBox;

    private User user;
    private SearchBar searchBar;

    private SongService songService;

    public SongController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.songService = factoryInstance.getSongService();
    }

    @Override
    public void initializeData(User data) {
        this.user = data;
        if(user instanceof Admin){
            addBox.getChildren().add(new AddLinkButton(Pages.EDITSONG));
        }
        try{
            ObservableList<Song> songs = FXCollections.observableList(songService.getAllSongs());
            listView.setItems(songs);
            listView.setCellFactory(song -> new SongRowCell(user instanceof Admin));
        }catch(BusinessException e){
            e.printStackTrace();
        }
    }

    public void initialize(URL location, ResourceBundle resources) {

        searchBar = new SearchBar("Search by Song");

        searchBox.getChildren().add(searchBar);


        searchBar.setOnSearch(text ->{
            showSearch(text);
        });
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
