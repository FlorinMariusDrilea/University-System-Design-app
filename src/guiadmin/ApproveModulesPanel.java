package guiadmin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import connection.GetFromDB;
import people.Admin;
import types.ApprovedModule;
import javax.swing.JTextField;
/**
*
* @author vasile alexandru apetri
*/
public class ApproveModulesPanel extends JPanel {

	/**
	 * 
	 */
	//The approved Modules panel class
	private static final long serialVersionUID = 3933351058010646917L;
	private JTable table;
	private JComboBox degree_field;
	private JComboBox module_field;
	private JRadioButton rdbtnOptional;
	private JRadioButton rdbtnCore;
	private JCheckBox chckbxDisertation;
	private JRadioButton radio1;
	private JScrollPane scrollPane;
	private ButtonGroup bg1;
	private JTextField totalcorecredit_field;
	private int maxCredit;

	private boolean checkCoreGrades(boolean post) {
		if (table.getRowCount() < 2)
			return false;

		int cMax = 120;
		int credits = 0;
		if (post)
			cMax = 180;
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, 3) == "Core")
				credits += (int) table.getValueAt(i, 4);
		}
		if (credits >= cMax)
			return true;
		return false;
	}

	public void Refresh() {
                //Refreshing the contents
		rdbtnCore.setSelected(true);
		chckbxDisertation.setSelected(false);

		if (scrollPane != null)
			remove(scrollPane);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(331, 55, 528, 251);
		add(scrollPane);
		GetFromDB get = new GetFromDB();

		try {
			table = get.getTableForApprovedModules((String) degree_field.getSelectedItem(),
					Integer.valueOf(bg1.getSelection().getActionCommand()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		get.closeConnection();
		get = new GetFromDB();
		boolean post = get.isPostgraduate((String) degree_field.getSelectedItem());
		if (post)
			maxCredit = 180;
		else
			maxCredit = 120;
		get.closeConnection();
		
		int credits = 0;		
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, 3) == "Core")
				credits += (int) table.getValueAt(i, 4);
		}
		totalcorecredit_field.setText(""+credits+"/"+maxCredit);
		table.getColumnModel().getColumn(0).setPreferredWidth(105);
		table.getColumnModel().getColumn(1).setPreferredWidth(110);
		table.getColumnModel().getColumn(2).setPreferredWidth(40);
		table.getColumnModel().getColumn(3).setPreferredWidth(35);

		scrollPane.setViewportView(table);
	}

	/**
	 * Create the panel.
	 * 
	 * @param user
	 */
	public ApproveModulesPanel(Admin admin) {
		setLayout(null);

	
                //Designing the Layout and fonts
		rdbtnCore = new JRadioButton("Core");
		rdbtnCore.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		rdbtnCore.setSelected(true);
		rdbtnCore.setBounds(135, 139, 54, 23);

		rdbtnOptional = new JRadioButton("Optional");
		rdbtnOptional.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		rdbtnOptional.setBounds(207, 139, 89, 23);

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnCore);
		bg.add(rdbtnOptional);
		add(rdbtnCore);
		add(rdbtnOptional);

		module_field = new JComboBox();
		module_field.setFont(new Font("Times New Roman", Font.PLAIN, 15));

		// ArrayList depNames = GetFromDB.getDepartmentsName();
		String[] mods = null;

		GetFromDB get = new GetFromDB();
		try {
			mods = get.getListOfModules().split(",");
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		get.closeConnection();
		module_field.setModel(new DefaultComboBoxModel(mods));

		module_field.setBounds(135, 63, 161, 23);

		add(module_field);

		JLabel lblLevelOfStudy = new JLabel("Level");
		lblLevelOfStudy.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblLevelOfStudy.setBounds(44, 101, 54, 24);
		add(lblLevelOfStudy);

		JLabel lblDegree = new JLabel("Degree");
		lblDegree.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblDegree.setBounds(44, 28, 54, 24);
		add(lblDegree);

		bg1 = new ButtonGroup();
                //implementing the action listener
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String degree = (String) degree_field.getSelectedItem();
				int level = Integer.valueOf(bg1.getSelection().getActionCommand());
				boolean ok = true;
				GetFromDB get = new GetFromDB();
				int maxlevel = get.getMaxLevelForDegree(degree);
				get.closeConnection();

				if (maxlevel < level)
					ok = false;


				remove(scrollPane);
				repaint();
				revalidate();

				if (ok) {
                                        //if correct
					scrollPane = new JScrollPane();
					scrollPane.setBounds(331, 55, 528, 251);
					add(scrollPane);

					get = new GetFromDB();
					try {
						table = get.getTableForApprovedModules(degree, level);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					get.closeConnection();
					table.getColumnModel().getColumn(0).setPreferredWidth(105);
					table.getColumnModel().getColumn(1).setPreferredWidth(110);
					table.getColumnModel().getColumn(2).setPreferredWidth(40);
					table.getColumnModel().getColumn(3).setPreferredWidth(35);
					scrollPane.setViewportView(table);

					get = new GetFromDB();
					boolean post = get.isPostgraduate((String) degree_field.getSelectedItem());
					if (post)
						maxCredit = 180;
					else
						maxCredit = 120;
					get.closeConnection();
					
					int credits = 0;		
					for (int i = 0; i < table.getRowCount(); i++) {
						if (table.getValueAt(i, 3) == "Core")
							credits += (int) table.getValueAt(i, 4);
					}
					totalcorecredit_field.setText(""+credits+"/"+maxCredit);
				} else
					JOptionPane.showMessageDialog(null,
							"Duration of the degree: " + degree + " doesnt take level: " + level, "Error", 2);

			}
		};

		radio1 = new JRadioButton("1");
		radio1.setSelected(true);
		radio1.setActionCommand("1");
		radio1.setBounds(135, 103, 47, 23);
		radio1.addActionListener(al);
		bg1.add(radio1);
		add(radio1);

		JRadioButton radio2 = new JRadioButton("2");
		radio2.setActionCommand("2");
		radio2.setBounds(181, 103, 44, 23);
		radio2.addActionListener(al);

		bg1.add(radio2);
		add(radio2);

		JRadioButton radio3 = new JRadioButton("3");
		radio3.setActionCommand("3");
		radio3.setBounds(228, 103, 49, 23);
		radio3.addActionListener(al);

		bg1.add(radio3);
		add(radio3);

		JRadioButton radio4 = new JRadioButton("4");
		radio4.setActionCommand("4");
		radio4.setBounds(279, 103, 46, 23);
		radio4.addActionListener(al);

		bg1.add(radio4);
		add(radio4);

		chckbxDisertation = new JCheckBox("Disertation");
		chckbxDisertation.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		chckbxDisertation.setBounds(25, 132, 89, 37);
		add(chckbxDisertation);

		degree_field = new JComboBox();
		degree_field.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		// ArrayList depNames = GetFromDB.getDepartmentsName();
		String[] deps = null;
		get = new GetFromDB();
		try {
			deps = get.getListOfDegrees().split(",");
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		get.closeConnection();

		// String[] deps = { "placeholder" };
		degree_field.setModel(new DefaultComboBoxModel(deps));
		degree_field.setBounds(135, 29, 161, 23);
		degree_field.addActionListener(al);

		add(degree_field);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(331, 55, 528, 251);
		add(scrollPane);

		table = new JTable();

		get = new GetFromDB();
		try {
			table = get.getTableForApprovedModules((String) degree_field.getSelectedItem(), 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		get.closeConnection();
                // setting width
		table.getColumnModel().getColumn(0).setPreferredWidth(105);
		table.getColumnModel().getColumn(1).setPreferredWidth(110);
		table.getColumnModel().getColumn(2).setPreferredWidth(40);
		table.getColumnModel().getColumn(3).setPreferredWidth(35);

		scrollPane.setViewportView(table);

		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		//action listener for Add
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Boolean core = rdbtnCore.isSelected();
				Boolean disertation = chckbxDisertation.isSelected();
				int level = Integer.valueOf(bg1.getSelection().getActionCommand());
				String degree = (String) degree_field.getSelectedItem();
				String module = (String) module_field.getSelectedItem();

				int credit = 0;
				GetFromDB get = new GetFromDB();
				boolean post = get.isPostgraduate(degree);
				get.closeConnection();

				if (disertation && post)
					credit = 60;
				else if (disertation)
					credit = 40;
				else if (level < 4)
					credit = 20;
				else
					credit = 15;

				Boolean ok = false;
				get = new GetFromDB();

				try {
					ok = get.moduleForDegreeAlreadyApprove(degree, module);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				get.closeConnection();

				ApprovedModule dm = new ApprovedModule(degree, module, core, level, credit);

				if (checkCoreGrades(post))
					JOptionPane.showMessageDialog(null, "To many core modules for level " + level, "Error", 2);
				else

				if (ok)
					JOptionPane.showMessageDialog(null,
							"Module: " + module + " for degree: " + degree + " is already approved!", "Error", 2);
				else {
					admin.approveModule(dm);
					JOptionPane.showMessageDialog(null, "Approved module:\n " + dm, "Success", 1);
				}

				Refresh();
			}
		});
		btnAdd.setBounds(44, 176, 99, 38);
		add(btnAdd);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "Please Select a Row", "Error", 2);
				} else {
					String degree = table.getModel().getValueAt(row, 0).toString();
					String module = table.getModel().getValueAt(row, 1).toString();

					admin.disapproveModule(degree, module);

					JOptionPane.showMessageDialog(null, "Disapproved module: " + module + " for " + "degree: " + degree,
							"Success", 1);
					Refresh();

				}
			}
		});

		btnDelete.setBounds(168, 176, 105, 38);
		add(btnDelete);

		JLabel lblModule = new JLabel("Module");
		lblModule.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblModule.setBounds(44, 68, 54, 24);
		add(lblModule);

		JLabel lblApprrrovModuleFor = new JLabel("Approve module for degree and level");
		lblApprrrovModuleFor.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblApprrrovModuleFor.setBounds(452, 11, 309, 37);
		add(lblApprrrovModuleFor);

		JLabel lblTotalCoreCredit = new JLabel("Total Core Credit");
		lblTotalCoreCredit.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblTotalCoreCredit.setBounds(376, 328, 113, 30);
		add(lblTotalCoreCredit);

		totalcorecredit_field = new JTextField();
		totalcorecredit_field.setBounds(487, 331, 99, 26);
		add(totalcorecredit_field);
		totalcorecredit_field.setColumns(10);
		//checking if postGrad or undergrand for 120 or 180 credits
		get = new GetFromDB();
		boolean post = get.isPostgraduate((String) degree_field.getSelectedItem());
		if (post)
			maxCredit = 180;
		else
			maxCredit = 120;
		get.closeConnection();
		
		int credits = 0;		
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, 3) == "Core")
				credits += (int) table.getValueAt(i, 4);
		}
		totalcorecredit_field.setText(""+credits+"/"+maxCredit);
		
	}
}
