package guiadmin;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import auxclasses.Hashing;
import connection.GetFromDB;
import people.Admin;
import types.Degree;
import types.Department;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
/**
*
* @author vasile alexandru apetri
*/
public class DegreesPanel extends JPanel {

	private JTable table;
	private JTextField fullname_field;
	private JComboBox departments_field;
	private JCheckBox chckbxYearInInd;
	private JRadioButton radio1;
        private JScrollPane scrollPane;

	public void Refresh() {
		if (scrollPane != null)
			remove(scrollPane);
		//refreshing the table
		scrollPane = new JScrollPane();
		scrollPane.setBounds(300, 24, 497, 250);
		add(scrollPane);

		table = new JTable();
		GetFromDB get = new GetFromDB();
		try {
			table = get.getTableForDegree((String )departments_field.getSelectedItem());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		get.closeConnection();
		
		scrollPane.setViewportView(table);	
		
		fullname_field.setText("");
		chckbxYearInInd.setSelected(false);
		radio1.setSelected(true);
	}

	/**
	 * Create the frame.
	 * 
	 * @param user
	 */
	public DegreesPanel(Admin admin) {
                //setting it up
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();

		setSize(836, 325);
		setLocation(screenSize.width / 4, screenSize.height / 4);

		setLayout(null);
		
		fullname_field = new JTextField();
		fullname_field.setBounds(102, 24, 188, 24);
		add(fullname_field);
		fullname_field.setColumns(10);
                //right into the action!!
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String department = (String) departments_field.getSelectedItem();

				remove(scrollPane);

				scrollPane = new JScrollPane();
				scrollPane.setBounds(300, 24, 497, 250);
				add(scrollPane);

				GetFromDB get = new GetFromDB();
				try {
					table = get.getTableForDegree(department);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				get.closeConnection();
		
				scrollPane.setViewportView(table);

			}
		};
		//setting up the departments
		departments_field = new JComboBox();
		departments_field.setFont(new Font("Times New Roman", Font.PLAIN, 15));
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
		departments_field.setModel(new DefaultComboBoxModel(deps));
		departments_field.setBounds(123, 110, 161, 23);
		departments_field.addActionListener(al);
		add(departments_field);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(300, 24, 497, 250);
		add(scrollPane);

		get = new GetFromDB();
		try {
			table = get.getTableForDegree((String )departments_field.getSelectedItem());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		get.closeConnection();
		
		
		scrollPane.setViewportView(table);
                //setting up the label fonts and bounds
		JLabel lblDuration = new JLabel("Duration");
		lblDuration.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblDuration.setBounds(21, 59, 86, 39);
		add(lblDuration);

		JLabel lblDepartment = new JLabel("Department");
		lblDepartment.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblDepartment.setBounds(21, 109, 121, 24);
		add(lblDepartment);

		chckbxYearInInd = new JCheckBox("Year in Industry");
		chckbxYearInInd.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		chckbxYearInInd.setBounds(148, 169, 142, 39);
		add(chckbxYearInInd);

		JLabel lblFullName = new JLabel("Full Name");
		lblFullName.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblFullName.setBounds(21, 24, 86, 24);
		add(lblFullName);
                //setting up the radiobuttons
		ButtonGroup bg = new ButtonGroup();
                 
		radio1 = new JRadioButton("1");
		radio1.setSelected(true);
		radio1.setActionCommand("1");
		radio1.setBounds(112, 55, 47, 23);
		bg.add(radio1);
		add(radio1);

		JRadioButton radio2 = new JRadioButton("2");
		radio2.setActionCommand("2");
		radio2.setBounds(161, 55, 44, 23);
		bg.add(radio2);
		add(radio2);

		JRadioButton radio3 = new JRadioButton("3");
		radio3.setActionCommand("3");
		radio3.setBounds(210, 55, 49, 23);
		bg.add(radio3);
		add(radio3);

		JRadioButton radio4 = new JRadioButton("4");
		radio4.setActionCommand("4");
		radio4.setBounds(113, 81, 46, 23);
		bg.add(radio4);
		add(radio4);

		JRadioButton radio5 = new JRadioButton("5");
		radio5.setActionCommand("5");
		radio5.setBounds(161, 81, 44, 23);
		bg.add(radio5);
		add(radio5);

		JRadioButton radio6 = new JRadioButton("6");
		radio6.setActionCommand("6");
		radio6.setBounds(210, 80, 49, 23);
		bg.add(radio6);
		add(radio6);

		ButtonGroup bg2 = new ButtonGroup();

		JRadioButton under = new JRadioButton("Undergraduate");
		under.setSelected(true);
		under.setActionCommand("Undergraduate");
		under.setBounds(31, 140, 113, 23);
		bg2.add(under);
		add(under);

		JRadioButton post = new JRadioButton("Postgraduate");
		post.setSelected(true);
		post.setActionCommand("Postgraduate");
		post.setBounds(31, 178, 96, 23);
		bg2.add(post);
		add(post);
		
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                                //performs check
				String fullname = fullname_field.getText();
				if (!Hashing.isValid(fullname))
					JOptionPane.showMessageDialog(null, "Invalid degree name", "Error", 2);
				else {
			        //if the check is passed
				String lead_depart = (String) departments_field.getSelectedItem();
				Department dep = new Department(lead_depart);
				Boolean year = chckbxYearInInd.isSelected();
				int duration = Integer.valueOf(bg.getSelection().getActionCommand());

				String undergraduate = bg2.getSelection().getActionCommand();
				boolean under = false;
				if(undergraduate=="Undergraduate")
					under=true;

				Degree degree = new Degree(fullname, dep, year, duration, under);
				admin.addDegree(degree , dep);
				JOptionPane.showMessageDialog(null, "Added degree:\n" + degree, "Success", 1);}
				Refresh();

			}
		});
                //delete Button
		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "Please Select a Row", "Error", 2);
				} else {
					String degree = table.getModel().getValueAt(row, 0).toString();

					admin.deleteDegree(degree);
					Refresh();
					JOptionPane.showMessageDialog(null, "Deleted degree: " + degree, "Success", 1);

				}
			}
		});
		btnDelete.setBounds(150, 241, 96, 33);
		add(btnDelete);
		btnAdd.setBounds(38, 241, 86, 33);
		add(btnAdd);

	}
}
