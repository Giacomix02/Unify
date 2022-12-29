package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.AlbumService;
import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.*;
import it.univaq.disim.psvmsa.unify.view.components.SingleAlbum;
import it.univaq.disim.psvmsa.unify.view.components.SongRow;
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

        artistNameInput.setText(existingArtist.getName());
        artistBiographyInput.setText(existingArtist.getBiography());
        images = (existingArtist.getPictures());
        songLabel.visibleProperty().set(false);
        albumLabel.visibleProperty().set(false);
        setImages(images);
        setSongsAndAlbums();

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


    private void setImages(List<Picture> pictures) {
        artistPictures.getChildren().clear();
        for (Picture picture : pictures) {
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

    private void setSongsAndAlbums() {
        artistSongs.getChildren().clear();
        artistAlbums.getChildren().clear();
        try{
            List<Song> songs = songService.searchByArtist(existingArtist);
            List<Album> albums = albumService.searchAlbumsByArtist(existingArtist);
            for (Song song : songs) {
                songLabel.visibleProperty().set(true);
                artistSongs.getChildren().add(new SongRow(new UserWithData<>(user, song), user instanceof Admin));
            }
            for (Album album : albums) {
                albumLabel.visibleProperty().set(true);
                artistAlbums.getChildren().add(new SingleAlbum(new UserWithData<>(user, album)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}