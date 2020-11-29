package model;

public class Manager {

	private Register r;
	private Playlist p;
	private Song songPlaying;
	
	public Manager() {
		r = new Register();
		p = new Playlist();
	}

	public Register getRegister() {
		return r;
	}
	
	public Playlist getPlaylist() {
		return p;
	}
	
	public Song getSongPlaying() {
		return songPlaying;
	}
	
	public void setSongPlaying(Song song) {
		songPlaying = song;
	}
	
}
