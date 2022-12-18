package it.univaq.disim.psvmsa.unify.controller;


import it.univaq.disim.psvmsa.unify.business.PlaylistService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Playlist;
import it.univaq.disim.psvmsa.unify.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class EditPlaylistController implements Initializable, DataInitializable{

    @FXML
    private TextField playlistInput;

    @FXML
    private Button saveButton;

    @FXML
    private Button deleteButton;

    private Playlist playlist;

    private User user;

    @FXML
    private Label exceptionLabel;

    @FXML
    private Label label;

    private PlaylistService playlistService;


    public EditPlaylistController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.playlistService = factoryInstance.getPlaylistService();
    }

    public void initializeData(Object data) {

        if(data instanceof Playlist){
            this.playlist = (Playlist) data;
            this.playlistInput.setText(playlist.getName());
            this.deleteButton.setVisible(true);
            label.setText("Edit playlist");
        }else if(data instanceof User){
            this.user = (User) data;
            this.deleteButton.setVisible(false);
            this.saveButton.setText("Save");
            label.setText("Add playlist");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        exceptionLabel.setText("");

        this.saveButton
                .disableProperty()
                .bind(playlistInput
                        .textProperty()
                        .isEmpty()
                );
    }

    public void savePlaylist(){
        if (user != null) {
            Playlist playlist = new Playlist(playlistInput.getText(),user);
            playlistService.add(playlist);
            playlistInput.clear();
            exceptionLabel.setText("Playlist saved");
        }else{
            playlist.setName(playlistInput.getText());
            try {
            playlistService.update(playlist);
            }catch (Exception e){
                e.printStackTrace();
            }
            exceptionLabel.setText("Playlist edited");
        }
    }

    public void deletePlaylist(){
        try{
            playlistService.delete(playlist);
        }catch (Exception e){
            e.printStackTrace();
        }
        exceptionLabel.setText("Playlist deleted");
    }


    public void textFieldClick(){
        exceptionLabel.setText("");
    }
}
