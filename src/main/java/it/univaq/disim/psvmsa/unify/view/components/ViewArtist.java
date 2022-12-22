package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.controller.UserWithData;
import it.univaq.disim.psvmsa.unify.model.Admin;
import it.univaq.disim.psvmsa.unify.model.Artist;
import it.univaq.disim.psvmsa.unify.model.Picture;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


public class ViewArtist extends HBox {

    private ImageView artistImage;

    private Label label;

    private Artist artist;

    private User user;

    private Button actionButton;

    private boolean editable;


    public ViewArtist(UserWithData data, boolean editable) {
        super();
        this.editable = editable;
        this.artist = (Artist) data.getData();
        this.user = data.getUser();
        init();
    }

    public void init() {
        try{
            HBox root = FXMLLoader.load(getClass().getResource("/ui/components/viewArtist.fxml"));
            HBox.setHgrow(root, Priority.ALWAYS);
            getChildren().add(root);
            artistImage = (ImageView) root.lookup("#artistImage");
            label = (Label) root.lookup("#artistName");
            actionButton = (Button) root.lookup("#actionButton");
            label.setText(artist.getName());
            if(artist.getPictures().size() > 0){
                Picture pfp = artist.getPictures().get(0);
                Image image = new Image(pfp.toStream());
                Rectangle rectangle = new Rectangle(0,0,50,50);
                rectangle.setArcHeight(14);
                rectangle.setArcWidth(14);
                ImagePattern pattern = new ImagePattern(image);
                rectangle.setFill(pattern);
                artistImage.setClip(rectangle);
                artistImage.setImage(image);
                artistImage.setOnMouseClicked(mouseEvent -> {
                    try{
                        UserWithData data = new UserWithData<>(user, artist);
                        ViewDispatcher.getInstance().navigateTo(Pages.ARTISTDETAILS, data);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                });
            }

            actionButton.visibleProperty().set(editable);
            actionButton.setOnAction(actionEvent -> {
                try{
                    UserWithData data = new UserWithData<>(user, artist);
                        ViewDispatcher.getInstance().navigateTo(Pages.EDITARTIST, data);
                } catch (Exception e){
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

