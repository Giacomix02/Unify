package it.univaq.disim.psvmsa.unify;

import it.univaq.disim.psvmsa.unify.business.UserService;
import it.univaq.disim.psvmsa.unify.business.impl.RAMUserServiceImpl;
import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.view.ViewDispatcher;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class App extends Application {

	private Stage stage;

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
		stage.getIcons().add(new Image(getClass().getResource("/ui/images/logo.png").toString()));
		stage.show();
		
	}

}
