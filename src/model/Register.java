package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import connection.ConnectionDB;

public class Register {

	public Register() {
	}

	public String createRegister(String id, String name, String email, String password) {
		String info = "";
		try {
			ConnectionDB con = new ConnectionDB();
			Connection conect = con.getConnection();
			PreparedStatement ps = conect.prepareStatement("INSERT INTO Usuario (id_usuario, corr_usuario, nom_usuario, con_usuario) VALUES(" + id + ',' + name + ',' + email + ',' + password +")");
			ps.executeUpdate();
			info += "Register created!";
			
		} catch (SQLException e) {
			info += "Error in the creation of the register";
		}
		return info;
	}
	
}
