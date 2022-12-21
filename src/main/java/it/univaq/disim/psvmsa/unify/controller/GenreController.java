package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.GenreService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.business.UserService;
import it.univaq.disim.psvmsa.unify.model.Admin;
import it.univaq.disim.psvmsa.unify.model.Genre;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.components.AddLinkButton;
import it.univaq.disim.psvmsa.unify.view.components.SearchBar;
import it.univaq.disim.psvmsa.unify.view.components.ViewGenre;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GenreController implements Initializable, DataInitializable<User> {

    private GenreService genreService;

    @FXML
    private HBox searchBox;
    @FXML
    private HBox addBox;
    @FXML
    private VBox viewList;

    private User user;
    private SearchBar searchBar;
    private ViewGenre viewGenre;

    public GenreController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.genreService = factoryInstance.getGenreService();
    }

    public void initializeData(User data) {
       user = data;
        if(user instanceof Admin){
            addBox.getChildren().add(new AddLinkButton(Pages.ADDGENRE));
        }
        List<Genre> genres = genreService.getGenres();

        for(Genre genre : genres){
            viewGenre = new ViewGenre(genre,user);
            viewList.getChildren().add(viewGenre);
        }

    }

    public void initialize(URL location, ResourceBundle resources){
        searchBar = new SearchBar("Search by Genre");

        searchBar.setOnSearch(text ->{
            showSearch(text);
        });

        searchBox.getChildren().add(searchBar);




    }

    public void showSearch(String text){

        List<Genre> genres = genreService.searchByName(text);

        viewList.getChildren().clear();

        for(Genre genre : genres){
            viewGenre = new ViewGenre(genre,user);
            viewList.getChildren().add(viewGenre);
        }
    }

}

