package it.univaq.disim.psvmsa.unify.controller;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.View;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcherException;
import it.univaq.disim.psvmsa.unify.view.components.MenuLink;
import it.univaq.disim.psvmsa.unify.view.components.MusicBar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LayoutController implements Initializable, DataInitializable<User>  {
    private Pages currentPage = Pages.HOME;
    private User user;

    private static  final String IMAGE_PATH = "/ui/images/icons/";
    private static final String ARTIST_IMAGE = IMAGE_PATH+"person.png";
    private static final String ALBUM_IMAGE = IMAGE_PATH+"compact-disc.png";
    private static final String SONG_IMAGE = IMAGE_PATH+"note.png";
    private static final String GENRE_IMAGE = IMAGE_PATH+"tag.png";

    private MenuLink MENU_LINKS[] = {
        new MenuLink("Artists", Pages.ARTISTS, new Image(ARTIST_IMAGE)),
        new MenuLink("Albums", Pages.ALBUMS, new Image(ALBUM_IMAGE)),
        new MenuLink("Songs", Pages.SONGS, new Image(SONG_IMAGE)) ,
        new MenuLink("Genres", Pages.GENRES, new Image(GENRE_IMAGE))
    };


    private MusicBar musicBar;
    @FXML
    private BorderPane borderPane;
    @FXML
    private VBox navigationMenu;
    @FXML
    private Label usernameLabel;

    private View currentView;
    @FXML
    private ScrollPane layoutRoot;
    @FXML
    private void gotoHome(){
        setCurrentPage(Pages.HOME);
    }
    @FXML
    private void logout(){
        try{
            ViewDispatcher.getInstance().showLogin();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void initialize(URL location, ResourceBundle resources) {
        createMenu();
        musicBar = new MusicBar();
        borderPane.setBottom(musicBar);
    }

    public void initializeData(User user) {
        this.user = user;
        usernameLabel.setText(user.getUsername());
    }
    private void createMenu(){
        navigationMenu.getChildren().clear();
        for(MenuLink menuLink : MENU_LINKS){
            navigationMenu.getChildren().add(menuLink);
            menuLink.setOnMouseClicked(event -> setCurrentPage(menuLink.getPage()));
        }
    }

    public void setCurrentView(View view){
        this.currentView = view;
        layoutRoot.setContent(view.getView());
        System.out.println(view.getController() instanceof Searchable);
    }

    private void setCurrentPage(Pages page){
        currentPage = page;
        try{
            ViewDispatcher.getInstance().navigateTo(page);
        }catch(ViewDispatcherException e){
            System.out.println(e.getMessage());
        }
        for(MenuLink menuLink : MENU_LINKS){
            menuLink.setActive(menuLink.getPage() == currentPage);
        }
    }
}
