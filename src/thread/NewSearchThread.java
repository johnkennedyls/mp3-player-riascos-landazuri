package thread;

import javafx.application.Platform;
import model.Manager;
import ui.GUI_MP3;

public class NewSearchThread extends Thread{

	private Manager m;
	private GUI_MP3 guiMp3;
	private String searched;
	
	/**
	 * This is the constructor of NewSearchThread
	 * @param manager is the controller class
	 * @param gmp3 is the GUI controller class
	 * @param s is the name of the playlist searched
	 */
	public NewSearchThread(Manager manager, GUI_MP3 gmp3, String s) {
		setDaemon(true);
		m = manager;
		guiMp3 = gmp3;
		searched = s;
	}

	/**
	 * This method is the action of the thread. This thread actualize the table view with the playlist searched by name
	 */
	public void run() {
		Platform.runLater(new Thread() {
			public void run() {
				guiMp3.initializePlaylistsGroupView(m.playlistSearched(searched));
			}
		});
	}
	
}
