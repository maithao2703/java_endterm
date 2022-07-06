package server.model;

import java.util.Date;

import lombok.Data;

@Data
public class Message {
	public Message(int senderId, int receiverId, String message) {
		this.sender = senderId;
		this.receiver = receiverId;
		this.content = message;
	}
	private int id;
	private String content;
	private int sender;
	private int receiver;
	private Date createdAt;
}
