package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.GenreService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.view.components.SearchBar;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class GenreController implements Initializable, DataInitializable {

    private final GenreService genreService;
    @FXML
    private HBox searchBox;
    private SearchBar searchBar;

    public GenreController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.genreService = factoryInstance.getGenreService();
    }

    public void initialize(URL location, ResourceBundle resources){
        searchBar = new SearchBar();

        searchBox.getChildren().add(searchBar);
    }
}


