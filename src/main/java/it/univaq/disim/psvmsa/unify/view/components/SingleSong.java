package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.model.Genre;
import it.univaq.disim.psvmsa.unify.model.Picture;
import it.univaq.disim.psvmsa.unify.model.Song;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class SingleSong extends VBox {

    private Label label;
    private ImageView image;
    private Song song;
    public SingleSong(Song song) {
        super();
        this.song = song;
        init();
    }

    public void init(){
        try{
            VBox root = FXMLLoader.load(getClass().getResource("/ui/components/SingleSong.fxml"));
            VBox.setVgrow(root, Priority.ALWAYS);
            getChildren().add(root);

            label = (Label) root.lookup("#label");
            image = (ImageView) root.lookup("#image");

            this.label.setText(song.getName());
            Picture picture = song.getPicture();
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

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //TODO form here song can be only played
}
