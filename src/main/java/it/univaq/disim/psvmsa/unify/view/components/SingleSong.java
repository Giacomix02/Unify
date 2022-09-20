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
                image.setImage(new Image(picture.getImageStream()));
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
