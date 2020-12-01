package model;

import java.util.ArrayList;
import java.util.List;

public class UserManager {

	private List<User> users;

	public UserManager() {
		users = new ArrayList<>();
	}

	public void addUser(String na, String em, String pass, int id) {
		users.add(new User(na, em, pass, id));
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
