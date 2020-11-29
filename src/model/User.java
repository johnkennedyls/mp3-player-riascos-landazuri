package model;

public class User {
	private String name;
	private String email;
	private String passWord;
	
	public User(String na, String em, String pass) {
		name = na;
		email = em;
		passWord = pass;
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
	
	
	
}
