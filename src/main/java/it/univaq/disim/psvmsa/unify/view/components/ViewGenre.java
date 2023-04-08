package it.univaq.disim.psvmsa.unify.view.components;
import it.univaq.disim.psvmsa.unify.business.AlbumService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.controller.UserWithData;
import it.univaq.disim.psvmsa.unify.model.*;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import java.util.List;

public class ViewGenre extends VBox {

    private Label label;

    private boolean editable;

    private User user;

    private Genre genre;

    private Button viewGenreSongsButton;

    private HBox albumBox;

    private AlbumService albumService;


    public ViewGenre(Genre genre, User user) {
        super();
        this.genre = genre;
        this.user = user;
        this.editable = user instanceof Admin;
        init();
    }

    public void init(){
        try {
            VBox root = FXMLLoader.load(getClass().getResource("/ui/components/viewGenre.fxml"));
            VBox.setVgrow(root, Priority.ALWAYS);
            getChildren().add(root);
            label = (Label) root.lookup("#label");
            albumBox = (HBox) root.lookup("#albums");
            viewGenreSongsButton = (Button) root.lookup("#viewGenreSongsButton");
            viewGenreSongsButton.setOnAction(e -> {
                try {
                    List<Song> songs = UnifyServiceFactory.getInstance().getSongService().searchByGenre(genre);
                    ViewDispatcher.getInstance().navigateTo(Pages.SONGSLIST, new UserWithData<>(user, songs));
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            });
            label.setText(genre.getName());
            albumService = UnifyServiceFactory.getInstance().getAlbumService();
            List<Album> albums = albumService.searchAlbumsByGenre(genre);
            for (Album album : albums) {
                SingleAlbum singleAlbum = new SingleAlbum(album);
                singleAlbum.setOnAlbumClick(a -> {
                    try {
                        ViewDispatcher.getInstance().navigateTo(Pages.ALBUMDETAILS,new UserWithData<>(user, a));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
                albumBox.getChildren().add(singleAlbum);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}