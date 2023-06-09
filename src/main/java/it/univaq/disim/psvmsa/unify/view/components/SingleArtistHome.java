package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.controller.UserWithData;
import it.univaq.disim.psvmsa.unify.model.Artist;
import it.univaq.disim.psvmsa.unify.model.Picture;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;

public class SingleArtistHome extends VBox {
    private Label label;
    private Button button;
    private Picture picture;

    Artist artist;
    User user;

    public SingleArtistHome(Artist artist, User user){
        this.artist = artist;
        this.user = user;
        init();
    }

    public void init(){
        try {
            VBox root = FXMLLoader.load(getClass().getResource("/ui/components/SingleArtistHome.fxml"));
            VBox.setVgrow(root, Priority.ALWAYS);
            getChildren().add(root);

            label = (Label) root.lookup("#label");
            button = (Button) root.lookup("#artist");

            this.label.setText(artist.getName());

            if(artist.getPictures().size() == 0) {
                picture = new Picture(new FileInputStream("src/main/resources/ui/images/artist-placeholder.png"));
            } else {
                picture = artist.getPictures().get(0);
            }
            setPicture(picture);

        }catch (Exception e) {
            e.printStackTrace();
        }

        this.button.setOnMouseClicked(event -> {
            try {
                UserWithData<Artist> data = new UserWithData<>(user, artist);
                ViewDispatcher.getInstance().navigateTo(Pages.ARTISTDETAILS, data);
            }catch (Exception e){
                e.printStackTrace();
            }

        });

    }

    public void setPicture(Picture picture) {
        Image image = new Image(picture.toStream());
        Rectangle rectangle = new Rectangle(0, 0, 110, 110);
        rectangle.setArcWidth(14);
        rectangle.setArcHeight(14);
        ImagePattern pattern = new ImagePattern(image);
        rectangle.setFill(pattern);
        button.setGraphic(rectangle);
    }
}
