package it.univaq.disim.psvmsa.unify;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class App extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/root.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.getIcons().add(new Image(getClass().getResource("/ui/images/logo.png").toString()));
		stage.setScene(scene);
		stage.show();

	}

}
