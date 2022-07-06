package client;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.json.simple.JSONObject;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Home extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField tfUsername;
	private JTabbedPane tabbedPane;
	/**
	 * Create the panel.
	 */
	public Home(Main main) {
		
		setTabbedPane(new JTabbedPane(JTabbedPane.TOP));
		
		tfUsername = new JTextField();
		tfUsername.setColumns(10);
		
		JButton btnFind = new JButton("Find");
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = tfUsername.getText();
				JSONObject jo = new JSONObject();
				jo.put("type", "FIND");
				jo.put("username", username);
				System.out.println(jo);
				try {
					main.getOos().writeObject(jo);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		JLabel lblNewLabel = new JLabel("Find by username");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(97)
							.addComponent(lblNewLabel)
							.addGap(26)
							.addComponent(tfUsername, GroupLayout.PREFERRED_SIZE, 204, GroupLayout.PREFERRED_SIZE)
							.addGap(38)
							.addComponent(btnFind))
						.addComponent(getTabbedPane(), GroupLayout.PREFERRED_SIZE, 650, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(129, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(35)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnFind)
						.addComponent(tfUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addGap(18)
					.addComponent(getTabbedPane(), GroupLayout.PREFERRED_SIZE, 364, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(90, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

		ThreadClientChatter tcc = new ThreadClientChatter(main, Home.this);
		tcc.start();
	}
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}
}
