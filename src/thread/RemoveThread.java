package thread;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import model.Manager;
import model.Playlist;
import ui.GUI_MP3;

public class RemoveThread extends Thread{

	private Manager m;
	private GUI_MP3 guiMp3;
	private Playlist searched;
	
	/**
	 * This is the constructor of RemoveThread
	 * @param manager is the controller class
	 * @param gmp3 is the GUI controller class
	 * @param s is the playlist to remove
	 */
	public RemoveThread(Manager manager, GUI_MP3 gmp3, Playlist s) {
		setDaemon(true);
		m = manager;
		guiMp3 = gmp3;
		searched = s;
	}

	/**
	 * This method is the action of the thread. This thread removes a playlist from the arraylist of playlist of the current user
	 */
	public void run() {
		Platform.runLater(new Thread() {
			public void run() {
				m.getUsers().get(m.userPosition(m.getCurrent())).removePlaylist(searched);
				new Alert(Alert.AlertType.INFORMATION,"Playlist with name " + searched.getName() + " was eliminated").showAndWait();
				guiMp3.initializePlaylistsGroupView();
			}
		});
	}
	
}
