package model;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
		private List<User> users;
		
		public UserManager() {
			users = new ArrayList<>();
		}
		
		public void addUser(String na, String em, String pass) {
			users.add(new User(na,em,pass));
		}

		public List<User> getUsers() {
			return users;
		}
		
		
}