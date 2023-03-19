package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Admin;
import it.univaq.disim.psvmsa.unify.model.Song;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import it.univaq.disim.psvmsa.unify.view.components.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SongsListController implements Initializable, DataInitializable<UserWithData<List<Song>>> {

    @FXML
    private VBox listViewWrapper;
    @FXML
    private HBox searchBox;
    @FXML

    private SearchBar searchBar;

    private List<Song> songs;

    private SongsListView songsList;
    ObservableList<Song> songsObservable = FXCollections.observableArrayList();


    @Override
    public void initializeData(UserWithData<List<Song>> userWithData) {
        this.songs = userWithData.getData();
        songsList = new SongsListView(this.songs, userWithData.getUser(), false);
        listViewWrapper.getChildren().add(songsList);
        songsList.setOnSongClicked(song -> {
            try {
                ViewDispatcher.getInstance().navigateTo(Pages.SONGDETAILS,new UserWithData<>(userWithData.getUser(), song));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        songsList.setOnPlayButtonClicked(song -> {
            MusicPlayer.getInstance().playOne(song);
        });
    }

    public void initialize(URL location, ResourceBundle resources) {
        searchBar = new SearchBar("Search by Song");
        searchBox.getChildren().add(searchBar);
        searchBar.setOnSearch(text ->{
            showSearch(text);
        });
    }

    public void showSearch(String text){
            List<Song> searchedSongs = songs.stream()
                    .filter(song ->
                            song.getName()
                                    .toLowerCase()
                                    .contains(text.toLowerCase()))
                    .collect(Collectors.toList());
        songsList.setSongs(searchedSongs);
    }
}
