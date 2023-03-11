package it.univaq.disim.psvmsa.unify.controller;


import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.GenreService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Genre;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class AddGenreController implements Initializable, DataInitializable{

    @FXML
    private TextField genreInput;

    @FXML
    private Button saveButton;

    @FXML
    private Label exceptionLabel;

    private final GenreService genreService;

    public AddGenreController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.genreService = factoryInstance.getGenreService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        exceptionLabel.setText("");

        this.saveButton
                .disableProperty()
                .bind(genreInput
                        .textProperty()
                        .isEmpty()
                );
    }

    public void saveGenre(){
        Genre genre = new Genre(genreInput.getText());
        try {
            genreService.add(genre);
        }
        catch (BusinessException e){
            exceptionLabel.setText(e.getMessage());
        }
        genreInput.clear();
    }


    public void textFieldClick(){
        exceptionLabel.setText("");
    }
}