package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.AlbumService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Album;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditAlbumController implements Initializable, DataInitializable {

    private final AlbumService albumService;

    @FXML
    private Button editButton;

    @FXML
    private Button removeButton;

    @FXML
    private TextField albumInput;

    @FXML
    private Label exceptionLabel;

    private Album album;


    public EditAlbumController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.albumService = factoryInstance.getAlbumService();
    }

    public void initializeData(Object data){
        this.album = (Album) data;
        albumInput.setText(album.getName());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exceptionLabel.setText("");

        this.editButton
                .disableProperty()
                .bind(albumInput
                        .textProperty()
                        .isEmpty());
    }

    @FXML
    public void updateAlbum(){
        try{
        album.setName(albumInput.getText());
        albumService.update(album);
        exit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void removeAlbum(){
        try{
            albumService.delete(album);
            exit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void exit() {
        try {
            ViewDispatcher.getInstance().navigateTo(Pages.ALBUMS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
