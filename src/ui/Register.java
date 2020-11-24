package ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import connection.ConnectionDB;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Register {

	@FXML
	private Label id;
	
	@FXML
	private Label name;
	
	@FXML
	private Label email;
	
	@FXML
	private Label password;

	public Register() {

		try {
			ConnectionDB con = new ConnectionDB();
			Connection conect = con.getConnection();

			PreparedStatement ps = conect.prepareStatement("INSERT INTO Usuario (id_usuario, corr_usuario, nom_usuario, con_usuario) VALUES(" + id.getText() + "','" + name.getText() + "','" + email.getText() + "','" + password.getText() +")");

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
