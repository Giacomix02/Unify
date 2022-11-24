package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.ArtistService;
import it.univaq.disim.psvmsa.unify.business.PictureService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Artist;
import it.univaq.disim.psvmsa.unify.model.GroupArtist;
import it.univaq.disim.psvmsa.unify.model.Picture;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddArtistController implements Initializable, DataInitializable {


    private Image DEFAULT_IMAGE = null;

    private ArtistService artistService;

    private Picture picture;

    private PictureService pictureService;

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

    @FXML
    private ChoiceBox<String> artistBoxChoice;

    private ObservableList<String> options;

    public AddArtistController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.artistService = factoryInstance.getArtistService();
        this.pictureService = factoryInstance.getPictureService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        DEFAULT_IMAGE = artistImage.getImage();

        options = FXCollections.observableArrayList(
                "Artist",
                    "GroupArtist"
        );

        artistBoxChoice.getItems().addAll(options);

        this.saveButton
                .disableProperty()
                .bind(artistNameInput
                        .textProperty()
                        .isEmpty().and(
                        artistBiographyInput
                                .textProperty()
                                .isEmpty()).and(
                        uploadImageButton.onMouseClickedProperty().isNull()
                        )
                );
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
        try(FileInputStream inputStream = new FileInputStream(file)){
            picture = new Picture(inputStream.readAllBytes());
            artistImage.setImage(new Image(picture.toStream()));
        }catch(IOException e){
            System.out.println(e);
        }
    }

    public void saveArtist(){
        Picture p = pictureService.add(picture);
        if (artistBoxChoice.getSelectionModel().equals("Artist")) {
            Artist artist = new Artist(artistNameInput.getText(), artistBiographyInput.getText(), p);
            save(artist);
        } else {
            GroupArtist groupArtist = new GroupArtist(artistNameInput.getText(), artistBiographyInput.getText(), p);
            save(groupArtist);
        }
    }

    public void save(Artist artist){
        artistService.add(artist);
        artistNameInput.clear();
        artistBiographyInput.clear();
        artistImage.setImage(DEFAULT_IMAGE);
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
