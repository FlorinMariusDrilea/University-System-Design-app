package guiteacher;

import java.awt.Font;

import java.sql.SQLException;

import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import connection.GetFromDB;

import javax.swing.JTable;
import javax.swing.JScrollPane;

/**
 *
 * @author vasile alexandru apetri
 */
public class RecordsPanel extends JPanel {

	private JTable table;

	/**
	 * Create the panel.
	 */
	public RecordsPanel(int regNr) {
		setLayout(null);

		JLabel lblCurentPeriodOf = new JLabel("Student History");
		lblCurentPeriodOf.setBounds(120, 5, 131, 24);
		lblCurentPeriodOf.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(lblCurentPeriodOf);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 40, 345, 213);
		add(scrollPane);

		table = new JTable();
		GetFromDB get = new GetFromDB();
		try {
			table = get.studentHistory(regNr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double og = get.studentDegreeGrade(regNr);

		get.closeConnection();

		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("Course in progress");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel.setBounds(77, 282, 278, 53);
		add(lblNewLabel);

		if (og != 0) {
			lblNewLabel.setText("Course finished with grade: " + og);
		}

	}
}
