package client;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.simple.JSONObject;

import helper.SecurityHelper;

public class ThreadClientChatter {
	private Runnable runnable;

	public ThreadClientChatter(Main main, Home home) {
		runnable = new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						// nhận object json từ server
						JSONObject jo = (JSONObject) main.getOis().readObject();
						// nếu là phản hồi yêu cầu tìm kiếm user
						if (jo.get("type").toString().equals("FIND")) {
							// nếu trả về tìm kiếm thành công
							if ((boolean) jo.get("success")) {
								// lấy ra username của người vừa tìm
								String receiver = jo.get("username").toString();
								// tạo chat panel mới cho người dùng đó
								PanelChatter p = new PanelChatter(main, receiver);
								// thêm chat panel vào hash table để tiện cho việc tìm kiếm
								main.getConversations().put(receiver, p);
								// add chat panel vào tabbed
								home.getTabbedPane().add(receiver, p);
							} else {
								// nếu không tìm thấy thì hiển thị thông báo
								JOptionPane.showMessageDialog(new JFrame(), "This user could not be found");
							}
						} else {
							String sender = jo.get("sender").toString();
							if (!main.getConversations().containsKey(sender)) {
								PanelChatter p = new PanelChatter(main, sender);
								main.getConversations().put(sender, p);
								home.getTabbedPane().add(sender, p);
							}

							String message = SecurityHelper.decryptMessage(main.getPrivateKey(),
									jo.get("message").toString());

							main.getConversations().get(sender).appendMessage(sender + ": " + message);
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
