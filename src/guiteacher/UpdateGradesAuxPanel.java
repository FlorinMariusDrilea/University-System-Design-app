package guiteacher;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import auxclasses.MyDate;
import connection.GetFromDB;

import people.Teacher;
import types.PeriodOfStudy;
import javax.swing.JTable;
import javax.swing.JScrollPane;

/**
 *
 * @author vasile alexandru apetri
 */
public class UpdateGradesAuxPanel extends JPanel {
	private JTextField label_field;
	private JTextField level_field;
	private JTextField start_date_field;
	private JTextField end_date_field;
	private JTextField ograde_field;
	private int level;
	private int passGrade;
	private char label;
	private int regNr;
	private double overallGrade;
	private JTable table;
	private JScrollPane scrollPane;
	private String degreename;
	private boolean post;

	private void calculateOverallGrade() {
		int totalcredit = 120;

		if (post) {
			totalcredit = 180;
		}

		double averagecredit = 1;
		if (table.getRowCount() != 0)
			averagecredit = totalcredit / (double) table.getRowCount();

		overallGrade = 0;
		Integer firstgrade;
		Integer resitgrade;
		Integer credit;
		for (int i = 0; i < table.getRowCount(); i++) {
			credit = Integer.valueOf(table.getValueAt(i, 1).toString());
			firstgrade = Integer.valueOf(table.getValueAt(i, 2).toString());
			resitgrade = Integer.valueOf(table.getValueAt(i, 3).toString());
			if (firstgrade < passGrade && resitgrade != 0)
				overallGrade += (resitgrade * credit);
			else {
				overallGrade += (firstgrade * credit);
			}

		}
		if (overallGrade != 0)
			overallGrade = overallGrade / (double) table.getRowCount() / averagecredit;
		ograde_field.setText((new DecimalFormat("##.##").format(overallGrade)));

	}

