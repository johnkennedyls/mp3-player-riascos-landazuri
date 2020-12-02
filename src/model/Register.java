package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import connection.ConnectionDB;

public class Register {

	private UserManager related;

	public Register() {
		related = new UserManager();
	}

	public String createRegister(String name, String email, String password, int id) throws SQLException {
		String info = "";
		String temp = "";
		temp += related.addUser(name, email, password, id);

		if (related.binarySearch(id)) {
			ConnectionDB con = new ConnectionDB();
			Connection conect = con.getConnection();
			PreparedStatement ps = conect.prepareStatement("INSERT INTO "
					+ "Usuario (id_usuario, corr_usuario, nom_usuario, con_usuario) "
					+ "VALUES(?, ?, ?, ?)");

			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setString(3, email);
			ps.setString(4, password);
			ps.executeUpdate();
			info += "Register created!";
		}
		else
			info += temp;
		return info;
	}

}
