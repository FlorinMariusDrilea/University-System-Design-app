package guiregistrar;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import connection.GetFromDB;
import connection.InsertInDB;
import types.PeriodOfStudy;
import javax.swing.JTable;
import javax.swing.JScrollPane;

/**
 *
 * @author vasile alexandru apetri
 */
public class CurrentPSPanel extends JPanel {
	private JTextField label_field;
	private JTextField level_field;
	private JTextField start_date_field;
	private JTextField end_date_field;
	private JTextField credits_field;
	private int level;
	private int credits;
	private int creditTotal;
	private JTable table;
	private DefaultTableModel model;
	private int regNr;
	private char label;

	private void updateCredits(DefaultTableModel model) {
		credits = 0;
		for (int i = 0; i < model.getRowCount(); i++) {
			if ((Boolean) model.getValueAt(i, 4) == true) {
				credits += Integer.valueOf(model.getValueAt(i, 2).toString());
			}
		}
		credits_field.setText(credits + "/" + creditTotal);

	}

	/**
	 * Create the panel.
	 * 
	 * @param locked
	 * @param mouseAdapter
	 */
	public CurrentPSPanel(PeriodOfStudy ps, boolean locked) {
		setLayout(null);

		JLabel lblLabel = new JLabel("Label");
		lblLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblLabel.setBounds(36, 44, 40, 33);
		add(lblLabel);

		JLabel lblCurentPeriodOf = new JLabel("Curent Period of Study");
		lblCurentPeriodOf.setBounds(74, 5, 180, 24);
		lblCurentPeriodOf.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(lblCurentPeriodOf);

		JLabel lblLevel = new JLabel("Level");
		lblLevel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblLevel.setBounds(156, 44, 40, 33);
		add(lblLevel);

		JLabel lblStartDate = new JLabel("Start Date");
		lblStartDate.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblStartDate.setBounds(14, 82, 62, 33);
		add(lblStartDate);

		JLabel lblEndDate = new JLabel("End Date");
		lblEndDate.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblEndDate.setBounds(166, 82, 62, 33);
		add(lblEndDate);

		label_field = new JTextField();
		label_field.setBounds(86, 51, 16, 20);
		add(label_field);
		label_field.setColumns(10);

		level_field = new JTextField();
		level_field.setColumns(10);
		level_field.setBounds(207, 51, 16, 20);
		add(level_field);

		start_date_field = new JTextField();
		start_date_field.setColumns(10);
		start_date_field.setBounds(86, 88, 71, 20);
		add(start_date_field);

		end_date_field = new JTextField();
		end_date_field.setColumns(10);
		end_date_field.setBounds(233, 89, 71, 20);
		add(end_date_field);

		credits_field = new JTextField();
		credits_field.setBounds(74, 381, 62, 20);
		credits_field.setText("0/0");
		add(credits_field);
		credits_field.setColumns(10);

		JLabel lblModules = new JLabel("Modules");
		lblModules.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblModules.setBounds(120, 122, 76, 33);
		add(lblModules);

		regNr = ps.getRegNr();
		label = ps.getLabel();

		JLabel lbllocked = null;
		JButton btnComplete = null;

		if (locked) {
			lbllocked = new JLabel("Locked");
			lbllocked.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			lbllocked.setBounds(146, 379, 166, 23);
			lbllocked.setText("Modules are locked");
			add(lbllocked);
		} else {

			btnComplete = new JButton("Lock modules");
			btnComplete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if (credits != creditTotal)
						JOptionPane.showMessageDialog(null, "Credit doenst match", "Error", 2);
					else {
						InsertInDB insert = new InsertInDB();
						for (int i = 0; i < model.getRowCount(); i++)

							if (model.getValueAt(i, 3).toString() == "Optional" && (Boolean) model.getValueAt(i, 4))

								try {
									insert.insertInModule_Grades(regNr, label, model.getValueAt(i, 1).toString());
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
						try {
							insert.confirmedModules(regNr, label);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						insert.closeConnection();
						JOptionPane.showMessageDialog(null, "Locked modules for this period of study", "Success", 1);
						repaint();
						revalidate();

					}

				}
			});
			btnComplete.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			btnComplete.setBounds(175, 379, 129, 23);
			add(btnComplete);
		}

		JLabel lblCredits = new JLabel("Credits");
		lblCredits.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblCredits.setBounds(14, 381, 52, 18);
		add(lblCredits);

		level = ps.getLevel();
		label_field.setText(Character.toString(ps.getLabel()));
		level_field.setText(Integer.toString(level));
		start_date_field.setText(ps.getStartDate().dateInString());
		end_date_field.setText(ps.getEndDate().dateInString());

		String degree_name = "";
		GetFromDB get = new GetFromDB();
		try {
			degree_name = get.getDegreeName(ps.getRegNr());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		get.closeConnection();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 166, 282, 187);
		add(scrollPane);

		get = new GetFromDB();

		try {
			table = get.modulesForCPS(degree_name, level);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		get.closeConnection();

		scrollPane.setViewportView(table);
		model = (DefaultTableModel) table.getModel();

		get = new GetFromDB();

		boolean post = get.isPostgraduate(table.getValueAt(1, 0).toString());

		get.closeConnection();

		if (post)
			creditTotal = 180;
		else
			creditTotal = 120;

		get = new GetFromDB();
		for (int i = 0; i < model.getRowCount(); i++) {
			boolean min = false;
			try {
				min = get.moduleAlreadyIn(regNr, label, model.getValueAt(i, 1).toString());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (min)
				model.setValueAt(Boolean.TRUE, i, 4);
			else
				model.setValueAt(Boolean.FALSE, i, 4);

		}
		get.closeConnection();

		updateCredits(model);

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				updateCredits(model);

			}
		});

	}
}
