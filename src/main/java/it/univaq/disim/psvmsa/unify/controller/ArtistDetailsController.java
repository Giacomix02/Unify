package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.model.Artist;
import it.univaq.disim.psvmsa.unify.model.GroupArtist;
import it.univaq.disim.psvmsa.unify.model.Picture;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ArtistDetailsController implements Initializable, DataInitializable<Artist> {

    public HBox membersPickerBox;

    @FXML
    private HBox artistPictures;

    @FXML
    private Label artistType;

    @FXML
    private TextField artistNameInput;

    @FXML
    private TextArea artistBiographyInput;

    @FXML
    private ChoiceBox<String> artistMembersChoiceBox;

    @FXML
    private Label membersLabel;

    private List<Picture> images = new ArrayList<>();


    public ArtistDetailsController() {
    }

    public void initializeData(Artist data) {

        artistNameInput.setText(data.getName());
        artistBiographyInput.setText(data.getBiography());
        images = (data.getPictures());
        getImages(images);

        artistNameInput.setEditable(false);
        artistBiographyInput.setEditable(false);

        if (data instanceof GroupArtist) {
            artistType.setText("Group");

            for (Artist artist : ((GroupArtist) data).getArtists()) {
                artistMembersChoiceBox.getItems().add(artist.getName());
            }

        } else {
            artistType.setText("Single");
            membersLabel.setDisable(true);
            artistMembersChoiceBox.setDisable(true);
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    private void getImages(List<Picture> pictures) {
        artistPictures.getChildren().clear();
        for (Picture picture : pictures) {
            ImageView img = new ImageView(new Image(picture.toStream()));
            img.setFitHeight(150);
            img.setFitWidth(150);
            artistPictures.getChildren().add(img);
        }
    }

}
