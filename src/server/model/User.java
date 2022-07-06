package server.model;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
	private int id;
	private String username;
	private String password;
	private Date createdAt;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public void save() {
		password = DigestUtils.sha256Hex(password);
	}
	
	public boolean isPasswordMatch(String password) {
		return this.password.equals(DigestUtils.sha256Hex(password));
	}
}
