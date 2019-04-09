package people;

import java.sql.SQLException;
import java.text.ParseException;

import connection.GetFromDB;
import connection.InsertInDB;
import types.PeriodOfStudy;

/**
 *
 * @author vasile alexandru apetri
 */
public class Teacher extends User {

	public Teacher(String username, String password) {
		super(username, password, Privilege.TEACHER);
	}
        //adding grades and resit grades
	public void addFirstGrade(int regNr, char label, String module, int grade) {
		InsertInDB insert = new InsertInDB();

		try {
			insert.setFirstGrade(regNr, label, module, grade);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		insert.closeConnection();
	}

	public void addResitGrade(int regNr, char label, String module, int grade) {
		InsertInDB insert = new InsertInDB();
		try {
			insert.setResitGrade(regNr, label, module, grade);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		insert.closeConnection();
	}
        //add the overall grade
	public void addOverallGrade(int regNr, char label, double overallGrade) {
		InsertInDB insert = new InsertInDB();

		try {
			insert.setOverallGrade(regNr, label, overallGrade);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		insert.closeConnection();
	}

	public String studentRepeatsLevel(PeriodOfStudy newps, int degreeDuration, boolean post) {
                //graduates and moves from level
		if (newps.getLevel() != 4 && (!post)) {
			GetFromDB get = new GetFromDB();
			boolean tla = get.tookLevelAlready(newps.getRegNr());
			get.closeConnection();
			if (tla) {
				studentCompletesCourse(newps.getRegNr());
				return "Force fail";
			} else {
				Registrar r = new Registrar("", "");

				try {
					r.addPeriodOfStudy(newps);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return "Repeat";
				}
			}
		} else {
			studentCompletesCourse(newps.getRegNr());
			return "Force Complete";
		}
		return null;
	}

	public void studentAdvances(PeriodOfStudy newps) {
		Registrar r = new Registrar("", "");
                //advances
		try {
			r.addPeriodOfStudy(newps);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void studentCompletesCourse(int regNr) {
		//completse course
		GetFromDB get = new GetFromDB();
		double degree_grade = get.calculateDegreeGrade(regNr);
		get.closeConnection();
		InsertInDB insert = new InsertInDB();
		try {
			insert.setDegreeGrade(regNr, degree_grade);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		insert.closeConnection();

	}

	public void studentAdvances2x(PeriodOfStudy newps, PeriodOfStudy newps2) {
		Registrar r = new Registrar("", "");

		try {
			r.addPeriodOfStudy(newps);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}

		try {
			r.addPeriodOfStudy(newps2);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}

	}
}
