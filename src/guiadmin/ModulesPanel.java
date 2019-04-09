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
import types.MyModule;
import types.MyModule.Period_of_teaching;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

/**
*
* @author vasile alexandru apetri
*/
public class ModulesPanel extends JPanel {
        //creating a ModulesPanel
	private JTable table;
	private JTextField fullname_field;
	private JComboBox thought_field;
	private JTextField lecturer_field;
	private JComboBox department_field;
	private JScrollPane scrollPane;

	public void Refresh() {
		if (scrollPane != null)
			remove(scrollPane);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(378, 23, 387, 216);
		add(scrollPane);
                //refresh function
		GetFromDB get = new GetFromDB();
		try {
			table = get.getTableForModules((String) department_field.getSelectedItem());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		get.closeConnection();
		scrollPane.setViewportView(table);

		fullname_field.setText("");
		thought_field.setSelectedIndex(0);
		lecturer_field.setText("");
	}

	/**
	 * Create the frame.
	 * 
	 * @param user
	 */
	public ModulesPanel(Admin admin) {
		setLayout(null);
                //creates fonts and layouts
		fullname_field = new JTextField();
		fullname_field.setBounds(157, 19, 172, 24);
		add(fullname_field);
		fullname_field.setColumns(10);

		thought_field = new JComboBox();
		thought_field.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		thought_field.setModel(new DefaultComboBoxModel(new String[] { "AUTUMN", "SPRING", "YEAR", "SUMMER" }));
		thought_field.setBounds(157, 80, 167, 23);
		add(thought_field);

		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String department = (String) department_field.getSelectedItem();
                                 
				remove(scrollPane);

				scrollPane = new JScrollPane();
				scrollPane.setBounds(378, 23, 387, 216);
				add(scrollPane);
                                //gets from Database
				GetFromDB get = new GetFromDB();
				try {
					table = get.getTableForModules(department);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				get.closeConnection();

				scrollPane.setViewportView(table);

			}
		};

		department_field = new JComboBox();
		department_field.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		// ArrayList depNames = GetFromDB.getDepartmentsName();
		String[] deps = null;
		GetFromDB get = new GetFromDB();

		try {
			deps = get.getListOfDepartments().split(",");
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		get.closeConnection();
		// String[] deps = { "placeholder" };
		department_field.setModel(new DefaultComboBoxModel(deps));
		department_field.setBounds(157, 124, 161, 23);
		department_field.addActionListener(al);
		add(department_field);
                
		scrollPane = new JScrollPane();
		scrollPane.setBounds(378, 23, 387, 216);
		add(scrollPane);

		get = new GetFromDB();
                //gets from database
		try {
			table = get.getTableForModules((String) department_field.getSelectedItem());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		get.closeConnection();

		scrollPane.setViewportView(table);
                //creating the styling
		JLabel lblFullName = new JLabel("Full Name");
		lblFullName.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblFullName.setBounds(42, 18, 86, 24);
		add(lblFullName);

		JLabel lblThought = new JLabel("Period of Teaching");
		lblThought.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblThought.setBounds(29, 79, 121, 24);
		add(lblThought);

		JLabel lblLecturer = new JLabel("Lecturer");
		lblLecturer.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblLecturer.setBounds(42, 44, 86, 24);
		add(lblLecturer);

		lecturer_field = new JTextField();
		lecturer_field.setColumns(10);
		lecturer_field.setBounds(157, 45, 172, 24);
		add(lecturer_field);

		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fullname = fullname_field.getText();
				String lecturer = lecturer_field.getText();
				if (!Hashing.isValid(fullname) || !Hashing.isValid(lecturer))
					JOptionPane.showMessageDialog(null, "Invalid module name or lecturer", "Error", 2);
				else {

					String thought = (String) thought_field.getSelectedItem();
					String department = (String) department_field.getSelectedItem();

					MyModule module = new MyModule(fullname, lecturer, Period_of_teaching.valueOf(thought));
					admin.addModule(module, department);
					JOptionPane.showMessageDialog(null, "Added module:\n " + module, "Success", 1);
				}
				Refresh();
			}
		});
		btnAdd.setBounds(61, 201, 99, 38);
		add(btnAdd);
                //delete button
		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "Please Select a Row", "Error", 2);
				} else {
					String module = table.getModel().getValueAt(row, 0).toString();

					admin.deleteModule(module);
					Refresh();
					JOptionPane.showMessageDialog(null, "Deleted module: " + module, "Success", 1);
				}
			}
		});
		btnDelete.setBounds(198, 201, 105, 38);
		add(btnDelete);

		JLabel lblTeachingDepartment = new JLabel("Teaching Department");
		lblTeachingDepartment.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblTeachingDepartment.setBounds(10, 123, 140, 24);
		add(lblTeachingDepartment);

	}
}
