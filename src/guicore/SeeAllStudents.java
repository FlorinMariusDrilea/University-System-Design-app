package guicore;

import javax.swing.JPanel;

import java.awt.Font;

import javax.swing.JButton;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import connection.DeleteDB;
import connection.GetFromDB;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *
 * @author vasile alexandru apetri
 */
public class SeeAllStudents extends JPanel {

	private JTable table;
	private JPanel cpsPanel;
	private JScrollPane scrollPane;

	// private JLabel[] isCore;

	public void Refresh() {
		if (scrollPane != null)
			remove(scrollPane);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 22, 806, 322);
		add(scrollPane);

		table = new JTable();
		GetFromDB get = new GetFromDB();
		try {
			table.setModel(get.createBasicTable("students"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		get.closeConnection();
		scrollPane.setViewportView(table);

	}

	/**
	 * Create the frame.
	 * 
	 * @param teacher
	 */
	public SeeAllStudents() {
		setLayout(null);
                //showing ALL Current students
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 22, 806, 322);
		add(scrollPane);

		table = new JTable();
		GetFromDB get = new GetFromDB();

		try {
			table.setModel(get.createBasicTable("students"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		get.closeConnection();
		scrollPane.setViewportView(table);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		//delete button
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "Please Select a Row", "Error", 2);
				} else {
					String cell = table.getModel().getValueAt(row, 0).toString();
					int id = Integer.valueOf(cell);
					try {
						DeleteDB delete = new DeleteDB();
						delete.deleteMGS(id);
						delete.deletePSs(id);
						delete.deleteStudent(id);
						delete.closeConnection();
						Refresh();
						JOptionPane.showMessageDialog(null, "Deleted ", "Success", 1);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnDelete.setBounds(308, 355, 195, 43);
		add(btnDelete);

	}

}
