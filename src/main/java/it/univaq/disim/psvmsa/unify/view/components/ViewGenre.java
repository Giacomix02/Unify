package it.univaq.disim.psvmsa.unify.view.components;
import it.univaq.disim.psvmsa.unify.business.GenreService;
import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Album;
import it.univaq.disim.psvmsa.unify.model.Genre;
import it.univaq.disim.psvmsa.unify.model.Song;
import it.univaq.disim.psvmsa.unify.model.User;
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

import java.util.ArrayList;
import java.util.List;
public class ViewGenre extends VBox {

    private Label label;
    private boolean editable;
    private Button removeButton;
    private Genre genre;
    private HBox songsBox;
    private HBox albumBox;
    private GenreService genreService;
    private SongService songService;
    public ViewGenre(Genre genre, boolean editable) {
        super();
        this.genre = genre;
        this.editable=editable;
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
            ArrayList<Album> albums = new ArrayList<>();
            for(Song song : songs){
                if(song.getGenres().contains(genre)){
                    SingleSong singleSong = new SingleSong(song);
                    songsBox.getChildren().add(singleSong);

                    if(!albums.contains(song.getAlbum())){
                        albums.add(song.getAlbum());
                    }
                }
            }
            for(Album album : albums){
                SingleAlbum singleAlbum = new SingleAlbum(album);
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
                ViewDispatcher.getInstance().navigateTo(Pages.GENRES);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}