package it.univaq.disim.psvmsa.unify.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ViewNavigation {
    private Stage stage;
    private BorderPane layout;
    private static ViewNavigation instance = new ViewNavigation();

    static public ViewNavigation getInstance(){
        return instance;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void loggedIn() throws ViewNavigationException{
        validateStage();
        Parent LayoutPage = loadView("/ui/views/layout.fxml");
        layout = (BorderPane) LayoutPage.lookup("#layoutRoot");
        stage.setScene(new Scene(LayoutPage));
    }

    public void showLogin() throws ViewNavigationException{
        Parent LoginPage = loadView("/ui/views/login.fxml");
        stage.setScene(new Scene(LoginPage));
    }
    public void navigateTo(Pages page) throws ViewNavigationException{
        validateStage();
        Parent pageToLoad = loadView("/ui/views/" + page.toString() + ".fxml");
        layout.setCenter(pageToLoad);
    }

    private void validateStage() throws ViewNavigationException{
        if(stage == null){
            throw new ViewNavigationException("Stage not set");
        }
    }
    private Parent loadView(String path) throws ViewNavigationException{
        try{
            System.out.println("Loading view: " + path);
            return FXMLLoader.load(getClass().getResource(path));
        }catch(Exception e){
            throw new ViewNavigationException(e.getMessage());
        }
    }
}
