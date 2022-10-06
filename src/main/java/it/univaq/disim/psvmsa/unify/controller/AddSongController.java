package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.*;
import it.univaq.disim.psvmsa.unify.model.*;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private Button uploadImageButton;
    @FXML
    private TextArea songLyricsInput;

    @FXML
    private Button exit;

    @FXML
    private Button saveButton;

    @FXML
    private Label saveImageLabel;

    @FXML
    private Button uploadSongButton;

    @FXML
    private ChoiceBox<String> albumBoxChoice;

    @FXML
    private ChoiceBox<String> artistBoxChoice;

    @FXML
    private ChoiceBox<Genre> genreBoxChoice;
    @FXML
    private Label saveSongLabel;


    private Picture picture;
    private List<Genre> genres;

    private FileInputStream songFileInputStream;



    public AddSongController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.songService = factoryInstance.getSongService();

        this.artistService = factoryInstance.getArtistService();
        this.albumService = factoryInstance.getAlbumService();
        this.genreService = factoryInstance.getGenreService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        saveSongLabel.setVisible(false);
        saveImageLabel.setVisible(false);

        List< Genre > genres = genreService.getGenres();
        //List < Artist > artists = artistService.getArtists();
        //List < Album > albums = albumService.getAlbums();


        genreBoxChoice.setConverter(new StringConverter<>() {
            @Override
            public String toString(Genre genre) {
                if(genre!=null){
                    return genre.getName();
                }
                return "";
            }

            @Override
            public Genre fromString(String s) {
                List<Genre> genres = genreService.searchByName(s);
                return genres.get(0);
            }
        });



        for(Genre genre : genres){
            genreBoxChoice.getItems().add(genre);
        }

    }

    public void uploadImage() throws FileNotFoundException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a picture");
        fileChooser.showDialog(null, "Upload");

        File file =  fileChooser.getSelectedFile();
        FileInputStream inputStream = new FileInputStream(file);
        picture = new Picture(inputStream);

        saveImageLabel.setVisible(true);    // advise the user that the image has been uploaded
        //TODO exeption?
    }

    public void uploadSong() throws FileNotFoundException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a song");
        fileChooser.showDialog(null, "Upload");

        File file =  fileChooser.getSelectedFile();
        songFileInputStream = new FileInputStream(file);



        saveSongLabel.setVisible(true);
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

        genres.add(genreService.getById(genreBoxChoice.getSelectionModel().getSelectedItem().getId()));
        song.setGenres(genres);

        songService.add(song);
        songNameInput.clear();
        saveImageLabel.setVisible(false);
    }
}
