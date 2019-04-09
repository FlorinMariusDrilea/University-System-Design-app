package guiteacher;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import connection.GetFromDB;
import guicore.BasicMessagePanel;
import people.Teacher;
import types.PeriodOfStudy;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *
 * @author vasile alexandru apetri
 */
public class AddGradesPanel extends JPanel {

	private JTable table;
	private JPanel cpsPanel;
	private JScrollPane scrollPane;
	private Teacher teacher;

	public void Refresh() {
		if (scrollPane != null) {
			remove(scrollPane);
			repaint();
			revalidate();
		}

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 68, 409, 344);
		add(scrollPane);
		GetFromDB get = new GetFromDB();
		try {
			table = get.specialTableForStudents();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		get.closeConnection();
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {

				TableModel model = table.getModel();
				int row = table.getSelectedRow();
				int regNr = Integer.valueOf(model.getValueAt(row, 0).toString());
				String progress = model.getValueAt(row, 1).toString();
				char label = model.getValueAt(row, 3).toString().charAt(0);

				boolean locked = false;
				if (progress == "Completed")
					locked = true;

				GetFromDB get = new GetFromDB();
				PeriodOfStudy ps = get.getPeriodOfStudy(regNr, label);
				get.closeConnection();
				remove(cpsPanel);
				repaint();
				cpsPanel = new UpdateGradesAuxPanel(ps, teacher, locked);
				cpsPanel.setBounds(442, 11, 350, 401);
				add(cpsPanel);

			}
		});
		scrollPane.setViewportView(table);

		if (cpsPanel != null)
			remove(cpsPanel);
		cpsPanel = new BasicMessagePanel();
		cpsPanel.setBounds(411, 11, 336, 401);
		add(cpsPanel);
		repaint();
		revalidate();
	}

	/**
	 * Create the frame.
	 * 
	 * @param teacher
	 */
	public AddGradesPanel(Teacher teacher) {
		setLayout(null);
		this.teacher = teacher;
		cpsPanel = new BasicMessagePanel();
		cpsPanel.setBounds(442, 11, 350, 401);
		add(cpsPanel);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 68, 409, 344);
		add(scrollPane);

		table = new JTable();
		GetFromDB get = new GetFromDB();
		try {
			table = get.specialTableForStudents();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		get.closeConnection();
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {

				TableModel model = table.getModel();
				int row = table.getSelectedRow();
				int regNr = Integer.valueOf(model.getValueAt(row, 0).toString());
				String progress = model.getValueAt(row, 1).toString();
				char label = model.getValueAt(row, 3).toString().charAt(0);

				boolean locked = false;
				if (progress == "Completed")
					locked = true;

				GetFromDB get = new GetFromDB();
				PeriodOfStudy ps = get.getPeriodOfStudy(regNr, label);
				get.closeConnection();
				remove(cpsPanel);
				repaint();
				cpsPanel = new UpdateGradesAuxPanel(ps, teacher, locked);
				cpsPanel.setBounds(442, 11, 350, 401);
				add(cpsPanel);

			}
		});

		scrollPane.setViewportView(table);

		JLabel lblAddGradesTo = new JLabel("Add grades to students");
		lblAddGradesTo.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblAddGradesTo.setBounds(27, 1, 167, 56);
		add(lblAddGradesTo);

		JButton btnNewButton = new JButton("Refresh");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Refresh();
			}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnNewButton.setBounds(252, 11, 105, 43);
		add(btnNewButton);
	}

}
