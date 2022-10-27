package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.business.AlbumService;
import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.model.Album;
import it.univaq.disim.psvmsa.unify.model.Picture;
import it.univaq.disim.psvmsa.unify.model.Song;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ViewAlbum extends VBox {

    @FXML
    private ImageView albumImage;

    @FXML
    private Label label;

    @FXML
    private Button removeButton;

    private Song song;

    private SongService songService;
    private Album album;

    private AlbumService albumService;

    public ViewAlbum(Album album){
        super();
        this.album = album;
        init();
    }

    public void init() {
        try{
            VBox root = FXMLLoader.load(getClass().getResource("/ui/components/viewAlbum.fxml"));
            VBox.setVgrow(root, Priority.ALWAYS);
            getChildren().add(root);

            albumImage = (ImageView) root.lookup("#albumImage");
            label = (Label) root.lookup("#label");
            removeButton = (Button) root.lookup("#removeButton");

            // Picture picture =
            // albumImage.setImage(albumImage);
            label.setText(album.getName());

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    public void removeAlbum(){

    }
}
