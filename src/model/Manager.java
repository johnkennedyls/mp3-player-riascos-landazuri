package model;

public class Manager {

	private Register r;
	private PlaylistManager p;
	private Song songPlaying;
	
	public Manager() {
		r = new Register();
		p = new PlaylistManager();
	}

	public Register getRegister() {
		return r;
	}
	
	public PlaylistManager getPlaylistManager() {
		return p;
	}
	
	public Song getSongPlaying() {
		return songPlaying;
	}
	
	public void setSongPlaying(Song song) {
		songPlaying = song;
	}
	
}
