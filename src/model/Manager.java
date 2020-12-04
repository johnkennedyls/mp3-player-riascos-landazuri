package model;


import java.io.FileNotFoundException;

import java.io.PrintWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import exceptions.NotFoundException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserDoesNotExistsException;

public class Manager implements Serializable{

	private static final long serialVersionUID = 1;
	private Song songPlaying;
	private List<Playlist> playlists;
	private List<User> users;
	
	private final static String SAVE_PATH_FILE_USERS = "data/users.xd";
	private final static String SAVE_PATH_FILE_PLAYLISTS = "data/playlists.xd";
	private final static String SAVE_PATH_FILE_SONGS = "data/songs.xd";
	private final static String SAVE_PATH_FILE_VIDEOS = "data/videos.xd";
	
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
		else if (!userExists(id)) {
			users.add(new User(na, em, pass, id));
			info += "User added succesfully";
		}
		else {
			throw new UserAlreadyExistsException();
		}
		return info;
	}

	public boolean userExists(int id) {
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
	
	public User searchUser(int id) {
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
		User user = searchUser(id);
		if (user != null) {
			users.remove(user);
		}
		else
			throw new UserDoesNotExistsException();
		return info;
	}
	
	public void saveData(String type) throws IOException{
		if(type.equalsIgnoreCase("users")) {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH_FILE_USERS));
			oos.writeObject(users);
			oos.close();
		} 
		if(type.equalsIgnoreCase("play")) {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH_FILE_PLAYLISTS));
			oos.writeObject(playlists);
			oos.close();
		}
		if(type.equalsIgnoreCase("song")) {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH_FILE_SONGS));
			for (int i = 0; i < playlists.size(); i++) {
				oos.writeObject(playlists.get(i).getFirstSong());
			}
			oos.close();
		} 
		if(type.equalsIgnoreCase("video")) {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH_FILE_VIDEOS));
			for (int i = 0; i < playlists.size(); i++) {
				oos.writeObject(playlists.get(i).getFirstVideo());
			}
			oos.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public String loadData() throws IOException, ClassNotFoundException, NotFoundException{
		File u = new File(SAVE_PATH_FILE_USERS);
		File p = new File(SAVE_PATH_FILE_PLAYLISTS);
		File ps = new File(SAVE_PATH_FILE_SONGS);
		File pv = new File(SAVE_PATH_FILE_VIDEOS);
		String info = "";
		boolean loaded = false;
		if(u.exists()){
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(u));
			users = (List<User>)ois.readObject();
			ois.close();	
			loaded = true;
		}
		else {
			throw new NotFoundException();
		}
		if(p.exists()){
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(p));
			playlists = (List<Playlist>)ois.readObject();
			ois.close();
			loaded = true;
		}
		else {
			throw new NotFoundException();
		}
		if(ps.exists()){
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ps));
			for (int i = 0; i < playlists.size(); i++) {
				Song firstSong = (Song)ois.readObject();
				playlists.get(i).setFirstSong(firstSong); 
			}
			ois.close();	
		}
		else {
			throw new NotFoundException();
		}
		if(pv.exists()){
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(pv));
			for (int i = 0; i < playlists.size(); i++) {
				Video firstVideo = (Video)ois.readObject();
				playlists.get(i).setFirstVideo(firstVideo); 
			}
			ois.close();
		}
		else {
			throw new NotFoundException();
		}
		if (!loaded) {
			info += "Nothing to load";
		}
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
