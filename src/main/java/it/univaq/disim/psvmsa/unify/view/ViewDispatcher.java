package it.univaq.disim.psvmsa.unify.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;


public class ViewDispatcher {
    private Stage stage;
    private ScrollPane layout;
    private static ViewDispatcher instance = new ViewDispatcher();

    static public ViewDispatcher getInstance(){
        return instance;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void loggedIn() throws ViewDispatcherException {
        validateStage();
        Parent layoutPage = loadView("/ui/views/layout.fxml");
        layout = (ScrollPane) layoutPage.lookup("#layoutRoot");
        stage.setScene(new Scene(layoutPage));
    }

    public void showLogin() throws ViewDispatcherException {
        Parent LoginPage = loadView("/ui/views/login.fxml");
        stage.setScene(new Scene(LoginPage));
    }
    public void navigateTo(Pages page) throws ViewDispatcherException {
        validateStage();
        Parent pageToLoad = loadView("/ui/views/" + page.toString() + ".fxml");
        layout.setContent(pageToLoad);
    }

    private void validateStage() throws ViewDispatcherException {
        if(stage == null){
            throw new ViewDispatcherException("Stage not set");
        }
    }
    private Parent loadView(String path) throws ViewDispatcherException {
        try{
            System.out.println("Loading view: " + path);
            return FXMLLoader.load(getClass().getResource(path));
        }catch(Exception e){
            throw new ViewDispatcherException(e.getMessage());
        }
    }
}
