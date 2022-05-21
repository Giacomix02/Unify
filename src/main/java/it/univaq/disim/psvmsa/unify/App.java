package it.univaq.disim.psvmsa.unify;

import it.univaq.disim.psvmsa.unify.view.ViewNavigation;
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
		ViewNavigation viewNavigation = ViewNavigation.getInstance();
		viewNavigation.setStage(stage);
		viewNavigation.showLogin();
		stage.getIcons().add(new Image(getClass().getResource("/ui/images/logo.png").toString()));
		stage.show();
	}

}
