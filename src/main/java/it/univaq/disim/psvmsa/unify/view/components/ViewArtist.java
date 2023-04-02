package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.controller.UserWithData;
import it.univaq.disim.psvmsa.unify.model.*;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;


public class ViewArtist extends HBox {

    private Button artistInfo;

    private Label label;

    private Artist artist;

    private User user;

    private Button actionButton;

    private boolean editable;


    public ViewArtist(UserWithData<Artist> data, boolean editable) {
        super();
        this.editable = editable;
        this.artist = data.getData();
        this.user = data.getUser();
        init();
    }

    public void init() {
        try{
            HBox root = FXMLLoader.load(getClass().getResource("/ui/components/viewArtist.fxml"));
            HBox.setHgrow(root, Priority.ALWAYS);
            getChildren().add(root);
            label = (Label) root.lookup("#artistName");
            actionButton = (Button) root.lookup("#actionButton");
            artistInfo = (Button) root.lookup("#artistInfo");

            label.setText(artist.getName());

            if(artist.getPictures().size() > 0) {
                Picture picture = artist.getPictures().get(0);
                setImage(picture);
            } else {
                Picture picture = new Picture(new FileInputStream("src/main/resources/ui/images/artist-placeholder.png"));
                setImage(picture);
            }

            artistInfo.setOnMouseClicked(mouseEvent -> {
                try{
                    UserWithData<Artist> data = new UserWithData<>(user, artist);
                    ViewDispatcher.getInstance().navigateTo(Pages.ARTISTDETAILS, data);
                } catch (Exception e){
                    e.printStackTrace();
                }
            });

            actionButton.visibleProperty().set(editable);
            actionButton.setOnAction(actionEvent -> {
                try{
                        UserWithData<Artist> data = new UserWithData<>(user, artist);
                        ViewDispatcher.getInstance().navigateTo(Pages.EDITARTIST, data);
                } catch (Exception e){
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setImage(Picture picture){
        Image image = new Image(picture.toStream());
        Rectangle rectangle = new Rectangle(0,0,50,50);
        rectangle.setArcHeight(14);
        rectangle.setArcWidth(14);
        ImagePattern pattern = new ImagePattern(image);
        rectangle.setFill(pattern);
        RoundedImage roundedImage = new RoundedImage(image,20,50);
        roundedImage.fitHeightProperty().set(50);
        roundedImage.fitWidthProperty().set(50);
        artistInfo.setGraphic(roundedImage);
    }
}

