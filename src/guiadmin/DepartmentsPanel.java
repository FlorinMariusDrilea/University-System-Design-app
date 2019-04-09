package guiadmin;


import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import auxclasses.Hashing;
import connection.GetFromDB;
import people.Admin;
import types.Department;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
/**
*
* @author vasile alexandru apetri
*/
public class DepartmentsPanel extends JPanel {
        //departments Panel
	private JPanel contentPane;
	private JTable table;
	private JTextField fullname_field;
	private JScrollPane scrollPane;

	public void Refresh() {
		//refresh function
		if (scrollPane != null)
			remove(scrollPane);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(359, 32, 485, 259);
		add(scrollPane);

		table = new JTable();
		GetFromDB get = new GetFromDB();
		try {
			table.setModel(get.createBasicTable("departments"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		get.closeConnection();
		table.getColumnModel().getColumn(0).setPreferredWidth(175);

		scrollPane.setViewportView(table);

		fullname_field.setText("");
	}

	/**
	 * Create the frame.
	 * 
	 * @param admin
	 */
	public DepartmentsPanel(Admin admin) {
		setLayout(null);
                //changing the styling
		fullname_field = new JTextField();
		fullname_field.setBounds(140, 49, 157, 28);
		add(fullname_field);
		fullname_field.setColumns(10);

		JLabel lblUsername = new JLabel("Full Name");
		lblUsername.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblUsername.setBounds(37, 45, 93, 30);
		add(lblUsername);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		//implements delete
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "Please Select a Row", "Error", 2);
				} else {
					String department = table.getModel().getValueAt(row, 0).toString();

					admin.deleteDepartment(department);
					Refresh();
					JOptionPane.showMessageDialog(null, "Deleted: " + department, "Success", 1);

				}
			}
		});

		btnDelete.setBounds(171, 148, 109, 30);
		add(btnDelete);

		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                                //performs adding a department
				String department = fullname_field.getText();
				if (!Hashing.isValid(department))
					JOptionPane.showMessageDialog(null, "Invalid department name", "Error", 2);
				else {
					Department newdepart = new Department(department);
					admin.addDepartment(newdepart);
					JOptionPane.showMessageDialog(null, "Added department:\n " + newdepart, "Success", 1);
				}
				Refresh();

			}
		});
		btnAdd.setBounds(57, 148, 104, 30);
		add(btnAdd);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(359, 32, 485, 259);
		add(scrollPane);

		table = new JTable();
		GetFromDB get = new GetFromDB();
                //getting the table
		try {
			table.setModel(get.createBasicTable("departments"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		get.closeConnection();
		table.getColumnModel().getColumn(0).setPreferredWidth(175);

		scrollPane.setViewportView(table);
	}
}
