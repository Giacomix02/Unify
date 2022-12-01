package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.ArtistService;
import it.univaq.disim.psvmsa.unify.business.PictureService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Artist;
import it.univaq.disim.psvmsa.unify.model.Genre;
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
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddArtistController implements Initializable, DataInitializable {
    private ArtistService artistService;
    private PictureService pictureService;
    @FXML
    private Button saveButton;

    @FXML
    private Button addPicture;

    @FXML
    private Button exit;

    @FXML
    private HBox artistPictures;

    @FXML
    private HBox membersPickerBox;
    @FXML
    private CheckComboBox<Artist> membersPicker;
    @FXML
    private TextField artistNameInput;

    @FXML
    private TextArea artistBiographyInput;

    @FXML
    private ChoiceBox<String> artistTypeChoiceBox;

    private ObservableList<String> options = FXCollections.observableArrayList("Single","Group");

    private List<Picture> images = new ArrayList<>();
    public AddArtistController() {
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.artistService = factoryInstance.getArtistService();
        this.pictureService = factoryInstance.getPictureService();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        artistTypeChoiceBox.getItems().addAll(options);
        artistTypeChoiceBox.setValue(options.get(0));
        //TODO finish this
        this.saveButton
                .disableProperty()
                .bind(artistNameInput
                        .textProperty()
                        .isEmpty()
                );
        //show only if the artisty type is Group
        membersPickerBox.visibleProperty().bind(
                artistTypeChoiceBox.valueProperty().isEqualTo("Group")
        );
        addPicture.setOnAction(event -> {
            try{
                askForPicture();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        membersPicker.setConverter(new StringConverter<>() {
            @Override
            public String toString(Artist artist) {
                if(artist == null) return "";
                return artist.getName();
            }

            @Override
            public Artist fromString(String s) {
                for(Artist a: membersPicker.getItems()){
                    if(a.getName().equals(s)) return a;
                }
                return null;
            }
        });
        membersPicker.getItems().addAll(artistService.getArtists());

    }

    private void askForPicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Choose a picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        try (FileInputStream inputStream = new FileInputStream(file)) {
            images.add(new Picture(inputStream.readAllBytes()));
            setImages(images);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setImages(List<Picture> pictures){
        artistPictures.getChildren().clear();
        for(Picture picture: pictures){
            ImageView img = new ImageView(new Image(picture.toStream()));
            img.setFitHeight(150);
            img.setFitWidth(150);
            artistPictures.getChildren().add(img);
        }
    }
    @FXML
    private void addArtist() {
        for (Picture picture: images){
            pictureService.add(picture);
        }
        if (artistTypeChoiceBox.getSelectionModel().getSelectedItem().equals("Single")) {
            Artist singleArtist = new Artist(artistNameInput.getText(), artistBiographyInput.getText(), images);
            save(singleArtist);
        } else {
            List<Artist> members = membersPicker.getCheckModel().getCheckedItems();
            GroupArtist groupArtist = new GroupArtist(artistNameInput.getText(), artistBiographyInput.getText(), images, members);
            save(groupArtist);
        }
    }

    private void save(Artist artist) {
        artistService.add(artist);
        artistNameInput.clear();
        artistBiographyInput.clear();
        artistPictures.getChildren().clear();
    }
    @FXML
    private void exit() {
        try {
            ViewDispatcher.getInstance().navigateTo(Pages.ARTISTS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
