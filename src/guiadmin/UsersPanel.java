package guiadmin;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;

import org.mindrot.jbcrypt.BCrypt;

import auxclasses.Hashing;
import connection.GetFromDB;
import people.Admin;

import people.User;
import people.User.Privilege;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
*
* @author vasile alexandru apetri
*/
public class UsersPanel extends JPanel {
	//the user panel
	private JTextField username_field;
	private JTextField password_field;
	private JTable table;
	private JComboBox privilege_field;
	private JScrollPane scrollPane;

	public void Refresh() {
		if (scrollPane != null)
			remove(scrollPane);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(288, 30, 465, 212);
		add(scrollPane);

		GetFromDB get = new GetFromDB();
		try {
			table = get.userTable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		get.closeConnection();
		table.getColumnModel().getColumn(0).setPreferredWidth(175);

		scrollPane.setViewportView(table);

		username_field.setText("");
		password_field.setText("");
		privilege_field.setSelectedIndex(0);
	}
	
	/**
	 * Create the panel.
	 * 
	 * @param user
	 */
	public UsersPanel(Admin admin) {
                //desining the layouts
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();

		setSize(804, 316);
		setLocation(screenSize.width / 4, screenSize.height / 4);

		setLayout(null);

		JLabel lblUsername = new JLabel("username");
		lblUsername.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblUsername.setBounds(23, 26, 90, 29);
		add(lblUsername);

		JLabel lblPassword = new JLabel("password");
		lblPassword.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblPassword.setBounds(23, 66, 90, 29);
		add(lblPassword);

		JLabel lblPrivillege = new JLabel("privilege");
		lblPrivillege.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblPrivillege.setBounds(23, 105, 90, 29);
		add(lblPrivillege);

		username_field = new JTextField();
		username_field.setBounds(144, 32, 101, 22);
		add(username_field);
		username_field.setColumns(10);

		password_field = new JTextField();
		password_field.setColumns(10);
		password_field.setBounds(144, 72, 101, 22);
		add(password_field);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                                //checks that everything is valid and hashes
				String username = username_field.getText();
				String password = password_field.getText();

				if (!Hashing.isValid(username) || !Hashing.isValid(password))
					JOptionPane.showMessageDialog(null, "Invalid input for username or password", "Error", 2);
				else {
					String privilege = (String) privilege_field.getSelectedItem();
					privilege = privilege.toUpperCase();
					User newuser = null;

					if (admin.usernameAlreadyInUsers(username)) {
						JOptionPane.showMessageDialog(null, "Username Already in Database", "Error", 2);
						Refresh();
					} else if (password.length() < 8) {
						JOptionPane.showMessageDialog(null, "Password must be atleast 8 caracters", "Error", 2);
						Refresh();
					} else {
						BCrypt encryption = new BCrypt();
						password =  encryption.hashpw(password, encryption.gensalt());		
						//using bCrypt, we hashed, and add the correct privilege
						switch (privilege) {
						case "ADMIN": {
							newuser = new User(username, password, Privilege.ADMIN);
							break;
						}
						case "REGISTRAR": {
							newuser = new User(username, password, Privilege.REGISTRAR);
							break;
						}
						case "TEACHER": {
							newuser = new User(username, password, Privilege.TEACHER);
							break;
						}
						case "STUDENT": {
							newuser = new User(username, password, Privilege.STUDENT);
							break;
						}
						default:
							break;
						}
						admin.addUser(newuser);
						JOptionPane.showMessageDialog(null, "Added user:\n " + newuser, "Success", 1);
					}
					Refresh();
				}
			}
		});

		privilege_field = new JComboBox();
		privilege_field.setBounds(144, 111, 101, 22);
		privilege_field.setModel(new DefaultComboBoxModel(new String[] { "ADMIN", "REGISTRAR", "TEACHER", "STUDENT" }));
		add(privilege_field);
		btnAdd.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnAdd.setBounds(33, 164, 89, 29);
		add(btnAdd);

		JButton btnDelete = new JButton("Delete");
		//delete button
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "Please Select a Row", "Error", 2);
					Refresh();
				} else {
					String username = table.getModel().getValueAt(row, 0).toString();

					admin.deleteUser(username);
					JOptionPane.showMessageDialog(null, "Deleted user: " + username, "Success", 1);

					Refresh();
				}
			}
		});
		btnDelete.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnDelete.setBounds(133, 164, 89, 29);
		add(btnDelete);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(288, 30, 465, 212);
		add(scrollPane);

		GetFromDB get = new GetFromDB();
		try {
			table = get.userTable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		get.closeConnection();
		table.getColumnModel().getColumn(0).setPreferredWidth(175);

		scrollPane.setViewportView(table);

	}
}
