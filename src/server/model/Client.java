package server.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.PublicKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
	private User user;
	private PublicKey publicKey;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public Client(User user) {
		this.user = user;
		this.publicKey = null;
		this.oos = null;
		this.ois = null;
	}
}
