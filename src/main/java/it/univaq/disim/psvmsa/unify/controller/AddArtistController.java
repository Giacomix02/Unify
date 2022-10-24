package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.ArtistService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Artist;
import it.univaq.disim.psvmsa.unify.model.Picture;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddArtistController implements Initializable, DataInitializable {


    private final String DEFAULT_IMAGE = "src/main/resources/images/artist-placeholder.png";

    private final ArtistService artistService;

    private Picture picture;

    @FXML
    private ImageView artistImage;

    @FXML
    private Button saveButton;

    @FXML
    private Button exit;

    @FXML
    private Button uploadImageButton;

    @FXML
    private TextField artistNameInput;

    @FXML
    private TextArea artistBiographyInput;


    public AddArtistController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.artistService = factoryInstance.getArtistService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){


    }

    public void uploadImage() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        fileChooser.setTitle("Choose a picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        FileInputStream inputStream = new FileInputStream(file);
        picture = new Picture(inputStream);
        artistImage.setImage(new Image(inputStream));
    }

    public void saveArtist(){
        Artist artist = new Artist(artistNameInput.getText(), artistBiographyInput.getText(), picture);

        artist.setName(artistNameInput.getText());
        artist.setBiography(artistBiographyInput.getText());
        artist.setPicture(picture);

        artistService.add(artist);
        artistNameInput.clear();
        artistBiographyInput.clear();
        artistImage.setImage(new Image(DEFAULT_IMAGE));
    }

    public void exit(){
        try{
            ViewDispatcher.getInstance().navigateTo(Pages.ARTISTS);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
