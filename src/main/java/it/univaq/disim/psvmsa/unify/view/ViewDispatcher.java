package it.univaq.disim.psvmsa.unify.view;

import it.univaq.disim.psvmsa.unify.model.User;
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

    public void loggedIn(User user) throws ViewDispatcherException {
        validateStage();
        View<User> layoutPage = loadView("/ui/views/layout.fxml");
        Parent layoutView = layoutPage.getView();
        layoutPage.getController().initializeData(user);
        layout = (ScrollPane) layoutView.lookup("#layoutRoot");
        stage.setScene(new Scene(layoutView));
        navigateTo(Pages.HOME);
    }

    public void showLogin() throws ViewDispatcherException {
        View loginPage = loadView("/ui/views/login.fxml");
        stage.setScene(new Scene(loginPage.getView()));
    }
    public <T> void navigateTo(Pages page, T data) throws ViewDispatcherException {
        validateStage();
        View<T> viewToLoad = loadView("/ui/views/" + page.toString() + ".fxml");
        viewToLoad.getController().initializeData(data);
        layout.setContent(viewToLoad.getView());
    }
    public void navigateTo(Pages page) throws ViewDispatcherException {
        validateStage();
        View viewToLoad = loadView("/ui/views/" + page.toString() + ".fxml");
        layout.setContent(viewToLoad.getView());
    }
    private void validateStage() throws ViewDispatcherException {
        if(stage == null){
            throw new ViewDispatcherException("Stage not set");
        }
    }

    private <T> View<T> loadView(String path) throws ViewDispatcherException {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent view = loader.load();
            return new View<>(view,loader.getController());
        }catch(Exception e){
            throw new ViewDispatcherException(e.getMessage());
        }
    }
}
