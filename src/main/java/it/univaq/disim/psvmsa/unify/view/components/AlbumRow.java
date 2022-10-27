package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Album;
import it.univaq.disim.psvmsa.unify.model.Song;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.List;

public class AlbumRow extends HBox {

    private Label albumName;

    private Button removeButton;

    private ImageView albumImage;

    private Album album;

    private Song song;

    private SongService songService;


    public AlbumRow(Album album) {
        super();
        this.album = album;
        init();
    }

    public void init(){
        try {
            HBox root = FXMLLoader.load(getClass().getResource("/ui/components/albumRow.fxml"));
            HBox.setHgrow(root, Priority.ALWAYS);
            getChildren().add(root);

            albumName = (Label) root.lookup("#albumName");
            removeButton = (Button) root.lookup("#removeButton");
            albumImage = (ImageView) root.lookup("#albumImage");

            albumName.setText(album.getName());

            List<Song> songs = songService.getAllSongs();
            for(Song song : songs){
                if(song.getAlbum().equals(album)){
                    this.song = song;
                    return;
                }
            }

            Image image = new Image(song.getPicture().toStream());
            albumImage.setImage(image);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        removeAlbum();
    }

    @FXML
    public void removeAlbum(){
        removeButton.setOnAction(event -> {
            //TODO removeAlbum()
        });
    }
}
