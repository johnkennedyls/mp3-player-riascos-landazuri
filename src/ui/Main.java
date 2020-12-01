package ui;


import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Manager;

public class Main extends Application {

	private GUI_MP3 guiMp3;
	private Manager manager;
	
	public Main() {
		manager = new Manager();
		guiMp3 = new GUI_MP3(manager);
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		launch(args);		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("welcome.fxml"));

		fxmlLoader.setController(guiMp3);
		
		Parent root = fxmlLoader.load();
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("My way");
		primaryStage.show();
	}

}
