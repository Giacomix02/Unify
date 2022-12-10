package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Album;
import it.univaq.disim.psvmsa.unify.model.Picture;
import it.univaq.disim.psvmsa.unify.model.Song;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class SingleAlbum extends HBox {
    private Label label;
    private ImageView image;

    private Picture picture;

    SongService songService;

    public SingleAlbum(Album album) {
        super();
        init(album);
    }

    public void init(Album album){
        try{
            HBox root = FXMLLoader.load(getClass().getResource("/ui/components/singleAlbum.fxml"));
            HBox.setHgrow(root, Priority.ALWAYS);
            getChildren().add(root);

            label = (Label) root.lookup("#label");
            image = (ImageView) root.lookup("#image");

            this.label.setText(album.getName());


            songService = UnifyServiceFactory.getInstance().getSongService();
            List<Song> songs = songService.getAllSongs();
            for (Song song : songs) {
                if (song.getAlbum().equals(album)) {
                    picture = song.getPicture();
                }
            }


            if(picture != null){
                Image i = new Image(picture.toStream());

                Rectangle rectangle = new Rectangle(0, 0, 100, 100);
                rectangle.setArcWidth(14);   // Corner radius
                rectangle.setArcHeight(14);
                ImagePattern pattern = new ImagePattern(i);
                rectangle.setFill(pattern);
                image.setClip(rectangle);

                image.setImage(i);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
