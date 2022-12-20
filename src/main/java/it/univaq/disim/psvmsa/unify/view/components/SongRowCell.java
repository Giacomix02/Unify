package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.controller.UserWithData;
import it.univaq.disim.psvmsa.unify.model.Admin;
import it.univaq.disim.psvmsa.unify.model.Song;
import it.univaq.disim.psvmsa.unify.model.User;
import javafx.scene.control.ListCell;

public class SongRowCell extends ListCell<Song> {

    User user;
    public SongRowCell(User user) {
        this.user = user;
    }

    @Override
    protected void updateItem(Song song,boolean empty){
        super.updateItem(song,empty);
        if(empty) {
            this.setGraphic(null);
        }
        else {
            this.setGraphic(new SongRow(new UserWithData(user, song), user instanceof Admin));
        }
    }
}
