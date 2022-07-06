package server.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import server.config.DBConfig;

public class DBConnector extends DBConfig {
	protected Connection conn = null;

	public DBConnector() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String uri = "jdbc:mysql://" + HOST + ":" + PORT + "/" + NAME;
			conn = DriverManager.getConnection(uri, USERNAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
