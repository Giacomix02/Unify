package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.AlbumService;
import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.*;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcherException;
import it.univaq.disim.psvmsa.unify.view.components.MusicPlayer;
import it.univaq.disim.psvmsa.unify.view.components.SingleAlbum;
import it.univaq.disim.psvmsa.unify.view.components.SongsListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ArtistDetailsController implements Initializable, DataInitializable<UserWithData<Artist>> {

    @FXML
    private FlowPane artistPictures;

    @FXML
    private FlowPane artistAlbums;

    @FXML
    private VBox artistSongs;

    @FXML
    private Label artistName;

    @FXML
    private Label artistBiography;

    @FXML
    private Label membersLabel;

    @FXML
    private Label albumLabel;

    @FXML
    private Label songLabel;

    @FXML
    private ImageView artistImagePreview;

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


    public void initializeData(UserWithData<Artist> data) {
        this.existingArtist = data.getData();
        this.user = data.getUser();
        artistName.setText(existingArtist.getName());
        artistBiography.setText(existingArtist.getBiography());
        images = (existingArtist.getPictures());
        songLabel.visibleProperty().set(false);
        albumLabel.visibleProperty().set(false);
        setImages(images);
        loadSongsAndAlbums();
        if (existingArtist instanceof GroupArtist) {
            StringBuilder members = new StringBuilder("Members: ");

            for (Artist artist : ((GroupArtist) existingArtist).getArtists()) {
                members.append(artist.getName()).append(", ");
            }

            if (members.toString().equals("Members: ")) {
                membersLabel.visibleProperty().set(false);
            } else {
                membersLabel.setText(members.substring(0, members.length() - 2)); //remove last comma
            }

        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    private void setImages(List<Picture> pictures) {
        artistPictures.getChildren().clear();

        if(pictures.size() > 0) {
            artistImagePreview.setImage(new Image(pictures.get(0).toStream()));;
        } else {
            try {
                artistImagePreview.setImage(new Image(new Picture(new FileInputStream("src/main/resources/ui/images/artist-placeholder.png")).toStream()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 1; i < pictures.size(); i++) {
            Picture picture = pictures.get(i);
            if (picture != null) {
                ImageView imageView = new ImageView(new Image(picture.toStream()));
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                artistPictures.getChildren().add(imageView);
            }
        }
    }

    private void loadSongsAndAlbums() {
        artistSongs.getChildren().clear();
        artistAlbums.getChildren().clear();
        try{
            List<Song> songs = songService.searchByArtist(existingArtist);
            List<Album> albums = albumService.searchAlbumsByArtist(existingArtist);
            songLabel.visibleProperty().set(songs.size() > 0);
            albumLabel.visibleProperty().set(albums.size() > 0);
            SongsListView songsListView = new SongsListView(songs, user, false);
            artistSongs.getChildren().add(songsListView);
            songsListView.setOnDetailsClicked(song -> {
                try {
                    ViewDispatcher.getInstance().navigateTo(Pages.SONGDETAILS, new UserWithData<>(user, song));
                } catch (ViewDispatcherException e) {
                    throw new RuntimeException(e);
                }
            });
            songsListView.setOnPlayClicked(song -> {
                MusicPlayer.getInstance().playOne(song);
            });
            for (Album album : albums) {
                SingleAlbum singleAlbum = new SingleAlbum(album);
                singleAlbum.setOnAlbumClick(a -> {
                    try {
                        if (a.getSongs().size() == 0) {
                            ViewDispatcher.getInstance().navigateTo(Pages.EDITALBUM, new UserWithData<>(user, a));
                        } else {
                            ViewDispatcher.getInstance().navigateTo(Pages.ALBUMDETAILS,new UserWithData<>(user, a));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
                artistAlbums.getChildren().add(singleAlbum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}