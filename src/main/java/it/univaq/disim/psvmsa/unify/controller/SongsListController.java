package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Admin;
import it.univaq.disim.psvmsa.unify.model.Song;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.components.AddLinkButton;
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
import java.util.stream.Collectors;

public class SongsListController implements Initializable, DataInitializable<UserWithData<List<Song>>> {

    @FXML
    private ListView<Song> listView;
    @FXML
    private HBox searchBox;
    @FXML

    private SearchBar searchBar;

    private List<Song> songs;


    ObservableList<Song> songsObservable = FXCollections.observableArrayList();


    @Override
    public void initializeData(UserWithData<List<Song>> userWithData) {
        this.songs = userWithData.getData();
        songsObservable = FXCollections.observableList(new ArrayList<>(this.songs));
            listView.setItems(songsObservable);
            listView.setCellFactory(song -> new ListCell<>(){
                @Override
                protected void updateItem(Song item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        setGraphic(new SongRow(new UserWithData<>(userWithData.getUser(), item), false));
                    }
                }
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
            songsObservable.clear();
            songsObservable.addAll(searchedSongs);
    }
}
