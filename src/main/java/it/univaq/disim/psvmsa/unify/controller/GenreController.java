package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.GenreService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Genre;
import it.univaq.disim.psvmsa.unify.view.components.Add;
import it.univaq.disim.psvmsa.unify.view.components.SearchBar;
import it.univaq.disim.psvmsa.unify.view.components.ViewGenre;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GenreController implements Initializable, DataInitializable {

    private final GenreService genreService;
    @FXML
    private HBox searchBox;
    @FXML
    private HBox addBox;
    @FXML
    private VBox viewList;

    private SearchBar searchBar;
    private Add addGenre;
    private ViewGenre viewGenre;

    public GenreController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.genreService = factoryInstance.getGenreService();
    }

    public void initialize(URL location, ResourceBundle resources){
        searchBar = new SearchBar("Search by Genre");
        addGenre = new Add();

        searchBar.setOnSearch(text ->{
            showSearch(text);
        });



        searchBox.getChildren().add(searchBar);
        addBox.getChildren().add(addGenre);

        List<Genre> genres = genreService.getGenres();

        for(Genre genre : genres){
            viewGenre = new ViewGenre(genre);
            viewList.getChildren().add(viewGenre);
        }
    }

    public void showSearch(String text){

        List<Genre> genres = genreService.searchByName(text);

        viewList.getChildren().clear();

        for(Genre genre : genres){
            viewGenre = new ViewGenre(genre);
            viewList.getChildren().add(viewGenre);
        }
    }

}

// TODO add songs and albums

