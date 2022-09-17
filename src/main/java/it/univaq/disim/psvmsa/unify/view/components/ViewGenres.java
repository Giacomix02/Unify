package it.univaq.disim.psvmsa.unify.view.components;
import it.univaq.disim.psvmsa.unify.business.GenreService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Genre;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ViewGenres extends VBox {

    private Label label;

    private Button removeButton;
    private Genre genre;

    private GenreService genreService;

    public ViewGenres(Genre genre) {
        super();
        this.genre = genre;
        init();
    }

    public void init(){
        try {
            VBox root = FXMLLoader.load(getClass().getResource("/ui/components/viewGenres.fxml"));
            VBox.setVgrow(root, Priority.ALWAYS);
            getChildren().add(root);

            label = (Label) root.lookup("#label");
            removeButton = (Button) root.lookup("#removeButton");

            label.setText(genre.getName());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        removeGenre();
    }

    @FXML
    public void removeGenre(){

        this.removeButton.setOnAction(event -> {
            try {
                UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
                this.genreService = factoryInstance.getGenreService();

                genreService.delete(genre);

                ViewDispatcher.getInstance().navigateTo(Pages.GENRES);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        });
    }

}

