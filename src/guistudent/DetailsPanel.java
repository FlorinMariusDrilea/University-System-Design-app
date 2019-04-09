package guistudent;

import java.awt.Font;

import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import connection.GetFromDB;
import types.MyStudent;
import javax.swing.JTable;
import javax.swing.JScrollPane;

/**
 *
 * @author vasile alexandru apetri
 */
public class DetailsPanel extends JPanel {
	private JTextField title_field;
	private JTextField regNr_field;
	private int regNr;
	private JTable table;
	private DefaultTableModel model;
	private JTextField surname_field;
	private JTextField forename_field;
	private JTextField degree_field;
	private JTextField email_field;
	private JTextField pt_field;

	/**
	 * Create the panel.
	 */
	public DetailsPanel(int regNr) {
		this.regNr = regNr;
		MyStudent student = null;

		GetFromDB get = new GetFromDB();
		try {
			student = get.getStudent(regNr);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		get.closeConnection();

		setLayout(null);

		JLabel lblLabel = new JLabel("Registration Nr.");
		lblLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblLabel.setBounds(36, 44, 94, 33);
		add(lblLabel);

		JLabel lblLevel = new JLabel("Title");
		lblLevel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblLevel.setBounds(36, 75, 40, 33);
		add(lblLevel);

		JLabel lblStartDate = new JLabel("Surname");
		lblStartDate.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblStartDate.setBounds(36, 103, 62, 33);
		add(lblStartDate);

		JLabel lblEndDate = new JLabel("Forename");
		lblEndDate.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblEndDate.setBounds(36, 135, 62, 33);
		add(lblEndDate);

		title_field = new JTextField();
		title_field.setBounds(108, 82, 47, 20);
		add(title_field);
		title_field.setColumns(10);

		regNr_field = new JTextField();
		regNr_field.setColumns(10);
		regNr_field.setBounds(145, 51, 94, 20);
		add(regNr_field);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(316, 12, 482, 187);
		add(scrollPane);

		get = new GetFromDB();

		table = new JTable();
		try {
			table = get.studentHistory(regNr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		get.closeConnection();

		scrollPane.setViewportView(table);

		surname_field = new JTextField();
		surname_field.setText("\u0000");
		surname_field.setColumns(10);
		surname_field.setBounds(108, 110, 84, 20);
		add(surname_field);

		forename_field = new JTextField();
		forename_field.setText("\u0000");
		forename_field.setColumns(10);
		forename_field.setBounds(108, 142, 84, 20);
		add(forename_field);

		JLabel lblDegree = new JLabel("Degree");
		lblDegree.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblDegree.setBounds(36, 166, 62, 33);
		add(lblDegree);

		degree_field = new JTextField();
		degree_field.setText("\u0000");
		degree_field.setColumns(10);
		degree_field.setBounds(92, 173, 184, 20);
		add(degree_field);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblEmail.setBounds(36, 193, 62, 33);
		add(lblEmail);

		email_field = new JTextField();
		email_field.setText("\u0000");
		email_field.setColumns(10);
		email_field.setBounds(91, 204, 190, 20);
		add(email_field);

		JLabel lblPersonalTutor = new JLabel("Personal Tutor");
		lblPersonalTutor.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblPersonalTutor.setBounds(36, 227, 88, 33);
		add(lblPersonalTutor);

		pt_field = new JTextField();
		pt_field.setText("\u0000");
		pt_field.setColumns(10);
		pt_field.setBounds(145, 235, 131, 20);
		add(pt_field);

		regNr_field.setText("" + regNr);
		title_field.setText(student.getTitle());
		surname_field.setText(student.getSurname());
		forename_field.setText(student.getForename());
		email_field.setText(student.getEmail());
		degree_field.setText(student.getDegree_name());
		pt_field.setText(student.getPersonalTutor());

		JLabel lblNewLabel = new JLabel("Course in progress");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel.setBounds(326, 203, 332, 52);
		add(lblNewLabel);

		get = new GetFromDB();

		double og = get.studentDegreeGrade(regNr);

		get.closeConnection();

		if (og != 0) {
			lblNewLabel.setText("Course finished with grade: " + og);
		}
	}
}
