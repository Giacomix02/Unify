package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.business.PictureService;
import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.controller.UserWithData;
import it.univaq.disim.psvmsa.unify.model.Album;
import it.univaq.disim.psvmsa.unify.model.Picture;
import it.univaq.disim.psvmsa.unify.model.Song;
import it.univaq.disim.psvmsa.unify.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class ViewAlbum extends HBox {

    @FXML
    private Button albumDetails;

    @FXML
    private Label label;

    @FXML
    private Button editButton;

    private Button playButton;

    private SongService songService;

    private boolean editable;

    private Album album;


    private LinkedList<Song> queue = new LinkedList<>();



    public ViewAlbum(Album album, boolean editable){
        super();
        this.editable = editable;
        this.album = album;
        init();
    }

    public void init() {

        songService = UnifyServiceFactory.getInstance().getSongService();

        try{
            HBox root = FXMLLoader.load(getClass().getResource("/ui/components/viewAlbum.fxml"));
            HBox.setHgrow(root, Priority.ALWAYS);
            getChildren().add(root);

            albumDetails = (Button) root.lookup("#albumDetails");
            label = (Label) root.lookup("#albumName");
            editButton = (Button) root.lookup("#editButton");
            playButton = (Button) root.lookup("#playButton");

            editButton.visibleProperty().set(editable);

            label.setText(album.getName());
            Picture pic = getAlbumPicture();
            if(pic == null) return;
            Image image = new Image(pic.toStream());
            Rectangle rectangle = new Rectangle(0, 0, 50, 50);
            rectangle.setArcWidth(14);   // Corner radius
            rectangle.setArcHeight(14);
            ImagePattern pattern = new ImagePattern(image);
            rectangle.setFill(pattern);
            RoundedImage roundedImage = new RoundedImage(image,20,50);
            roundedImage.fitHeightProperty().set(50);
            roundedImage.fitWidthProperty().set(50);
            albumDetails.setGraphic(roundedImage);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setOnAlbumClicked(Consumer<Album> consumer) {
        this.albumDetails.setOnMouseClicked(event -> {
            consumer.accept(album);
        });
    }

    public void setOnEditClicked(Consumer<Album> consumer) {
        this.editButton.setOnAction(event -> {
            consumer.accept(album);
        });
    }

    public void setOnPlayClicked(Consumer<Album> consumer) {
        this.playButton.setOnAction(event -> {
            consumer.accept(album);
        });
    }

    public Picture getAlbumPicture() {

        try {
            songService = UnifyServiceFactory.getInstance().getSongService();
            List<Song> songs = album.getSongs();
            if(songs.size() == 0) {
                Picture picture = new Picture(new FileInputStream("src/main/resources/ui/images/album-placeholder.png"));
                return picture;
            }
            return songs.get(0).getPicture();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
