package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.controller.UserWithData;
import it.univaq.disim.psvmsa.unify.model.Song;
import it.univaq.disim.psvmsa.unify.model.User;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.util.List;

public class SongsListView extends ListView<Song> {
    public SongsListView(List<Song> songs, User user,boolean editable) {
        super();
        this.setCellFactory(param -> new ListCell<>(){
            @Override
            protected void updateItem(Song item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(new SongRow(new UserWithData<>(user, item), editable));
                }
            }
        });
    }
}
