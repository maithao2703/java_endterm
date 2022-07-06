package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Hashtable;

import org.json.simple.JSONObject;

import constant.TypeConstant;
import helper.SecurityHelper;
import server.model.Client;
import server.model.Message;
import server.model.User;
import server.repository.MessageRepository;
import server.repository.UserRepository;

public class Server {
	private Hashtable<String, Client> clients;
	private UserRepository userRepo;
	private MessageRepository messageRepo;
	private KeyPair keyPair;

	public Server() {
		userRepo = new UserRepository();
		clients = new Hashtable<>();
		messageRepo = new MessageRepository();
		userRepo.getAllUser().forEach(user -> getClients().put(user.getUsername(), new Client(user)));
		keyPair = SecurityHelper.generateKeyPairRSA();
	}

	public int getIdByUsername(String username) {
		return clients.get(username).getUser().getId();
	}

	public boolean sendMessage(String sender, String receiver, String message) {
		int senderId = getIdByUsername(sender);
		int receiverId = getIdByUsername(receiver);
		if (clients.get(receiver).getPublicKey() != null) {
			try {
				JSONObject jo = new JSONObject();
				jo.put("type", "RECEIVE");
				jo.put("message", SecurityHelper.encryptMessage(clients.get(receiver).getPublicKey(), message));
				jo.put("sender", sender);

				clients.get(receiver).getOos().writeObject(jo);
			} catch (IOException e) {
				System.out.println("client offline");
			}
		}
		return messageRepo.createMessage(new Message(senderId, receiverId, message));
	}

	public void start() {
		try {
			ServerSocket ss = new ServerSocket(2022);
			JSONObject jo = null;
			while (true) {
				// nhận kết nối với 1 client
				Socket socket = ss.accept();
				// Tạo đối tượng dùng để gửi object cho client
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				// Tạo đối tượng nhận object từ client
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

				jo = (JSONObject) ois.readObject();
//				tạo user từ username, password người dùng cung cấp
				User newUser = new User(jo.get("username").toString(), jo.get("password").toString());
				newUser.save();
				// nếu yêu cầu là đăng ký tài khoản
				if (jo.get("type").toString().equals(TypeConstant.SIGN_UP)) {
					// gọi hàm đăng ký tài khoản từ đối tượng user repository
					// nếu thành công thì trả về id người dùng trong db
					jo.clear();
					int id = getUserRepo().createUser(newUser);
					jo.put("type", TypeConstant.SIGN_UP);
					// nếu không thành công thì id trả về là -1
					if (id != -1) {
						newUser.setId(id);
						Client c = new Client(newUser, (PublicKey) jo.get(TypeConstant.PUBLIC_KEY), ois, oos);
						getClients().put(newUser.getUsername(), c);
						jo.put("success", true);
						jo.put(TypeConstant.PUBLIC_KEY, getKeyPair().getPublic());
						ThreadServerChatter tsc = new ThreadServerChatter(Server.this, newUser.getUsername());
						tsc.start();
					} else {
						jo.put("success", false);
					}
					oos.writeObject(jo);
					oos.flush();
				} else {
					User user = getClients().get(newUser.getUsername()).getUser();
					jo.put("type", TypeConstant.LOGIN);
					if (user.isPasswordMatch(jo.get("password").toString())) {
						Client c = new Client(user, (PublicKey) jo.get(TypeConstant.PUBLIC_KEY), ois, oos);
						jo.put("success", true);
						jo.put(TypeConstant.PUBLIC_KEY, getKeyPair().getPublic());
						getClients().replace(user.getUsername(), c);
						ThreadServerChatter tsc = new ThreadServerChatter(Server.this, newUser.getUsername());
						tsc.start();
					} else {
						jo.put("success", false);
					}
					oos.writeObject(jo);
					oos.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server s = new Server();
		s.start();
	}

	public Hashtable<String, Client> getClients() {
		return clients;
	}

	public void setClients(Hashtable<String, Client> clients) {
		this.clients = clients;
	}

	public UserRepository getUserRepo() {
		return userRepo;
	}

	public void setUserRepo(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	public KeyPair getKeyPair() {
		return keyPair;
	}

	public void setKeyPair(KeyPair keyPair) {
		this.keyPair = keyPair;
	}

	public MessageRepository getMessageRepo() {
		return messageRepo;
	}

	public void setMessageRepo(MessageRepository messageRepo) {
		this.messageRepo = messageRepo;
	}
}
