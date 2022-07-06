package client;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import org.json.simple.JSONObject;

import helper.SecurityHelper;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class PanelChatter extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField tfMessage;
	private JTextArea taContent;
	
	public void appendMessage(String msg) {
		taContent.append(msg + "\n");
	}

	/**
	 * Create the panel.
	 */
	public PanelChatter(Main main, String receiver) {
		
		JScrollPane scrollPane = new JScrollPane();
		
		tfMessage = new JTextField();
		tfMessage.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String msg = tfMessage.getText();
				if (msg.equals("")) 
					return;
				JSONObject jo = new JSONObject();
				jo.put("type", "SEND");
				jo.put("message", SecurityHelper.encryptMessage(main.getPublicKey(), msg));
				jo.put("receiver", receiver);
				appendMessage("Me: " + msg);
				try {
					main.getOos().writeObject(jo);
				} catch (IOException e) {
					e.printStackTrace();
				}
				tfMessage.setText("");
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(15)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(tfMessage, GroupLayout.PREFERRED_SIZE, 371, GroupLayout.PREFERRED_SIZE)
							.addGap(34)
							.addComponent(btnSend))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 507, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(36)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
					.addGap(43)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfMessage, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSend))
					.addContainerGap(112, Short.MAX_VALUE))
		);
		
		taContent = new JTextArea();
		scrollPane.setViewportView(taContent);
		setLayout(groupLayout);

	}
}
