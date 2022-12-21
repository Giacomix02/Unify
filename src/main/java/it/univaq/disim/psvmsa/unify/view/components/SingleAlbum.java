package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.controller.UserWithData;
import it.univaq.disim.psvmsa.unify.model.Album;
import it.univaq.disim.psvmsa.unify.model.Picture;
import it.univaq.disim.psvmsa.unify.model.Song;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class SingleAlbum extends HBox {
    private Label label;
    private ImageView image;
    private User user;
    private Album album;
    private ViewDispatcher viewDispatcher;

    private Picture picture;

    SongService songService;

    public SingleAlbum(UserWithData data) {
        super();
        user = data.getUser();
        album = (Album) data.getData();
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

            this.image.setOnMouseClicked(mouseEvent -> {
                try {
                    List<Song> songs = songService.getAllSongs();
                    ArrayList<Song> associatedSongs = new ArrayList<>();

                    for(Song song : songs){
                        if(song.getAlbum().equals(album)){
                            associatedSongs.add(song);
                        }
                    }

                    UserWithData userWithData = new UserWithData(user, associatedSongs);
                    ViewDispatcher.getInstance().navigateTo(Pages.SONGS,userWithData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            });

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

                Rectangle rectangle = new Rectangle(0, 0, 30, 30);
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
