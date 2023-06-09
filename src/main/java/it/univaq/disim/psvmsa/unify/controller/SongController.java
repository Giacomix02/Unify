package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.*;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import it.univaq.disim.psvmsa.unify.view.components.AddLinkButton;
import it.univaq.disim.psvmsa.unify.view.components.MusicPlayer;
import it.univaq.disim.psvmsa.unify.view.components.SearchBar;
import it.univaq.disim.psvmsa.unify.view.components.SongRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SongController implements Initializable, DataInitializable<User> {

    @FXML
    private ListView<Song> listView;
    @FXML
    private HBox searchBox;
    @FXML
    private HBox addBox;

    private User user;

    private SearchBar searchBar;

    private List<Song> songs;

    private SongService songService;

    ObservableList<Song> songsObservable = FXCollections.observableArrayList();

    public SongController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.songService = factoryInstance.getSongService();
    }

    @Override
    public void initializeData(User u) {
        this.user = u;
        try{
            songsObservable = FXCollections.observableList(songService.getAllSongs());
            listView.setItems(songsObservable);
            listView.setCellFactory(song -> new ListCell<>(){
                @Override
                protected void updateItem(Song item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        SongRow songRow = new SongRow(item, user instanceof Admin);
                        setGraphic(songRow);

                        songRow.setOnSongClicked(s -> {
                            try {
                                UserWithData<Song> data = new UserWithData<>(user, s);
                                ViewDispatcher.getInstance().navigateTo(Pages.SONGDETAILS, data);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                        songRow.setOnEditClicked(s -> {
                            try {
                                UserWithData<Song> data = new UserWithData<>(user, s);
                                ViewDispatcher.getInstance().navigateTo(Pages.EDITSONG, data);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        songRow.setOnPlayButtonClicked(s -> {
                            MusicPlayer.getInstance().playOne(s);
                        });

                    }
                }
            });
        } catch(BusinessException e){
            e.printStackTrace();
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        searchBar = new SearchBar("Search a Song");
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
