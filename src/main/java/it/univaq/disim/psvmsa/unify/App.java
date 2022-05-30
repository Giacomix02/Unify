package it.univaq.disim.psvmsa.unify;

import it.univaq.disim.psvmsa.unify.business.impl.ram.RAMUserServiceImpl;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.File;

public class App extends Application {
	private Stage stage;
	private static final String UNIFY_ICON = File.separator+"ui"+ File.separator+"images"
												+File.separator+"logo.png";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		ViewDispatcher viewDispatcher = ViewDispatcher.getInstance();
		viewDispatcher.setStage(stage);
		viewDispatcher.showLogin();
		//User testUser = new User("admin", "admin");
		//viewDispatcher.loggedIn(testUser);
		RAMUserServiceImpl userService = new RAMUserServiceImpl();
		userService.addMock();
		stage.getIcons().add(new Image(getClass().getResource(UNIFY_ICON).toString()));
		stage.show();
		
	}

}
