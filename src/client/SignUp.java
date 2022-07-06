package client;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.json.simple.JSONObject;

import constant.TypeConstant;
import helper.SecurityHelper;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.awt.event.ActionEvent;

public class SignUp extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField tfUsername;
	private JPasswordField pfPassword;
	private JPasswordField pfRepeatPassword;

	/**
	 * Create the panel.
	 */
	public SignUp(Main main) {
		
		JLabel lblNewLabel = new JLabel("Sign Up");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		
		tfUsername = new JTextField();
		tfUsername.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		
		JLabel lblNewLabel_3 = new JLabel("RepeatPassword");
		
		pfPassword = new JPasswordField();
		
		pfRepeatPassword = new JPasswordField();
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = tfUsername.getText();
				if (username.equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), "Please enter username");
					return;
				}
				
				String password = String.valueOf(pfPassword.getPassword());
				if (password.equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), "Please enter password");
					return;
				}
				String repeatPassword = String.valueOf(pfRepeatPassword.getPassword());
				if (!password.equals(repeatPassword)) {
					JOptionPane.showMessageDialog(new JFrame(), "Repeat password is not match");
					return;
				}
				
				try {
					Socket socket = new Socket("localhost", 2022);
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					
					JSONObject jo = new JSONObject();
					jo.put(TypeConstant.TYPE, TypeConstant.SIGN_UP);
					jo.put(TypeConstant.USERNAME, username);
					jo.put(TypeConstant.PASSWORD, password);
					KeyPair keyPair = SecurityHelper.generateKeyPairRSA();
					jo.put(TypeConstant.PUBLIC_KEY, keyPair.getPublic());
					
					oos.writeObject(jo);
					oos.flush();
					
					jo.clear();
					jo = (JSONObject) ois.readObject();
					if ((boolean)jo.get("success")) {
						main.setOis(ois);
						main.setOos(oos);
						main.setPublicKey((PublicKey) jo.get(TypeConstant.PUBLIC_KEY));
						main.setPrivateKey(keyPair.getPrivate());
						JOptionPane.showMessageDialog(new JFrame(), "Sign up success!");
						main.changeLayout(new Home(main));
					} else {
						oos.close();
						ois.close();
						JOptionPane.showMessageDialog(new JFrame(), "Sign up failed!");
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main.changeLayout(new Login(main));
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(163, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1)
						.addComponent(lblNewLabel_2)
						.addComponent(lblNewLabel_3))
					.addGap(54)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(tfUsername, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
						.addComponent(pfPassword, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
						.addComponent(pfRepeatPassword, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
					.addGap(237))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(297)
					.addComponent(lblNewLabel)
					.addContainerGap(351, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(201, Short.MAX_VALUE)
					.addComponent(btnLogin)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnSubmit)
					.addGap(294))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(57)
					.addComponent(lblNewLabel)
					.addGap(73)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(tfUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(pfPassword, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_3)
						.addComponent(pfRepeatPassword, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addGap(33)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSubmit)
						.addComponent(btnLogin))
					.addContainerGap(189, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
}
