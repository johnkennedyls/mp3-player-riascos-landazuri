package model;

public class Manager {

	private Register r;
	private Playlist p;
	
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
	
}
