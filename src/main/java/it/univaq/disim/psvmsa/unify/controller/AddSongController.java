package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.*;
import it.univaq.disim.psvmsa.unify.model.*;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class AddSongController implements Initializable, DataInitializable{

    private final SongService songService;

    private final AlbumService albumService;
    private final ArtistService artistService;
    private final GenreService genreService;

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

    @FXML
    private ChoiceBox<String> albumBoxChoice;

    @FXML
    private ChoiceBox<String> artistBoxChoice;

    @FXML
    private ChoiceBox<String> genreBoxChoice;


    private Picture picture;
    private List<Genre> genres;



    public AddSongController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.songService = factoryInstance.getSongService();

        this.artistService = factoryInstance.getArtistService();
        this.albumService = factoryInstance.getAlbumService();
        this.genreService = factoryInstance.getGenreService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        saveImageLabel.setVisible(false);

        List< Genre > genres = genreService.getGenres();
        //List < Artist > artists = artistService.getArtists();
        //List < Album > albums = albumService.getAlbums();

        for(Genre genre : genres){
            genreBoxChoice.getItems().add(genre.getName());
        }

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

        genres.add(genreService.getById(genreBoxChoice.getSelectionModel().getSelectedIndex()));
        song.setGenres(genres);

        songService.add(song);
        songNameInput.clear();
        saveImageLabel.setVisible(false);
    }
}
