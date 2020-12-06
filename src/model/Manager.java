package model;


import java.io.FileNotFoundException;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import exceptions.NotFoundException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserDoesNotExistsException;

public class Manager implements Serializable{

	private static final long serialVersionUID = 1;
	private Song songPlaying;
	private List<User> users;
	private int current;

	private final static String SAVE_PATH_FILE_USERS = "data/users.xd";

	public Manager() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, UserAlreadyExistsException {	
		users = new ArrayList<>();
//		addUser("Julian", "julian@gmail.com","1234",123);
//		users.get(0).addPlaylist("Songs");
//		users.get(0).getPlaylists().get(0).addSong("multimedia/3 Doors Down - Here Without You.mp3");
//		users.get(0).getPlaylists().get(0).addSong("multimedia/505 lyrics - Arctic Monkeys.mp3");
//		users.get(0).addPlaylist("Videos", "MP4");
//		users.get(0).getPlaylists().get(1).addVideo("multimedia/11440017.MP4");
//		users.get(0).getPlaylists().get(1).addVideo("multimedia/11450004.MP4");
//		addUser("Juan Manuel", "seyerman@gmail.com","1234",456);
//		users.get(1).addPlaylist("Songs");
//		users.get(1).getPlaylists().get(0).addSong("multimedia/3 Doors Down - Here Without You.mp3");
//		users.get(1).getPlaylists().get(0).addSong("multimedia/505 lyrics - Arctic Monkeys.mp3");
//		users.get(1).addPlaylist("Videos", "MP4");
//		users.get(1).getPlaylists().get(1).addVideo("multimedia/11440017.MP4");
//		users.get(1).getPlaylists().get(1).addVideo("multimedia/11450004.MP4");
//		addUser("Gallo", "gallo@gmail.com","1234",789);
//		users.get(2).addPlaylist("Songs");
//		users.get(2).getPlaylists().get(0).addSong("multimedia/3 Doors Down - Here Without You.mp3");
//		users.get(2).getPlaylists().get(0).addSong("multimedia/505 lyrics - Arctic Monkeys.mp3");
//		users.get(2).addPlaylist("Videos", "MP4");
//		users.get(2).getPlaylists().get(1).addVideo("multimedia/11440017.MP4");
//		users.get(2).getPlaylists().get(1).addVideo("multimedia/11450004.MP4");
//		saveData();
	}
	
	public Manager(String test) {
		users = new ArrayList<>();
	}

	public String addUser(String na, String em, String pass, int id) throws UserAlreadyExistsException, CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
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
			int middle = ( start + end ) / 2;
			if(users.get(middle).getId() == id )
				found = true;
			else if(users.get(middle).getId() > id )
				end = middle - 1;
			else
				start = middle + 1;
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
			int middle = ( start + end ) / 2;
			if(users.get(middle).getId() == id ) {
				found = true;
				searched = users.get(middle);
			}
			else if(users.get(middle).getId() > id )
				end = middle - 1;
			else
				start = middle + 1;
		}
		return searched;
	}

	public int userPosition(int id) {
		boolean found = false;
		int start = 0;
		int end = users.size() - 1;
		int middle = 0;

		List<User> clon = new ArrayList<>();

		for (int i = 0; i < users.size(); i++) {
			clon.add(users.get(i));
		}

		users = burbleSort(users); 

		while(start <= end && !found ) {	
			middle = ( start + end ) / 2;
			if(users.get(middle).getId() == id ) {
				found = true;
			}
			else if(users.get(middle).getId() > id )
				end = middle - 1;
			else
				start = middle + 1;
		}

		users = clon;

		return middle;
	}

	public List<User> burbleSort(List<User> users) {
		User current;
		List<User> sorted;
		for(int i = 2; i < users.size(); i++){
			for(int j = 0;j < users.size()-i;j++){
				if(users.get(j).getId() > users.get(j+1).getId()){
					current = users.get(j+1);
					users.set(j+1, users.get(j));
					users.set(j, current);
				}   
			}
		}
		sorted = users;
		return sorted;
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

	public void saveData() throws IOException{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH_FILE_USERS));
		oos.writeObject(users);
		oos.close();
	}

	@SuppressWarnings("unchecked")
	public String loadData() throws IOException, ClassNotFoundException, NotFoundException{
		File u = new File(SAVE_PATH_FILE_USERS);
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
		if (!loaded) {
			info += "Nothing to load";
		}
		return info;
	}

	public void exportPlayListsData(String fileName) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(fileName);	
		for (int i = 0; i < users.get(userPosition(current)).getPlaylists().size(); i++) {
			Playlist myPlaylist = users.get(userPosition(current)).getPlaylists().get(i);			
			pw.println(myPlaylist.toString());
		}
		pw.close();
	}

	public void exportUsersData(String fileName) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(fileName);
		pw.println("Name;ID;Email;Password");
		for (int i = 0; i < users.size(); i++) {
			User myUser = users.get(i);			
			pw.println(myUser.toString());
		}
		pw.close();
	}

	public void importUsersData(String fileName) throws IOException, NumberFormatException, UserAlreadyExistsException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		br.readLine();
		String line = br.readLine();
		while(line!=null){
			String[] parts = line.split(";");
			addUser(parts[0],parts[2],parts[3],Integer.parseInt(parts[1]));	
			line = br.readLine();
		}
		br.close();
	}

	public List<Playlist> playlistSearched(String name){
		List<Playlist> newPlaylists = new ArrayList<>();
		for (int i = 0; i < users.get(userPosition(current)).getPlaylists().size(); i++) {
			if (users.get(userPosition(current)).getPlaylists().get(i).getName().contains(name)) {
				newPlaylists.add(users.get(userPosition(current)).getPlaylists().get(i));
			}
		}
		return newPlaylists;
	}

	public List<User> getUsers() {
		return users;
	}

	public Song getSongPlaying() {
		return songPlaying;
	}

	public void setSongPlaying(Song song) {
		songPlaying = song;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}


}
