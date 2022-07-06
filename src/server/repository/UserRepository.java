package server.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import server.model.User;

public class UserRepository extends DBConnector {
	public UserRepository() {
		super();
	}

	public int createUser(User user) {
		String sql = "insert into user(username, password) values(?, ?)";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			} else
				return -1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public User getUserByUsername(String username) {
		String sql = "select * from user where username=?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
					rs.getDate("createdAt"));
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<User> getAllUser() {
		String sql = "select * from user";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<User> users = new ArrayList<>();
			while (rs.next()) {
				users.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
						rs.getDate("createdAt")));
			}
			
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
