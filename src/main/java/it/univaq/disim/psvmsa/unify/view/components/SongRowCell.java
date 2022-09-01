package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.model.Song;
import javafx.scene.control.ListCell;

public class SongRowCell extends ListCell<Song> {


    @Override
    protected void updateItem(Song song,boolean empty){
        super.updateItem(song,empty);
        if(empty) {
            this.setGraphic(null);
        }
        else {
            this.setGraphic(new SongRow(song));
        }
    }
}
