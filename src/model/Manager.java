package model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import exceptions.UserAlreadyExistsException;
import exceptions.UserDoesNotExistsException;

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
	
	public String addUser(String na, String em, String pass, int id) throws UserAlreadyExistsException {
		String info = "";
		if (users.isEmpty()) {
			users.add(new User(na, em, pass, id));
		}
		else if (!binarySearch(id)) {
			users.add(new User(na, em, pass, id));
			info += "User added succesfully";
		}
		else {
			throw new UserAlreadyExistsException();
		}
		return info;
	}

	public boolean binarySearch(int id) {
		boolean found = false;
		int start = 0;
		int end = users.size() - 1;
		
		List<User> clon = new ArrayList<>();
		
		for (int i = 0; i < users.size(); i++) {
			clon.add(users.get(i));
		}
		
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
		
		users = clon;
		return found;
	}
	
	public User binarySearch2(int id) {
		User searched = null;
		boolean found = false;
		int start = 0;
		int end = users.size() - 1;
		
		Object[] toSort =  users.toArray();
		
		Arrays.sort(toSort);
		
		users.clear();
		
		for (int i = 0; i < toSort.length; i++) {
			users.add((User)toSort[i]);
		}
		
		while(start <= end && !found ) {	
			int medio = ( start + end ) / 2;
			if(users.get(medio).getId() == id ) {
				found = true;
				searched = users.get(medio);
			}
			else if(users.get(medio).getId() > id )
				end = medio - 1;
			else
				start = medio + 1;
		}
		return searched;
	}
	
	public String removeUser(int id) throws UserDoesNotExistsException{
		String info = "";
		User user = binarySearch2(id);
		if (user != null) {
			users.remove(user);
		}
		else
			throw new UserDoesNotExistsException();
		return info;
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
	
	
	
	public String toStringUsers() {
		String msg = "Users List: \n ";
		for(User user : users) {
			msg += user.toString();
		}
		return msg;
	}

	public String toStringPlaylists() {
		String msg = "Playlists List: \n ";
		for(Playlist playlist : playlists) {
			msg += playlist.toString();
		}
		return msg;
	}
	
	public void exportPlayListsData(String fileName) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(fileName);
		for (int i = 0; i < playlists.size(); i++) {
			Playlist myPlaylist = playlists.get(i);			
			pw.println(myPlaylist.toString());
		}
		pw.close();
	}
	
	public void exportUsersData(String fileName) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(fileName);
		for (int i = 0; i < users.size(); i++) {
			User myUser = users.get(i);			
			pw.println(myUser.toString());
		}
		pw.close();
	}
	

}
