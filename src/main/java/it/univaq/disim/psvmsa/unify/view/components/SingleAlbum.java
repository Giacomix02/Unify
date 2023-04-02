package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Album;
import it.univaq.disim.psvmsa.unify.model.Picture;
import it.univaq.disim.psvmsa.unify.model.Song;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.util.List;
import java.util.function.Consumer;

public class SingleAlbum extends HBox {
    private Label label;
    private ImageView image;
    private Album album;

    private HBox box;
    private ViewDispatcher viewDispatcher;

    private Picture picture;

    SongService songService;

    public SingleAlbum(Album album) {
        super();
        this.album = album;
        init(album);
    }

    public void init(Album album){
        viewDispatcher = ViewDispatcher.getInstance();

        try{
            HBox root = FXMLLoader.load(getClass().getResource("/ui/components/singleAlbum.fxml"));
            HBox.setHgrow(root, Priority.ALWAYS);
            getChildren().add(root);

            label = (Label) root.lookup("#label");
            image = (ImageView) root.lookup("#image");

            List<Song> songs = album.getSongs();

            this.label.setText(album.getName());


            songService = UnifyServiceFactory.getInstance().getSongService();

            if (songs.size() > 0) {
                picture = songs.get(0).getPicture();
            } else {
                picture = new Picture(new FileInputStream("src/main/resources/ui/images/album-placeholder.png"));
            }
            setImage(picture);


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setOnAlbumClick(Consumer<Album> consumer){
        this.image.setOnMouseClicked(mouseEvent -> {
            consumer.accept(this.album);
        });
        this.label.setOnMouseClicked(mouseEvent -> {
            consumer.accept(this.album);
        });
    }

    public void setImage(Picture picture) {
        Image i = new Image(picture.toStream());
        Rectangle rectangle = new Rectangle(0, 0, 30, 30);
        rectangle.setArcWidth(14);   // Corner radius
        rectangle.setArcHeight(14);
        ImagePattern pattern = new ImagePattern(i);
        rectangle.setFill(pattern);
        image.setClip(rectangle);
        image.setImage(i);
    }
}
