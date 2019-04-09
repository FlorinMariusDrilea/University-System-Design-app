package guicore;

import java.awt.EventQueue;

import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.mindrot.jbcrypt.BCrypt;

import auxclasses.Hashing;
import connection.GetFromDB;
import people.Admin;
import people.Registrar;
import people.Student;
import people.Teacher;
import people.User;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.HeadlessException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author vasile alexandru apetri
 */
public class LoginBox {
       //loginBox class
	private JFrame frame;
	private JTextField username_field;
	private JTextField password_field;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginBox window = new LoginBox();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void Refresh() {
		username_field.setText("");
		password_field.setText("");
	}

	/**
	 * Create the application.
	 */
	public LoginBox() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
                //we initialize the contents
		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setBounds(144, 35, 150, 47);
		lblWelcome.setFont(new Font("Times New Roman", Font.PLAIN, 40));
		frame.getContentPane().add(lblWelcome);

		username_field = new JTextField();
		username_field.setBounds(231, 118, 106, 31);
		frame.getContentPane().add(username_field);
		username_field.setColumns(10);

		password_field = new JPasswordField();
		password_field.setBounds(231, 160, 106, 25);
		frame.getContentPane().add(password_field);
		password_field.setColumns(10);

		JLabel lblPasword = new JLabel("password");
		lblPasword.setBounds(133, 164, 78, 21);
		lblPasword.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		frame.getContentPane().add(lblPasword);

		JLabel lblUsername = new JLabel("username");
		lblUsername.setBounds(133, 118, 83, 29);
		lblUsername.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		frame.getContentPane().add(lblUsername);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String username = username_field.getText();
				String passwordfield = password_field.getText();
				BCrypt encryption = new BCrypt();
                                 //error checking hte username and password
				if (!Hashing.isValid(username) || !Hashing.isValid(passwordfield)) {
					JOptionPane.showMessageDialog(null, "Invalid input for username or password", "Error", 2);
					Refresh();
				} else
					try {
						GetFromDB get = new GetFromDB();
						boolean ok = get.usernameAlreadyInUsers(username);
						get.closeConnection();
						if (!ok) {
							JOptionPane.showMessageDialog(null, "Wrong username or password", "Error", 2);
							Refresh();
						}

						else {

							String pass = null;
							get = new GetFromDB();
							try {
								pass = get.getPassword(username);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							get.closeConnection();
							User user = null;

							boolean decrypted = encryption.checkpw(passwordfield, pass);

							if (!decrypted)
								JOptionPane.showMessageDialog(null, "Wrong username or password", "Error", 2);
							else {
								get = new GetFromDB();
								String priv = null;
								//gets the privilage nad does the correct one
								try {
									priv = get.getPrivilege(username);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								get.closeConnection();

								switch (priv) {
								case "ADMIN": {
									user = new Admin(username, passwordfield);
									break;
								}
								case "REGISTRAR": {
									user = new Registrar(username, passwordfield);
									break;
								}
								case "TEACHER": {
									user = new Teacher(username, passwordfield);
									break;
								}
								case "STUDENT": {
									user = new Student(username, passwordfield);
									break;
								}
								default:
									break;
								}
								MainFrame mainframe = new MainFrame(user);
								mainframe.setVisible(true);
								frame.setVisible(false);

							}
						}
					} catch (HeadlessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			}
		});
		btnLogin.setBounds(251, 196, 68, 31);
		frame.getContentPane().add(btnLogin);
	}

	public void setVisible() {
		frame.setVisible(true);

	}

}
