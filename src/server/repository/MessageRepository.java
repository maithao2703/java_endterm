package server.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import server.model.Message;

public class MessageRepository extends DBConnector {
	public boolean createMessage(Message message) {
		String sql = "insert into message(content,sender,receiver) values(?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, message.getContent());
			ps.setInt(2, message.getSender());
			ps.setInt(3, message.getReceiver());
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
//	public List<Message> getLastTenMessage(int sender, int receiver) {
//		List<Message> messages = new ArrayList<>();
//		String sql = "select * from message where (sender=? and receiver=?) or (sender=? and receiver=?)";
//
//		return messages;
//	}
}
