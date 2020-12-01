package model;

public class User {
	
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
}
