package it.univaq.disim.psvmsa.unify.view.components;
import it.univaq.disim.psvmsa.unify.business.AlbumService;
import it.univaq.disim.psvmsa.unify.business.GenreService;
import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.controller.UserWithData;
import it.univaq.disim.psvmsa.unify.model.*;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ViewGenre extends VBox {

    private Label label;

    private boolean editable;

    private User user;

    private Button removeButton;

    private Genre genre;

    private HBox songsBox;

    private HBox albumBox;

    private GenreService genreService;

    private SongService songService;

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
            removeButton = (Button) root.lookup("#removeButton");

            songsBox = (HBox) root.lookup("#songs");
            albumBox = (HBox) root.lookup("#albums");

            removeButton.visibleProperty().set(editable);

            label.setText(genre.getName());
            songService = UnifyServiceFactory.getInstance().getSongService();
            List<Song> songs = songService.getAllSongs();
            for(Song song : songs){
                if(song.getGenres().contains(genre)){
                    SingleSong singleSong = new SingleSong(song);
                    singleSong.setOnSongClick(s -> {
                        try {
                            ViewDispatcher.getInstance().navigateTo(Pages.SONGDETAILS,new UserWithData<>(user,s));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                    songsBox.getChildren().add(singleSong);
                }
            }

            albumService = UnifyServiceFactory.getInstance().getAlbumService();
            ArrayList<Album> albums = new ArrayList<>(albumService.getAlbums());
            Set<Album> matchedAlbums = new HashSet<>();
            for(Album album : albums){
                for(Song song : album.getSongs()) {
                    if(song.getGenres().contains(genre)) {
                        matchedAlbums.add(album);
                    }
                }
            }
            for (Album album : matchedAlbums) {
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

        removeGenre();
    }

    @FXML
    public void removeGenre(){
        this.removeButton.setOnAction(event -> {
            try {
                UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
                this.genreService = factoryInstance.getGenreService();
                this.songService = factoryInstance.getSongService();

                genreService.delete(genre);
                List<Song> songs = songService.getAllSongs();
                for(Song song : songs){
                    if(song.getGenres().contains(genre)){
                        song.getGenres().remove(genre);
                        songService.update(song);
                    }
                }
                ViewDispatcher.getInstance().navigateTo(Pages.GENRES, user);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}