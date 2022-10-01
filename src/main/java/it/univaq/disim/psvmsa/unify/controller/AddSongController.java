package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Picture;
import it.univaq.disim.psvmsa.unify.model.Song;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddSongController implements Initializable, DataInitializable{

    private final SongService songService;

    @FXML
    private TextField songNameInput;

    @FXML
    private Button saveImageButton;
    @FXML
    private TextArea songLyricsInput;

    @FXML
    private Button exit;

    @FXML
    private Button save;

    @FXML
    private Label saveImageLabel;

    private Picture picture;



    public AddSongController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.songService = factoryInstance.getSongService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveImageLabel.setVisible(false);
    }

    public void uploadImage() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a picture");
        fileChooser.showDialog(null, "Upload");

        File file =  fileChooser.getSelectedFile();
        FileInputStream inputStream = new FileInputStream(file);
        picture = new Picture(inputStream);

        saveImageLabel.setVisible(true);    // advise the user that the image has been uploaded
        //TODO exeption?
    }

    public void exit(){
        try {
            ViewDispatcher.getInstance().navigateTo(Pages.SONGS);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void saveSong(){
        Song song = new Song(songNameInput.getText());
        song.setLyrics(songLyricsInput.getText());
        song.setPicture(picture);

        songService.add(song);
        songNameInput.clear();
        saveImageLabel.setVisible(false);
    }
}
