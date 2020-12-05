package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

public class User implements Comparable<User>, Serializable{

	private static final long serialVersionUID = 1;
	private String name;
	private String email;
	private String passWord;
	private int id;
	private List<Playlist> playlists;
	
	public User(String na, String em, String pass, int ide) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		name = na;
		email = em;
		passWord = pass;
		id = ide;
		playlists = new ArrayList<>();
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
	
	public void addPlaylist(String name) {
		playlists.add(new Playlist(name));
	}

	public void addPlaylist(String name, String content) {
		playlists.add(new Playlist(name, content));
	}

	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
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
		String separator = ", ";
		msg = name + separator + email + separator + passWord + separator + id;
		return msg;
	}
}
