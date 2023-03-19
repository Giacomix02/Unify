package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.controller.SongsListController;
import it.univaq.disim.psvmsa.unify.controller.UserWithData;
import it.univaq.disim.psvmsa.unify.model.Song;
import it.univaq.disim.psvmsa.unify.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.util.List;
import java.util.function.Consumer;

public class SongsListView extends ListView<Song> {
    Consumer<Song> onEditClicked;
    Consumer<Song> onSongClicked;
    Consumer<Song> onPlayButtonClicked;

    ObservableList<Song> songsObservable = FXCollections.observableArrayList();

    public SongsListView(List<Song> songs, User user,boolean editable) {
        super();
        this.setCellFactory(param -> new ListCell<>(){
            @Override
            protected void updateItem(Song item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    SongRow row = new SongRow(new UserWithData<>(user, item), editable);
                    row.setOnEditClicked(onEditClicked);
                    row.setOnSongClicked(onSongClicked);
                    row.setOnPlayButtonClicked(onPlayButtonClicked);
                    setGraphic(row);
                }
            }
        });
        //style="-fx-background-color: transparent;" VBox.vgrow="ALWAYS"
        this.setStyle("-fx-background-color: transparent;");
        this.setPrefHeight(USE_COMPUTED_SIZE);
        this.setPrefWidth(USE_COMPUTED_SIZE);
        this.setMinHeight(USE_COMPUTED_SIZE);
        this.setMinWidth(USE_COMPUTED_SIZE);
        this.setMaxHeight(USE_COMPUTED_SIZE);
        this.setMaxWidth(USE_COMPUTED_SIZE);
        this.setItems(songsObservable);
        //set vbox grow
        songsObservable.addAll(songs);
    }

    public void setSongs(List<Song> songs){
        songsObservable.clear();
        songsObservable.addAll(songs);
    }

    public void setOnEditClicked(Consumer<Song> consumer){
        this.onEditClicked = consumer;
        this.getChildren().forEach(node -> {
            if(node instanceof SongRow){
                ((SongRow) node).setOnEditClicked(consumer);
            }
        });
    }
    public void setOnSongClicked(Consumer<Song> consumer){
        this.onSongClicked = consumer;
        this.getChildren().forEach(node -> {
            if(node instanceof SongRow){
                ((SongRow) node).setOnSongClicked(consumer);
            }
        });
    }
    public void setOnPlayButtonClicked(Consumer<Song> consumer){
        this.onPlayButtonClicked = consumer;
        this.getChildren().forEach(node -> {
            if(node instanceof SongRow){
                ((SongRow) node).setOnPlayButtonClicked(consumer);
            }
        });
    }
}
