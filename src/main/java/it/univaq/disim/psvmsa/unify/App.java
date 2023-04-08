package it.univaq.disim.psvmsa.unify;

import it.univaq.disim.psvmsa.unify.model.Admin;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class App extends Application {
	private Stage stage;
	private static final String UNIFY_ICON = "/ui/images/logo.png";
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		ViewDispatcher viewDispatcher = ViewDispatcher.getInstance();
		viewDispatcher.setStage(stage);
		viewDispatcher.showLogin();
		boolean autoLogin = false;
		if(autoLogin){
			//viewDispatcher.showLogin();
			Admin admin = new Admin("admin", "admin", 2);
			viewDispatcher.loggedIn(admin);
		}
		stage.getIcons().add(new Image(getClass().getResource(UNIFY_ICON).toString()));
		stage.show();
	}
}
