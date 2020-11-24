package model;

public class User {
	private String name;
	private String email;
	private String sex;
	private String passWord;
	
	public User(String na, String em, String sex, String pass) {
		name = na;
		email = em;
		this.sex = sex;
		passWord = pass;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getSex() {
		return sex;
	}

	public String getPassWord() {
		return passWord;
	}
	
	
	
}
