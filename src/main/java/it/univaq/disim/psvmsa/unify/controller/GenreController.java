package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.GenreService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.view.components.Add;
import it.univaq.disim.psvmsa.unify.view.components.SearchBar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GenreController implements Initializable, DataInitializable {

    private final GenreService genreService;
    @FXML
    private HBox searchBox;
    @FXML
    private HBox addBox;
    private SearchBar searchBar;
    private Add addGenre;

    public GenreController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.genreService = factoryInstance.getGenreService();
    }

    public void initialize(URL location, ResourceBundle resources){
        searchBar = new SearchBar();
        addGenre = new Add();

        searchBox.getChildren().add(searchBar);
        addBox.getChildren().add(addGenre);
    }
}


