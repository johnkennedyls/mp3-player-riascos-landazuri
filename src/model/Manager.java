package model;

import java.util.ArrayList;
import java.util.List;

public class Manager {

	private Register r;
	private Song songPlaying;
	private List<Playlist> playlists;
	private List<User> users;
	
	public Manager() {
		r = new Register();
		playlists = new ArrayList<>();
		users = new ArrayList<>();
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
	
	public String addUser(String na, String em, String pass, int id) {
		String info = "";
		if (uniqueUserId(id)) {
			users.add(new User(na, em, pass, id));
			info += "User added succesfully";
		}
		else {
			info += "The ID alredy exists";
		}
		return info;
	}
	
	/**
	 * This method check if the user ID isn't duplicate
	 * @param id is the user ID number 
	 * @return a boolean with the result of the search
	 */
	public boolean uniqueUserId(int id){
		boolean unique = true;
		for(int i=0; i<users.size() && unique; i++){
			if(users.get(i).getId() == id){
				unique = false;
			}
		}
		return unique;
	}

	public boolean binarySearch(int id) {
		boolean found = false;
		int start = 0;
		int end = users.size() - 1;

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

//	public void removeUser(int id) {
//		
//		users.remove();
//	}
	
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
