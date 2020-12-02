package model;

import java.util.ArrayList;
import java.util.List;

public class Manager {

	private Register r;
	private Song songPlaying;
	private List<Playlist> playlists;
	
	public Manager() {
		r = new Register();
		playlists = new ArrayList<>();
	}

	public Register getRegister() {
		return r;
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

	public Song getSongPlaying() {
		return songPlaying;
	}
	
	public void setSongPlaying(Song song) {
		songPlaying = song;
	}
	
}
