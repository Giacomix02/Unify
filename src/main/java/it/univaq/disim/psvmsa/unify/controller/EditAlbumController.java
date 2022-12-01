package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.business.AlbumService;
import it.univaq.disim.psvmsa.unify.business.UnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.model.Album;
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
