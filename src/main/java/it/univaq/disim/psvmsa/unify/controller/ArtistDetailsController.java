package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.AlbumService;
import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.*;
import it.univaq.disim.psvmsa.unify.view.components.SingleAlbum;
import it.univaq.disim.psvmsa.unify.view.components.SongRow;
import it.univaq.disim.psvmsa.unify.view.components.ViewAlbum;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ArtistDetailsController implements Initializable, DataInitializable<UserWithData> {

    public HBox membersPickerBox;

    @FXML
    private HBox artistPictures;

    @FXML
    private HBox artistAlbums;

    @FXML
    private VBox artistSongs;

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

    @FXML
    private Label songLabel;

    @FXML
    private Label albumLabel;

    private List<Picture> images = new ArrayList<>();

    private SongService songService;

    private AlbumService albumService;

    private Artist existingArtist;

    private User user;


    public ArtistDetailsController() {
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.songService = factoryInstance.getSongService();
        this.albumService = factoryInstance.getAlbumService();
    }


    public void initializeData(UserWithData data) {

        this.existingArtist = (Artist) data.getData();
        this.user = data.getUser();
        List<Album> albums = albumService.getAlbums();

        artistNameInput.setText(existingArtist.getName());
        artistBiographyInput.setText(existingArtist.getBiography());
        images = (existingArtist.getPictures());
        songLabel.visibleProperty().set(false);
        albumLabel.visibleProperty().set(false);
        getImages(images);
        getSongsAndAlbums(albums);

        artistNameInput.setEditable(false);
        artistBiographyInput.setEditable(false);

        if (existingArtist instanceof GroupArtist) {
            artistType.setText("Group");

            for (Artist artist : ((GroupArtist) existingArtist).getArtists()) {
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

    private void getSongsAndAlbums(List<Album> albums) {
        artistSongs.getChildren().clear();
        try{
            ArrayList<Album> iterated = new ArrayList<>();
            for (Song song : songService.getAllSongs()) {
                if (song.getArtist().getId().equals(existingArtist.getId())) {
                    songLabel.visibleProperty().set(true);
                    artistSongs.getChildren().add(new SongRow(new UserWithData(user, song), user instanceof Admin));
                }
                for (Album album : albums) {
                    if (song.getAlbum().getId().equals(album.getId())) {
                        albumLabel.visibleProperty().set(true);
                        if(!iterated.contains(album)) {
                            artistAlbums.getChildren().add(new SingleAlbum(new UserWithData(user, album)));
                        }
                        iterated.add(album);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}