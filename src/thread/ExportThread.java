package thread;

import java.io.FileNotFoundException;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import model.Manager;

public class ExportThread extends Thread{

	private Manager m;
	private String fileName;
	
	public ExportThread(Manager manager, String s) {
		setDaemon(true);
		m = manager;
		fileName = s;
	}

	public void run() {
		Platform.runLater(new Thread() {
			public void run() {
				try {
					m.exportPlayListsData("data/" + fileName + "Playlists.csv");
					m.exportUsersData("data/" +fileName+"Users.csv");
					new Alert(Alert.AlertType.INFORMATION,"Playlists and users exported").showAndWait();
				} catch (FileNotFoundException e) {
					new Alert(Alert.AlertType.ERROR,"Can't export data, verify your configuration please").showAndWait();
				}	
			}
		});
	}
	
	
}
