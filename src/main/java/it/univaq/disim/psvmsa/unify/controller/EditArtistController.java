package it.univaq.disim.psvmsa.unify.controller;


import it.univaq.disim.psvmsa.unify.business.ArtistService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Artist;
import it.univaq.disim.psvmsa.unify.model.Picture;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class EditArtistController implements Initializable, DataInitializable {

    @FXML
    private Button saveButton;
    @FXML
    private Button exit;
    @FXML
    private Button uploadImageButton;
    @FXML
    private TextField artistNameInput;
    @FXML
    private ImageView artistImage;
    @FXML
    private TextField artistBiographyInput;

    private ArtistService artistService;

    public EditArtistController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.artistService = factoryInstance.getArtistService();
    }

    public void initializeData(Object data){
        Artist artist = (Artist) data;
        artistNameInput.setText(artist.getName());
        artistBiographyInput.setText(artist.getBiography());

        Picture picture = artist.getPicture();
        Image image = new Image(picture.toStream());
        artistImage.setImage(image);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }


}
