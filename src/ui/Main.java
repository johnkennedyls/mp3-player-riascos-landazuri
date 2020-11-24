package ui;


import java.io.FileNotFoundException;
import java.sql.Connection;
import connection.ConnectionDB;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) throws FileNotFoundException{

		ConnectionDB con = new ConnectionDB();

		@SuppressWarnings("unused")
		Connection conect = (Connection) con.getConnection();	

		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {		
		Parent root = FXMLLoader.load(getClass().getResource("registerView.fxml"));

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("MY WAY test");
		primaryStage.show();
	}

}
