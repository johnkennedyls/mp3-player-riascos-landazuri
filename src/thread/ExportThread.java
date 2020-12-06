package thread;

import java.io.FileNotFoundException;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import model.Manager;

public class ExportThread extends Thread{

	private Manager m;
	private String fileName;
	
	/**
	 * This is the constructor of ExportThread
	 * @param manager is the controller class
	 * @param s is the file name of the file to export
	 */
	public ExportThread(Manager manager, String s) {
		setDaemon(true);
		m = manager;
		fileName = s;
	}

	/**
	 * This method is the action of the thread. This thread exports users and playlists data.
	 */
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
