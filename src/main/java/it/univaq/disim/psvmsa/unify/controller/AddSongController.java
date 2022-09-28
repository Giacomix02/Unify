package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;


public class AddSongController implements Initializable, DataInitializable{

    private SongService songService;

    @FXML
    private Button exit;


    public AddSongController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.songService = factoryInstance.getSongService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void exit(){
        try {
            ViewDispatcher.getInstance().navigateTo(Pages.SONGS);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void saveSong(){
        //TODO
    }
}
