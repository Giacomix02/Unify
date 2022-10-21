package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.AlbumService;
import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Album;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddAlbumController implements Initializable, DataInitializable {

    private final AlbumService albumService;

    @FXML
    private Button saveButton;

    @FXML
    private Button exit;

    @FXML
    private TextField albumInput;

    @FXML
    private Label exceptionLabel;

    public AddAlbumController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.albumService = factoryInstance.getAlbumService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){

        exceptionLabel.setText("");

        this.saveButton
                .disableProperty()
                .bind(albumInput
                        .textProperty()
                        .isEmpty()
                );
    }

    public void saveAlbum(){
        Album album = new Album(albumInput.getText());
        albumService.add(album);
        albumInput.clear();
    }

    public void exit() {
        try{
            ViewDispatcher.getInstance().navigateTo(Pages.ALBUMS);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
