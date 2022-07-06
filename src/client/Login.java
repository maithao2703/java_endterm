package client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PublicKey;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.simple.JSONObject;

import constant.TypeConstant;
import helper.SecurityHelper;

public class Login extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField tfUsername;
	private JPasswordField pfPassword;

	/**
	 * Create the panel.
	 */
	public Login(Main main) {
		
		tfUsername = new JTextField();
		tfUsername.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("LOGIN");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		
		pfPassword = new JPasswordField();
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = tfUsername.getText();
				if (username.equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), "Please enter username");
					return;
				}
				
				String password = String.valueOf(pfPassword.getPassword());
				if (password.equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), "Please enter password");
				}
				
				try {
					Socket socket = new Socket("localhost", 2022);
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					JSONObject jo = new JSONObject();
					jo.put(TypeConstant.TYPE, TypeConstant.LOGIN);
					jo.put(TypeConstant.USERNAME, username);
					jo.put(TypeConstant.PASSWORD, password);
					KeyPair kp = SecurityHelper.generateKeyPairRSA();
					jo.put(TypeConstant.PUBLIC_KEY, kp.getPublic());
					
					oos.writeObject(jo);
					oos.flush();
					
					jo.clear();
					
					jo = (JSONObject) ois.readObject();
					if ((boolean) jo.get("success")) {
						main.setOis(ois);
						main.setOos(oos);
						main.setPublicKey((PublicKey) jo.get(TypeConstant.PUBLIC_KEY));
						main.setPrivateKey(kp.getPrivate());
						JOptionPane.showMessageDialog(new JFrame(), "Login success");
						main.changeLayout(new Home(main));
					} else {
						JOptionPane.showMessageDialog(new JFrame(), "Login failed");
						ois.close();
						oos.close();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main.changeLayout(new SignUp(main));
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(239)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_2)
								.addComponent(lblNewLabel_1))
							.addGap(50)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(tfUsername)
								.addComponent(lblNewLabel)
								.addComponent(pfPassword)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(260)
							.addComponent(btnSignUp)
							.addGap(31)
							.addComponent(btnSubmit)))
					.addContainerGap(297, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(83)
					.addComponent(lblNewLabel)
					.addGap(79)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1))
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(pfPassword, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2))
					.addGap(37)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSubmit)
						.addComponent(btnSignUp))
					.addContainerGap(218, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
}
