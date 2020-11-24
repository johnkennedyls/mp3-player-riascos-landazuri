package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDB {

	//	The connection variables comes here

	private static final String URL = "jdbc:mysql://localhost:3306/MyWay?autoReconnect=true&useSSL=false";
	private static final String USER = "root";
	private static final String PASSWORD = "Max64512";
	private static final String DRIVER = "com.mysql.jdbc.Driver";

	public Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(DRIVER);
			con = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}
