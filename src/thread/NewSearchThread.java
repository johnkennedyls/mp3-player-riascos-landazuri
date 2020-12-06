package thread;

import javafx.application.Platform;
import model.Manager;
import ui.GUI_MP3;

public class NewSearchThread extends Thread{

	private Manager m;
	private GUI_MP3 guiMp3;
	private String searched;
	
	/**
	 * Este es el constructor de newSearchThread
	 * @param manager es la controladora del modelo
	 * @param gmp3 es la controladora de la GUI
	 * @param s es el nombre del la playlist que se busca
	 */
	public NewSearchThread(Manager manager, GUI_MP3 gmp3, String s) {
		setDaemon(true);
		m = manager;
		guiMp3 = gmp3;
		searched = s;
	}

	/**
	 * Este metodo es la accion del hilo. Este hilo busca una playlist y renueva la TableView de playlists
	 */
	public void run() {
		Platform.runLater(new Thread() {
			public void run() {
				guiMp3.initializePlaylistsGroupView(m.playlistSearched(searched));
			}
		});
	}
	
}
