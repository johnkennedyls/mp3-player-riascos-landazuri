package model;

import java.util.ArrayList;
import java.util.List;

public class UserManager {

	private List<User> users;

	public UserManager() {
		users = new ArrayList<>();
	}

	public String addUser(String na, String em, String pass, int id) {
		String info = "";
		if (uniqueUserId(id)) {
			users.add(new User(na, em, pass, id));
		}
		else {
			
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
	
}
