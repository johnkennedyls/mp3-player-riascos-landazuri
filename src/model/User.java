package model;

import java.io.Serializable;

public class User implements Comparable<User>, Serializable{

	private static final long serialVersionUID = 1;
	private String name;
	private String email;
	private String passWord;
	private int id;
	
	public User(String na, String em, String pass, int ide) {
		name = na;
		email = em;
		passWord = pass;
		id = ide;
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
