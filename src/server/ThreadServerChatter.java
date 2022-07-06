package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.json.simple.JSONObject;

import helper.SecurityHelper;

public class ThreadServerChatter {
	private Runnable runnable;

	public ThreadServerChatter(Server server, String sender) {
		runnable = new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						ObjectOutputStream oos = server.getClients().get(sender).getOos();
						ObjectInputStream ois = server.getClients().get(sender).getOis();
						JSONObject jo = (JSONObject) ois.readObject();
						System.out.println(jo);
						if (jo.get("type").toString().equals("FIND")) {
							String username = jo.get("username").toString();
							if (server.getClients().containsKey(username)) {
								jo.put("success", true);
							} else {
								jo.put("success", false);
							}
							oos.writeObject(jo);
							oos.flush();
						} else {
							String receiver = jo.get("receiver").toString();
							String message = SecurityHelper.decryptMessage(server.getKeyPair().getPrivate(),
									jo.get("message").toString());
							server.sendMessage(sender, receiver, message);
						}
						Thread.sleep(500);
					}
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
	}

	public void start() {
		Thread t = new Thread(runnable);
		t.start();
	}

}
