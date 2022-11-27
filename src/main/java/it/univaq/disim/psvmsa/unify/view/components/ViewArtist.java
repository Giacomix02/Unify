package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.business.ArtistService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Artist;
import it.univaq.disim.psvmsa.unify.model.Picture;
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

public class ViewArtist extends HBox {

    private ImageView artistImage;

    private Label label;

    private Artist artist;

    private ArtistService artistService;

    public ViewArtist(Artist artist) {
        super();
        this.artist = artist;
        init();
    }

    public void init() {
        try{
            HBox root = FXMLLoader.load(getClass().getResource("/ui/components/viewArtist.fxml"));
            HBox.setHgrow(root, Priority.ALWAYS);
            getChildren().add(root);

            artistImage = (ImageView) root.lookup("#artistImage");
            label = (Label) root.lookup("#artistName");

            label.setText(artist.getName());

            Image image = new Image(artist.getPicture().toStream());
            Rectangle rectangle = new Rectangle(0,0,50,50);
            rectangle.setArcHeight(14);
            rectangle.setArcWidth(14);
            ImagePattern pattern = new ImagePattern(image);
            rectangle.setFill(pattern);
            artistImage.setClip(rectangle);
            artistImage.setImage(image);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

