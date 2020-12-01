package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDB {

	private static final String URL = "jdbc:mysql://localhost:3306/MyWay?autoReconnect=true&useSSL=false";
	private static final String USER = "root";
	private static final String PASSWORD = "Max64512";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private Connection con;

	public ConnectionDB() {
		try {
			Class.forName(DRIVER);
			con = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return con;
	}
	
	public void disconect() {
		con = null;
	}
}
