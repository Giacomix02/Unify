package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.ArtistService;
import it.univaq.disim.psvmsa.unify.business.PictureService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.*;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

public class EditArtistController implements Initializable, DataInitializable<UserWithData> {
    private ArtistService artistService;

    private PictureService pictureService;

    @FXML
    private Button actionButton;

    private Artist existingArtist;

    private ViewDispatcher dispatcher;

    private User user;

    @FXML
    private Button addPicture;

    @FXML
    private Button delete;

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


    public EditArtistController() {
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.artistService = factoryInstance.getArtistService();
        this.pictureService = factoryInstance.getPictureService();
    }

    public void initializeData(UserWithData<Artist> data) {

        this.existingArtist = data.getData();
        this.user = data.getUser();

        artistNameInput.setText(existingArtist.getName());
        artistBiographyInput.setText(existingArtist.getBiography());
        delete.visibleProperty().set(true);
        boolean isGroup = existingArtist instanceof GroupArtist;
        artistTypeChoiceBox.setValue(isGroup ? "Group" : "Single");
        if(isGroup){
            membersPicker.getCheckModel().clearChecks();
            for (Artist member : ((GroupArtist) existingArtist).getArtists()) {
                membersPicker.getCheckModel().check(member);
            }
        }
        images = existingArtist.getPictures();
        setImages(existingArtist.getPictures());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        artistTypeChoiceBox.getItems().addAll(options);
        artistTypeChoiceBox.setValue(options.get(0));
        dispatcher = ViewDispatcher.getInstance();
        delete.visibleProperty().set(false);
        //TODO finish this
        this.actionButton
                .disableProperty()
                .bind(artistNameInput
                        .textProperty()
                        .isEmpty()
                );
        //show only if the artist type is Group
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
            images.add(new Picture(inputStream));
            setImages(images);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setImages(List<Picture> pictures){
        artistPictures.getChildren().clear();

        for(Picture picture: pictures){
            try{
                ImageView img = new ImageView(new Image(picture.toStream()));
                img.setFitHeight(150);
                img.setFitWidth(150);
                artistPictures.getChildren().add(img);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void onAction() {
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
        try{
            if (existingArtist == null) {
                artistService.add(artist);
                images.clear();

            } else {
                artist.setId(existingArtist.getId());
                artistService.update(artist);
                dispatcher.navigateTo(Pages.ARTISTS,this.user);
            }

            clearFields();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void delete() {
        try{
            artistService.delete(existingArtist);
            dispatcher.navigateTo(Pages.ARTISTS,this.user);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void clearFields(){
        artistNameInput.clear();
        artistBiographyInput.clear();
        artistPictures.getChildren().clear();
        images.clear();
        artistTypeChoiceBox.setValue(options.get(0));
        membersPicker.getCheckModel().clearChecks();
    }
}