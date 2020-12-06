package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Comparable<User>, Serializable{

	private static final long serialVersionUID = 1;
	private String name;
	private String email;
	private String passWord;
	private int id;
	private List<Playlist> playlists;
	private int numPlaylists;
	private User sonL;
	private User sonR;
	
	public User(String na, String em, String pass, int ide) {
		name = na;
		email = em;
		passWord = pass;
		id = ide;
		playlists = new ArrayList<>();
	}
	
	public User() {
		playlists = new ArrayList<>();
	}

	public void addPlaylist(String name) {
		playlists.add(new Playlist(name));
		numPlaylists = playlists.size();
	}

	public void addPlaylist(String name, String content) {
		playlists.add(new Playlist(name, content));
		numPlaylists = playlists.size();
	}
	
	public void removePlaylist(Playlist searched) {
		playlists.remove(searched);
		numPlaylists = playlists.size();
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassWord() {
		return passWord;
	}
	
	public int getId() {
		return id;
	}
	
	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}

	public User getSonL() {
		return sonL;
	}

	public void setSonL(User sonL) {
		this.sonL = sonL;
	}

	public User getSonR() {
		return sonR;
	}

	public void setSonR(User sonR) {
		this.sonR = sonR;
	}

	public int getNumPlaylists() {
		return numPlaylists;
	}

	public void setNumPlaylists(int numPlaylists) {
		this.numPlaylists = numPlaylists;
	}

	@Override
	public int compareTo(User user) {
		int comp;
		if(id<user.getId()) {
			comp = -1;
		}else if(id>user.getId()) {
			comp = 1;
		}else {
			comp = 0;
		}
		return comp;
	}
	
	public String toString() {
		String msg = "";
		String separator = ";";
		msg = name + separator + id + separator + email + separator + passWord;
		return msg;
	}
}
