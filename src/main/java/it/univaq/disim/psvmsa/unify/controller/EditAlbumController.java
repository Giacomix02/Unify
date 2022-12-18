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

public class EditAlbumController implements Initializable, DataInitializable<UserWithData> {

    private AlbumService albumService;
    private ViewDispatcher viewDispatcher;

    @FXML
    private Button editButton;

    @FXML
    private Button removeButton;

    @FXML
    private TextField albumInput;

    @FXML
    private Label exceptionLabel;

    @FXML
    private Label label;

    private Album album;
    private User user;


    public EditAlbumController(){
        UnifyServiceFactory factoryInstance = UnifyServiceFactory.getInstance();
        this.albumService = factoryInstance.getAlbumService();
    }

    public void initializeData(UserWithData data){
        this.album = (Album) data.getData();
        this.user = data.getUser();

        System.out.println(this.album);

        if(this.album != null){
            removeButton.visibleProperty().set(true);
            albumInput.setText(album.getName());
            label.setText("Edit album");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewDispatcher = ViewDispatcher.getInstance();
        exceptionLabel.setText("");
        removeButton.visibleProperty().set(false);
        label.setText("Add album");
        this.editButton
                .disableProperty()
                .bind(albumInput
                        .textProperty()
                        .isEmpty());
    }

    @FXML
    public void updateAlbum(){
        try{
            Album a = this.album;
            if(a == null){
               a = new Album(albumInput.getText());
               albumService.add(a);
               exceptionLabel.setText("Saved album");
               albumInput.clear();
            }else{
                a.setName(albumInput.getText());
                albumService.update(album);
                exceptionLabel.setText("Edited album");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void removeAlbum(){
        try{
            albumService.delete(album);
            exceptionLabel.setText("Removed album");
            viewDispatcher.navigateTo(Pages.ALBUMS, user);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
