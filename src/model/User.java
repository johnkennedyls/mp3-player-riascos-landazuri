package model;

import java.io.Serializable;

public class User implements Comparable<User>, Serializable{

	private static final long serialVersionUID = 1;
	private String name;
	private String email;
	private String passWord;
	private int id;
	
	/**
	 * Constructor de la clase que representa al usuario.
	 * @param na:String nombre del usuario.
	 * @param em:String email del usuario.
	 * @param pass:String password del usuario.
	 * @param ide:int número de identidad del usuario.
	 */
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
/**
 * Se encarga de parametrizar la ubicación del objeto en una lista.
 * @return ubicación relativa en la lista.
 */
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
	/**
	 * Retorna el estado del usuario.
	 * return msg:String estado del usuario.
	 */
	public String toString() {
		String msg = "";
		String separator = ", ";
		msg = name + separator + email + separator + passWord + separator + id;
		return msg;
	}
}
