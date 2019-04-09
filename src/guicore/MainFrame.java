package guicore;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import connection.GetFromDB;
import guiadmin.ApproveModulesPanel;
import guiadmin.DegreesPanel;
import guiadmin.DepartmentsPanel;
import guiadmin.ModulesPanel;
import guiadmin.UsersPanel;
import guiregistrar.ExistingStudentsPanel;
import guiregistrar.NewStudentsPanel;
import guistudent.DetailsPanel;
import guiteacher.AddGradesPanel;
import guiteacher.StudetsRecordsPanel;
import people.Admin;
import people.Registrar;
import people.Teacher;
import people.User;
import people.User.Privilege;

import java.awt.CardLayout;
import java.awt.Toolkit;

import javax.swing.JMenuBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Font;

/**
 *
 * @author vasile alexandru apetri
 */
public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JPanel users;
	private JPanel departments;
	private JPanel degrees;
	private JPanel modules;
	private JPanel linkDegreeModule;
	private JPanel newStudents;
	private JPanel exStudents;
	private JPanel allStudents;
	private JPanel addGrades;
	private JPanel studentRecords;


	/**
	 * Create the frame.
	 */
	public MainFrame(User user) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
                //mainframe calss
		setSize(900, 500);
		setLocation(screenSize.width / 4, screenSize.height / 4);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));

		Privilege priv = user.getPrivilege();

		switch (priv) {
	        //case responding to each user
		case ADMIN:
			Admin admin = new Admin(user.getUsername(), user.gePassword());
			setTitle("Logged in as an Admin");

			users = new UsersPanel(admin);
			contentPane.add(users, "usersBox");

			JButton btnUsers = new JButton("Users");
			btnUsers.setFont(new Font("Times New Roman", Font.PLAIN, 30));
			btnUsers.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					contentPane.remove(users);
					users = new UsersPanel(admin);
					contentPane.add(users, "usersBox");
					contentPane.revalidate();
					contentPane.repaint();

					((CardLayout) contentPane.getLayout()).show(contentPane, "usersBox");
				}
			});
			menuBar.add(btnUsers);
                        //departments
			JButton btnDepartments = new JButton("Departments");
			btnDepartments.setFont(new Font("Times New Roman", Font.PLAIN, 30));
			btnDepartments.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (departments != null)
						contentPane.remove(departments);

					departments = new DepartmentsPanel(admin);
					contentPane.add(departments, "departmentsBox");
					contentPane.revalidate();
					contentPane.repaint();
					((CardLayout) contentPane.getLayout()).show(contentPane, "departmentsBox");
				}
			});
			menuBar.add(btnDepartments);

			JButton btnDegrees = new JButton("Degrees");
			btnDegrees.setFont(new Font("Times New Roman", Font.PLAIN, 30));
			btnDegrees.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (degrees != null)

						contentPane.remove(degrees);

					degrees = new DegreesPanel(admin);
					contentPane.add(degrees, "degreesBox");
					contentPane.revalidate();
					contentPane.repaint();
					((CardLayout) contentPane.getLayout()).show(contentPane, "degreesBox");
				}
			});
			menuBar.add(btnDegrees);
                        //Modules 
			JButton btnModules = new JButton("Modules");
			btnModules.setFont(new Font("Times New Roman", Font.PLAIN, 30));
			btnModules.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (modules != null)
						contentPane.remove(modules);

					modules = new ModulesPanel(admin);
					contentPane.add(modules, "modulesBox");
					contentPane.revalidate();
					contentPane.repaint();
					((CardLayout) contentPane.getLayout()).show(contentPane, "modulesBox");
				}
			});
			menuBar.add(btnModules);
                        //aproved Modules
			JButton btnLinkdegreemodule = new JButton("ApproveModules");
			btnLinkdegreemodule.setFont(new Font("Times New Roman", Font.PLAIN, 30));
			btnLinkdegreemodule.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (linkDegreeModule != null)
						contentPane.remove(linkDegreeModule);

					linkDegreeModule = new ApproveModulesPanel(admin);
					contentPane.add(linkDegreeModule, "linkMDBox");
					contentPane.revalidate();
					contentPane.repaint();
					((CardLayout) contentPane.getLayout()).show(contentPane, "linkMDBox");
				}
			});

			menuBar.add(btnLinkdegreemodule);
			break;

		case REGISTRAR:
			setTitle("Logged in as a Registrar");

			Registrar registrar = new Registrar(user.getUsername(), user.gePassword());

			newStudents = new NewStudentsPanel(registrar);
			contentPane.add(newStudents, "newStudedntsBox");

			JButton btnNewStudednts = new JButton("NewStudednts");
			btnNewStudednts.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (newStudents != null) {
						contentPane.remove(newStudents);
					}
					newStudents = new NewStudentsPanel(registrar);
					contentPane.add(newStudents, "newStudedntsBox");
					((CardLayout) contentPane.getLayout()).show(contentPane, "newStudedntsBox");
				}
			});
			menuBar.add(btnNewStudednts);

			JButton btnExistingStudents = new JButton("ExistingStudents");
			btnExistingStudents.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (exStudents != null) {
						contentPane.remove(exStudents);
					}
					exStudents = new ExistingStudentsPanel(registrar);
					contentPane.add(exStudents, "existingStudentsBox");
					((CardLayout) contentPane.getLayout()).show(contentPane, "existingStudentsBox");
				}
			});
			menuBar.add(btnExistingStudents);

			JButton btnAllStudents = new JButton("FullStudentInformation");
			btnAllStudents.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if (allStudents != null) {
						contentPane.remove(exStudents);
					}
					allStudents = new SeeAllStudents();
					contentPane.add(allStudents, "allStudentsBox");
					((CardLayout) contentPane.getLayout()).show(contentPane, "allStudentsBox");
				}
			});
			menuBar.add(btnAllStudents);

			break;

		case TEACHER:
			setTitle("Logged in as a Teacher");
			Teacher teacher = new Teacher(user.getUsername(), user.gePassword());

			addGrades = new AddGradesPanel(teacher);
			contentPane.add(addGrades, "addGradesBox");

			JButton btnAddGrades = new JButton("ModifyGrades");
			btnAddGrades.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (addGrades != null) {
						contentPane.remove(addGrades);
					}
					addGrades = new AddGradesPanel(teacher);
					contentPane.add(addGrades, "addGradesBox");
					((CardLayout) contentPane.getLayout()).show(contentPane, "addGradesBox");
				}
			});
			menuBar.add(btnAddGrades);

			JButton btnStudentRecords = new JButton("ExistingStudents");
			btnStudentRecords.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (studentRecords != null)
						contentPane.remove(studentRecords);

					studentRecords = new StudetsRecordsPanel(teacher);
					contentPane.add(studentRecords, "studentRecordsBox");
					((CardLayout) contentPane.getLayout()).show(contentPane, "studentRecordsBox");
				}
			});
			menuBar.add(btnStudentRecords);

			JButton btnAllStudents2 = new JButton("FullStudentInformation");
			btnAllStudents2.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if (allStudents != null) {
						contentPane.remove(allStudents);
					}
					allStudents = new SeeAllStudents();
					contentPane.add(allStudents, "allStudentsBox");
					((CardLayout) contentPane.getLayout()).show(contentPane, "allStudentsBox");
				}
			});
			menuBar.add(btnAllStudents2);
			break;

		case STUDENT:
			//student box
			setTitle("Logged in as a Student");
			GetFromDB get = new GetFromDB();
			int regNr = get.getStudentRegNrFromEmail(user.getUsername());
			get.closeConnection();

			JPanel details = new DetailsPanel(regNr);
			contentPane.add(details, "detailsBox");

			JButton btnDetails = new JButton("Details");
			btnDetails.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((CardLayout) contentPane.getLayout()).show(contentPane, "detailsBox");
				}
			});
			menuBar.add(btnDetails);

			break;

		}
	}
}
