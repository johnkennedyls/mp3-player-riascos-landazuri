package model;

import java.util.ArrayList;
import java.util.List;

public class PlaylistManager {

	private List<Playlist> playlists;

	public PlaylistManager() {	
		playlists = new ArrayList<>();
	}

	public void addPlaylist(String name) {
		playlists.add(new Playlist(name));
	}

	public void addPlaylist(String name, String content) {
		playlists.add(new Playlist(name, content));
	}
	
	public List<Playlist> getPlaylists() {
		return playlists;
	}

}
