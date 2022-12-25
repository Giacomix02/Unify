package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.business.AlbumService;
import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.controller.UserWithData;
import it.univaq.disim.psvmsa.unify.model.Album;
import it.univaq.disim.psvmsa.unify.model.Picture;
import it.univaq.disim.psvmsa.unify.model.Song;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class ViewAlbum extends HBox {

    @FXML
    private ImageView albumImage;

    @FXML
    private Label label;

    @FXML
    private Button editButton;

    @FXML
    private Button playButton;

    private SongService songService;

    private boolean editable;

    private Album album;

    private User user;


    public ViewAlbum(UserWithData data, boolean editable){
        super();
        this.editable = editable;
        this.album = (Album) data.getData();
        this.user = data.getUser();
        init();
    }

    public void init() {

        songService = UnifyServiceFactory.getInstance().getSongService();

        try{
            HBox root = FXMLLoader.load(getClass().getResource("/ui/components/viewAlbum.fxml"));
            HBox.setHgrow(root, Priority.ALWAYS);
            getChildren().add(root);

            albumImage = (ImageView) root.lookup("#albumImage");
            label = (Label) root.lookup("#albumName");
            editButton = (Button) root.lookup("#editButton");

            editButton.visibleProperty().set(editable);
            this.editButton.setOnAction(event -> {
                try{
                    UserWithData data = new UserWithData(user, album);
                    ViewDispatcher.getInstance().navigateTo(Pages.EDITALBUM, data);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            });

            this.albumImage.setOnMouseClicked(event -> {
                try {
                    List<Song> songs = songService.getAllSongs();
                    ArrayList<Song> albumSongs = new ArrayList<>();
                    for(Song song : songs){
                        if(song.getAlbum().getId().equals(album.getId())){
                            albumSongs.add(song);
                        }
                    }

                    UserWithData userWithData = new UserWithData(user, albumSongs);

                    ViewDispatcher.getInstance().navigateTo(Pages.SONGS,userWithData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            label.setText(album.getName());
            Picture pic = getAlbumPicture();
            if(pic == null) return;
            Image image = new Image(pic.toStream());
            Rectangle rectangle = new Rectangle(0, 0, 50, 50);
            rectangle.setArcWidth(14);   // Corner radius
            rectangle.setArcHeight(14);
            ImagePattern pattern = new ImagePattern(image);
            rectangle.setFill(pattern);
            albumImage.setClip(rectangle);
            albumImage.setImage(image);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


    public Picture getAlbumPicture() {
        try {
            songService = UnifyServiceFactory.getInstance().getSongService();
            List<Song> songs = songService.getAllSongs();
            for (Song song : songs) {
                if (song.getAlbum().equals(album)) {
                    return song.getPicture();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void playAlbum() {

    }
}
