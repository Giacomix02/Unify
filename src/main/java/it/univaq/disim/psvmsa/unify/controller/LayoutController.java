package it.univaq.disim.psvmsa.unify.controller;
import it.univaq.disim.psvmsa.unify.view.Pages;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcherException;
import it.univaq.disim.psvmsa.unify.view.components.MenuLink;
import it.univaq.disim.psvmsa.unify.view.components.MusicBar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LayoutController implements Initializable {
    private Pages currentPage = Pages.HOME;

    private MenuLink MENU_LINKS[] = {
        new MenuLink("Artists", Pages.ARTISTS, "/ui/images/icons/person.png"),
        new MenuLink("Albums", Pages.ALBUMS, "/ui/images/icons/compact-disc.png"),
        new MenuLink("Songs", Pages.SONGS, "/ui/images/icons/note.png") ,
        new MenuLink("Genres", Pages.GENRES, "/ui/images/icons/tag.png")
    };

    private MusicBar musicBar;

    @FXML
    private BorderPane borderPane;
    @FXML
    private VBox navigationMenu;

    @FXML
    private void gotoHome(){
        setCurrentPage(Pages.HOME);
    }
    public void initialize(URL location, ResourceBundle resources) {
        createMenu();
        musicBar = new MusicBar();
        borderPane.setBottom(musicBar);
    }

    private void createMenu(){
        navigationMenu.getChildren().clear();
        for(MenuLink menuLink : MENU_LINKS){
            navigationMenu.getChildren().add(menuLink);
            menuLink.setOnMouseClicked(event -> {
                setCurrentPage(menuLink.getHref());
            });
        }
    }

    private void setCurrentPage(Pages page){
        if(page == currentPage) return; //might have to remove this later if we want to reset the page
        currentPage = page;
        for(MenuLink menuLink : MENU_LINKS){
            menuLink.setActive(menuLink.getHref() == currentPage);
        }
        try{
            ViewDispatcher.getInstance().navigateTo(page);
        }catch(ViewDispatcherException e){
            System.out.println(e.getMessage());
        }
    }
}
