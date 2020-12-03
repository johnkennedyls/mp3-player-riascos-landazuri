package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Manager {

	private Song songPlaying;
	private List<Playlist> playlists;
	private List<User> users;
	
	public Manager() {
		playlists = new ArrayList<>();
		users = new ArrayList<>();
	}
	
	public void addPlaylist(String name) {
		playlists.add(new Playlist(name));
	}

	public void addPlaylist(String name, String content) {
		playlists.add(new Playlist(name, content));
	}
	
	public String addUser(String na, String em, String pass, int id) {
		String info = "";
		if (users.isEmpty()) {
			users.add(new User(na, em, pass, id));
		}
		else if (!binarySearch(id)) {
			users.add(new User(na, em, pass, id));
			info += "User added succesfully";
		}
		else {
			info += "The ID alredy exists";
		}
		return info;
	}

	public boolean binarySearch(int id) {
		boolean found = false;
		int start = 0;
		int end = users.size() - 1;
		
		class SortUsers implements Comparator<User>{
			@Override
			public int compare(User o1, User o2) {
				int value1 = 0;
				value1 = o1.getId() - o2.getId();
				return value1;
			}
		}
		Collections.sort(users, new SortUsers());
		
		while(start <= end && !found ) {	
			int medio = ( start + end ) / 2;
			if(users.get(medio).getId() == id )
				found = true;
			else if(users.get(medio).getId() > id )
				end = medio - 1;
			else
				start = medio + 1;
		}
		return found;
	}
	
	public void removeUser(int id) {
		
//		users.remove();
	}
	
	public List<User> getUsers() {
		return users;
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