	/**
	 * Create the panel.
	 * 
	 * @param locked
	 */
	public UpdateGradesAuxPanel(PeriodOfStudy ps, Teacher teacher, boolean locked) {
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
		label_field.setBounds(86, 51, 47, 20);
		add(label_field);
		label_field.setColumns(10);

		level_field = new JTextField();
		level_field.setColumns(10);
		level_field.setBounds(207, 51, 47, 20);
		add(level_field);

		start_date_field = new JTextField();
		start_date_field.setColumns(10);
		start_date_field.setBounds(86, 88, 71, 20);
		add(start_date_field);

		end_date_field = new JTextField();
		end_date_field.setColumns(10);
		end_date_field.setBounds(233, 89, 71, 20);
		add(end_date_field);

		ograde_field = new JTextField();
		ograde_field.setBounds(46, 356, 62, 20);
		ograde_field.setText("0/0");
		add(ograde_field);
		ograde_field.setColumns(10);

		level = ps.getLevel();

		regNr = ps.getRegNr();
		label = ps.getLabel();

		GetFromDB get = new GetFromDB();
		try {
			degreename = get.getDegreeName(regNr);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		post = get.isPostgraduate(degreename);
		get.closeConnection();

		if (level > 3 || post)
			passGrade = 50;
		else
			passGrade = 40;

		JLabel lbllocked = null;
		JButton btnComplete = null;

		if (locked == false) {
			lbllocked = new JLabel("Locked");
			lbllocked.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			lbllocked.setBounds(118, 346, 191, 35);
			lbllocked.setText("Modules not locked yet");
			add(lbllocked);
		} else {

			btnComplete = new JButton("Update");
			btnComplete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					for (int i = 0; i < table.getRowCount(); i++) {
						String module = table.getValueAt(i, 0).toString();
						String fg = table.getValueAt(i, 2).toString();
						String rg = table.getValueAt(i, 3).toString();
						int firstgrade = Integer.valueOf(fg);
						int resitgrade = Integer.valueOf(rg);
						if (firstgrade != 0) {
							if (firstgrade < 0 || firstgrade > 100) {
								JOptionPane.showMessageDialog(null, "First grade not valid for module: " + module,
										"Error", 2);
								break;
							} else {
								teacher.addFirstGrade(regNr, label, module, firstgrade);
							}
						}

						if (resitgrade != 0) {
							if (resitgrade < 0 || resitgrade > 100) {
								JOptionPane.showMessageDialog(null, "Resit grade not valid for module: " + module,
										"Error", 2);
								break;
							} else if (firstgrade == 0 || firstgrade > passGrade) {
								JOptionPane.showMessageDialog(null, "Resit grade not needed for module : " + module,
										"Error", 2);
								break;
							} else if (resitgrade > passGrade)
								teacher.addResitGrade(regNr, label, module, passGrade);
							else
								teacher.addResitGrade(regNr, label, module, resitgrade);

						}
					}

					if (scrollPane != null) {
						remove(scrollPane);
						repaint();
						revalidate();
					}
					scrollPane = new JScrollPane();
					scrollPane.setBounds(36, 119, 282, 187);
					add(scrollPane);
					GetFromDB get = new GetFromDB();
					try {
						table = get.moduleForGrades(regNr, label);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					get.closeConnection();
					scrollPane.setViewportView(table);
					calculateOverallGrade();
					teacher.addOverallGrade(regNr, label, overallGrade);
					JOptionPane.showMessageDialog(null, "Grades have been updated base on the rules. ", "Success", 1);

				}
			});

			btnComplete.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			btnComplete.setBounds(162, 325, 113, 23);
			add(btnComplete);

			JButton btnConfirmed = new JButton("Complete year");
			btnConfirmed.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					int degreeDuration = 0;
					boolean yearInIndustry = false;

					yearInIndustry = degreename.endsWith("with a Year in Industry");

					try {
						GetFromDB get = new GetFromDB();
						degreeDuration = get.getDegreeDuration(degreename);
						get.closeConnection();
					} catch (SQLException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}

					MyDate stardate = ps.getStartDate();
					MyDate enddate = ps.getEndDate();

					MyDate newsDate = MyDate.yearPlusOne(stardate);
					MyDate neweDate = MyDate.yearPlusOne(enddate);

					PeriodOfStudy newps = null;

					if (!yearInIndustry && level == degreeDuration) {
						teacher.studentCompletesCourse(regNr);
						JOptionPane.showMessageDialog(null, "Student Completed Course: ", "Success", 1);

					}

					else if (yearInIndustry && level == degreeDuration - 1) {
						teacher.studentCompletesCourse(regNr);
						JOptionPane.showMessageDialog(null, "Student Completed Course: ", "Success", 1);

					}

					else if (overallGrade < passGrade) {
						newps = new PeriodOfStudy(label += 1, newsDate, neweDate, level, regNr);
						String re = teacher.studentRepeatsLevel(newps, degreeDuration, post);
						if (re == "Force fail")
							JOptionPane.showMessageDialog(null, "Student forced failed degree", "Success", 1);
						else if (re == "Repeat")
							JOptionPane.showMessageDialog(null, "Student repeats year ", "Success", 1);
						else
							JOptionPane.showMessageDialog(null, "Student force complete degree ", "Success", 1);

					}

					else if (yearInIndustry && level == degreeDuration - 2) {
						newps = new PeriodOfStudy(label += 1, newsDate, neweDate, Integer.valueOf("" + level + "" + 1),
								regNr);
						PeriodOfStudy newps2 = new PeriodOfStudy(label += 1, MyDate.yearPlusOne(newsDate),
								MyDate.yearPlusOne(neweDate), level + 1, regNr);
						teacher.studentAdvances2x(newps, newps2);
						JOptionPane.showMessageDialog(null, "Student advances ", "Success", 1);

					}

					else {
						newps = new PeriodOfStudy(label += 1, newsDate, neweDate, level + 1, regNr);
						teacher.studentAdvances(newps);
						JOptionPane.showMessageDialog(null, "Student advances ", "Success", 1);

					}

				}
			});
			btnConfirmed.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			btnConfirmed.setBounds(156, 354, 148, 23);

			add(btnConfirmed);

		}
		JLabel lblCredits = new JLabel("Overall Grade");
		lblCredits.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblCredits.setBounds(36, 327, 119, 18);
		add(lblCredits);

		level = ps.getLevel();
		label_field.setText(Character.toString(ps.getLabel()));
		level_field.setText(Integer.toString(level));
		start_date_field.setText(ps.getStartDate().dateInString());
		end_date_field.setText(ps.getEndDate().dateInString());

		scrollPane = new JScrollPane();
		scrollPane.setBounds(36, 119, 282, 187);
		add(scrollPane);
		get = new GetFromDB();
		try {
			table = get.moduleForGrades(regNr, label);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		get.closeConnection();
		scrollPane.setViewportView(table);

		calculateOverallGrade();

	}
}
