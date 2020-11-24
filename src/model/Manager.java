package model;

public class Manager {

	private Register r;
	
	public Manager() {
		r = new Register();
	}

	public String createRegister(String id, String name, String email, String password) {
		return r.createRegister(id, name, email, password);
	}
	
}
