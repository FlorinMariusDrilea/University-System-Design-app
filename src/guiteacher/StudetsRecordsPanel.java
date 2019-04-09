package guiteacher;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import connection.GetFromDB;
import guicore.BasicMessagePanel;
import people.Teacher;

import javax.swing.JScrollPane;

/**
 *
 * @author vasile alexandru apetri
 */
public class StudetsRecordsPanel extends JPanel {

	private JTable table;
	private JPanel cpsPanel;

	/**
	 * Create the frame.
	 * 
	 * @param teacher
	 */
	public StudetsRecordsPanel(Teacher teacher) {
		setLayout(null);

		cpsPanel = new BasicMessagePanel();
		cpsPanel.setBounds(405, 11, 362, 401);
		add(cpsPanel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 63, 375, 349);
		add(scrollPane);

		table = new JTable();
		GetFromDB get = new GetFromDB();
		try {
			table = get.tableWithRegNRs();
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

				remove(cpsPanel);
				repaint();
				cpsPanel = new RecordsPanel(regNr);
				cpsPanel.setBounds(405, 11, 362, 401);
				add(cpsPanel);

			}
		});

		scrollPane.setViewportView(table);

		JLabel label = new JLabel("See Students History");
		label.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		label.setBounds(135, 11, 189, 41);
		add(label);

	}

}
