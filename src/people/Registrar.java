package people;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

import connection.DeleteDB;
import connection.GetFromDB;
import connection.InsertInDB;
import types.MyStudent;
import types.PeriodOfStudy;

/**
 *
 * @author vasile alexandru apetri
 */
public class Registrar extends User {

	public Registrar(String username, String password) {
		super(username, password, Privilege.REGISTRAR);
	}

	public String addStudent(MyStudent student, String password) {
                //method that adds student
		GetFromDB get = new GetFromDB();
		int regNr = get.getLastRegNr() + 1;
		get.closeConnection();

		student.setRegistartionNR(regNr);

		String name = student.getForename().substring(0, 1).toUpperCase() + student.getSurname();

		get = new GetFromDB();
		String emailindb = get.getLastEmail(name);
		get.closeConnection();
		
		String email = null;
		if (emailindb == null)
			email = name + "01" + "@sheffield.ac.uk";
		else {
			int index = name.length();
			String count = emailindb.substring(index, index + 2);
			int countint = Integer.valueOf(count) + 1;
			if (countint < 10)
				count = "0" + countint;
			else
				count = "" + countint;

			email = name + count + "@sheffield.ac.uk";
		}
		student.setEmail(email);

		InsertInDB insert = new InsertInDB();

		try {
			insert.insertInStudents(student);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int index = email.lastIndexOf("@sheffield.ac.uk");
		if (index > 0) {
			email = email.substring(0, index);
		}

		BCrypt encryption = new BCrypt();
		String pass = encryption.hashpw(password, encryption.gensalt());
                //trying to insert a new User
		User user = new Student(email, pass);
		try {
			insert.insertInUsers(user);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		insert.closeConnection();

		return email;
	}

	public void addPeriodOfStudy(PeriodOfStudy firstPs) throws ParseException {
		InsertInDB insert = new InsertInDB();
		//adds a new period of study
		try {
			insert.insertInPeriods_Of_Study(firstPs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		insert.closeConnection();

		int regNr = firstPs.getRegNr();
		char label = firstPs.getLabel();
		int level = firstPs.getLevel();
		MyStudent st = null;

		GetFromDB get = new GetFromDB();
		try {
			st = get.getStudent(regNr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String degree = st.getDegree_name();
		ArrayList<String> ar = null;
		try {
			ar = get.getCoreModules(degree, level);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		get.closeConnection();
		insert = new InsertInDB();
		for (int i = 0; i < ar.size(); i++) {
			try {
				insert.insertInModule_Grades(regNr, label, ar.get(i));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		insert.closeConnection();
		get = new GetFromDB();
		boolean creditsofar = get.totalCreditInModuleGrades(regNr, label);
		get.closeConnection();

		if (creditsofar) {
			insert = new InsertInDB();
			try {
				insert.confirmedModules(regNr, label);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			insert.closeConnection();
		}
	}

	public void deleteStudent(int id) throws ParseException {
		DeleteDB delete = new DeleteDB();
		try {
			delete.deleteStudent(id);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		delete.closeConnection();
	}

}
