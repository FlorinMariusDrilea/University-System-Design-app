package guiregistrar;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import auxclasses.Hashing;
import auxclasses.MyDate;
import connection.GetFromDB;
import people.Registrar;

import types.MyStudent;
import types.PeriodOfStudy;

import java.awt.event.ActionListener;
import java.sql.SQLException;

import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import java.text.ParseException;

/**
 *
 * @author vasile alexandru apetri
 */
public class NewStudentsPanel extends JPanel {

	private JTextField surname_field;
	private JTextField forename_field;
	private JTextField tutor_field;
	private JComboBox degreeComboBox;
	private JComboBox titleComboBox;
	private JTextField sday;
	private JTextField eday;
	private JTextField smonth;
	private JTextField emonth;
	private JTextField syear;
	private JTextField eyear;

	public void Refresh() {
		surname_field.setText("");
		forename_field.setText("");
		tutor_field.setText("");
		degreeComboBox.setSelectedIndex(0);
		titleComboBox.setSelectedIndex(0);
		sday.setText("01");
		eday.setText("01");
		smonth.setText("01");
		emonth.setText("01");
		syear.setText("2018");
		eyear.setText("2019");
	}

	/**
	 * Create the frame.
	 * 
	 * @param registrar
	 */
	public NewStudentsPanel(Registrar registrar) {
		setLayout(null);

		JLabel lblUsers = new JLabel("New Student");
		lblUsers.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		lblUsers.setBounds(42, 11, 216, 52);
		add(lblUsers);

		JLabel lblTitle = new JLabel("title");
		lblTitle.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblTitle.setBounds(86, 92, 66, 18);
		add(lblTitle);

		titleComboBox = new JComboBox();
		titleComboBox.setModel(new DefaultComboBoxModel(new String[] { "Mr", "Ms", "Dr" }));
		titleComboBox.setBounds(128, 91, 55, 22);
		add(titleComboBox);

		degreeComboBox = new JComboBox();
		degreeComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		// ArrayList depNames = GetFromDB.getDepartmentsName();
		String[] degrees = null;
		GetFromDB get = new GetFromDB();
		try {
			degrees = get.getListOfDegrees().split(",");
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		get.closeConnection();
		// String[] deps = { "placeholder" };
		degreeComboBox.setModel(new DefaultComboBoxModel(degrees));
		degreeComboBox.setBounds(125, 204, 161, 23);
		add(degreeComboBox);

		JLabel lblFamilyName = new JLabel("family name");
		lblFamilyName.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblFamilyName.setBounds(31, 121, 86, 18);
		add(lblFamilyName);

		surname_field = new JTextField();
		surname_field.setBounds(128, 121, 92, 20);
		add(surname_field);
		surname_field.setColumns(10);

		JLabel lblForename = new JLabel("forename");
		lblForename.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblForename.setBounds(31, 150, 79, 18);
		add(lblForename);

		forename_field = new JTextField();
		forename_field.setBounds(128, 152, 92, 20);
		add(forename_field);
		forename_field.setColumns(10);

		JLabel lblPersonalTutor = new JLabel("personal tutor");
		lblPersonalTutor.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblPersonalTutor.setBounds(31, 179, 86, 18);
		add(lblPersonalTutor);

		tutor_field = new JTextField();
		tutor_field.setColumns(10);
		tutor_field.setBounds(128, 179, 92, 20);
		add(tutor_field);

		JLabel lblDegree = new JLabel("Degree");
		lblDegree.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblDegree.setBounds(32, 206, 86, 18);
		add(lblDegree);

		JLabel lblFirstPeriodOf = new JLabel("First Period of Study");
		lblFirstPeriodOf.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		lblFirstPeriodOf.setBounds(278, 11, 278, 52);
		add(lblFirstPeriodOf);

		JLabel lblSteartDate = new JLabel("start date");
		lblSteartDate.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblSteartDate.setBounds(288, 75, 73, 22);
		add(lblSteartDate);

		JLabel lblEndDate = new JLabel("end date");
		lblEndDate.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblEndDate.setBounds(288, 134, 73, 22);
		add(lblEndDate);

		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String surname = surname_field.getText();// family name
				String forename = forename_field.getText();
				String title = (String) titleComboBox.getSelectedItem();
				String tutor = tutor_field.getText();
				String degree_name = (String) degreeComboBox.getSelectedItem();

				MyStudent student = new MyStudent(title, surname, forename, degree_name, tutor);
				String password = Hashing.generateRandomPW();
				String user = registrar.addStudent(student, password);

				MyDate start_date = new MyDate(sday.getText(), smonth.getText(), syear.getText());
				MyDate end_date = new MyDate(eday.getText(), emonth.getText(), eyear.getText());

				PeriodOfStudy firstPs = new PeriodOfStudy('A', start_date, end_date, 1, student.getRegNr());
				try {
					registrar.addPeriodOfStudy(firstPs);
				} catch (ParseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				JOptionPane.showMessageDialog(null,
						"Added student:\n " + student + "\n" + "Added first period of study:\n " + firstPs, "Success",
						1);
				JOptionPane.showMessageDialog(null, "Added new user: " + user + " password: " + password, "Success", 1);
				Refresh();
			}

		});

		btnAdd.setBounds(317, 196, 92, 34);
		add(btnAdd);

		JLabel lblDay = new JLabel("DD");
		lblDay.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblDay.setBounds(290, 103, 32, 21);
		add(lblDay);

		JLabel lblMm = new JLabel("MM");
		lblMm.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblMm.setBounds(370, 104, 32, 18);
		add(lblMm);

		JLabel lblYyyy = new JLabel("YYYY");
		lblYyyy.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblYyyy.setBounds(453, 104, 55, 18);
		add(lblYyyy);

		JLabel label = new JLabel("DD");
		label.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		label.setBounds(288, 165, 32, 21);
		add(label);

		JLabel label_1 = new JLabel("MM");
		label_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		label_1.setBounds(370, 165, 32, 18);
		add(label_1);

		JLabel label_2 = new JLabel("YYYY");
		label_2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		label_2.setBounds(453, 165, 55, 18);
		add(label_2);

		sday = new JTextField();
		sday.setLocation(317, 104);
		sday.setSize(43, 20);
		sday.setBackground(Color.WHITE);
		sday.setText("01");
		add(sday);

		smonth = new JTextField();
		smonth.setBackground(Color.WHITE);
		smonth.setBounds(400, 104, 43, 20);
		smonth.setText("01");
		add(smonth);

		syear = new JTextField();
		syear.setBackground(Color.WHITE);
		syear.setBounds(513, 104, 55, 20);
		syear.setText("2018");
		add(syear);

		eday = new JTextField();
		eday.setBackground(Color.WHITE);
		eday.setBounds(317, 166, 43, 20);
		eday.setText("01");
		add(eday);

		emonth = new JTextField();
		emonth.setBackground(Color.WHITE);
		emonth.setBounds(400, 163, 43, 20);
		emonth.setText("01");
		add(emonth);

		eyear = new JTextField();
		eyear.setBackground(Color.WHITE);
		eyear.setBounds(513, 166, 55, 20);
		eyear.setText("2019");
		add(eyear);
	}

}
