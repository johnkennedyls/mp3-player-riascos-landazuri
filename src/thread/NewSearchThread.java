package thread;

import javafx.application.Platform;
import model.Manager;
import ui.GUI_MP3;

public class NewSearchThread extends Thread{

	private Manager m;
	private GUI_MP3 guiMp3;
	private String searched;
	
	public NewSearchThread(Manager manager, GUI_MP3 gmp3, String s) {
		setDaemon(true);
		m = manager;
		guiMp3 = gmp3;
		searched = s;
	}

	public void run() {
		Platform.runLater(new Thread() {
			public void run() {
				guiMp3.initializePlaylistsGroupView(m.playlistSearched(searched));
			}
		});
	}
	
}
