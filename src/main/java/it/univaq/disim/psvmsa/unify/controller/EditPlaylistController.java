package it.univaq.disim.psvmsa.unify.controller;


import it.univaq.disim.psvmsa.unify.business.PlaylistService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Playlist;
import it.univaq.disim.psvmsa.unify.model.Song;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class EditPlaylistController implements Initializable, DataInitializable<UserWithData<Playlist>> {

    @FXML
    private TextField playlistInput;

    @FXML
    private Button saveButton;

    @FXML
    private Button deleteButton;

    private ViewDispatcher viewDispatcher;

    private Playlist playlist;

    private User user;

    @FXML
    private Label exceptionLabel;

    @FXML
    private Label label;

    @FXML
    private Label labelSongEdit;

    @FXML
    private CheckComboBox<Song> songChoice;

    @FXML
    private Button savePlaylistButton;


    private PlaylistService playlistService;

    @FXML
    private HBox spaceBox;

    @FXML
    private HBox buttonsBox;


    public EditPlaylistController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.playlistService = factoryInstance.getPlaylistService();
        viewDispatcher = ViewDispatcher.getInstance();
    }

    public void initializeData(UserWithData<Playlist> data) {
        user = data.getUser();
        playlist = data.getData();

        if(playlist!=null){
            this.playlistInput.setText(playlist.getName());
            this.deleteButton.setVisible(true);
            this.songChoice.setVisible(true);
            labelSongEdit.setVisible(true);
            label.setText("Edit playlist");
            songChoice.getItems().addAll(playlist.getSongs());
            songChoice.getCheckModel().checkAll();

        }else{
            this.deleteButton.setVisible(false);
            this.songChoice.setVisible(false);
            labelSongEdit.setVisible(false);
            this.saveButton.setText("Save");
            label.setText("Create playlist");
            this.spaceBox.setPrefHeight(0);
            this.buttonsBox.setPadding(new Insets(-45,20,0,0));
        }

        songChoice.setConverter(new StringConverter<>() {   // Convert Genre to String
            @Override
            public String toString(Song song) {
                if (song == null) return "";
                return song.getName();
            }

            @Override
            public Song fromString(String s) {
                for(Song song : playlist.getSongs()){
                    if(song.getName().equals(s)) return song;
                }
                return null;
            }
        });

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
        if (playlist == null) {
            Playlist playlist = new Playlist(playlistInput.getText(),user);
            playlistService.add(playlist);
            playlistInput.clear();
            exceptionLabel.setText("Playlist saved");
        }else{
            ArrayList<Song> songs = new ArrayList<>(songChoice.getCheckModel().getCheckedItems());
            playlist.setName(playlistInput.getText());
            playlist.setSongs(songs);
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
        try {
            viewDispatcher.navigateTo(Pages.PLAYLISTS,user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void textFieldClick(){
        exceptionLabel.setText("");
    }
}
