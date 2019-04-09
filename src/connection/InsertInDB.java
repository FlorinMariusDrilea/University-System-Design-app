package connection;

import java.sql.*;
import java.text.ParseException;

import people.User;
import types.Degree;
import types.ApprovedModule;
import types.Department;
import types.MyModule;
import types.MyStudent;
import types.PeriodOfStudy;

/**
 *
 * @author vasile alexandru apetri
 */
public class InsertInDB {
        //establishing connection and inserting in Database
	private DBconnect dbcon;
	private Connection con;

	public InsertInDB() {
		dbcon = new DBconnect();
		con = dbcon.getConnection();
	}

	public void closeConnection() {
		dbcon.close();
	}

	public MyStudent getStudent(int regNr) throws SQLException {
		PreparedStatement prep = null;

		try {
			String sql = "SELECT * FROM students WHERE registration_number = ?";
			prep = con.prepareStatement(sql);
			prep.setInt(1, regNr);
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				String title = rs.getString("title");
				String fn = rs.getString("family_name");
				String forename = rs.getString("forename");
				String degree = rs.getString("degree_name");
				String pt = rs.getString("personal_tutor");
				String email = rs.getString("email_address");

				return new MyStudent(regNr, title, fn, forename, degree, pt, email);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
		return null;
	}

	public void insertInUsers(User user) throws Exception {
		PreparedStatement prep = null;

		try {
			String sql = "INSERT INTO users VALUES (?,?,?)";
			prep = con.prepareStatement(sql);
			prep.setString(1, user.getUsername());
			prep.setString(2, user.gePassword());
			prep.setString(3, user.getPrivilege().toString());
			prep.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
	}

	public void insertInDepartments(Department department) throws Exception {
		PreparedStatement prep = null;

		try {
			String sql = "INSERT INTO departments VALUES (?,?)";
			prep = con.prepareStatement(sql);
			prep.setString(1, department.getFullName());
			prep.setString(2, department.getCode());
			prep.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
	}

	public void insertInDegrees(Degree degree) throws Exception {
		PreparedStatement prep = null;

		try {
			String sql = "INSERT INTO degrees VALUES (?,?,?,?)";
			prep = con.prepareStatement(sql);
			prep.setString(1, degree.getFullName());
			prep.setString(2, degree.getCode());
			prep.setInt(3, degree.getDuration());
			prep.setString(4, degree.getLeadDepartmentName());
			prep.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
	}

	public void insertInModules(MyModule module) throws Exception {
		PreparedStatement prep = null;

		try {
			String sql = "INSERT INTO modules VALUES (?,?,?,?)";
			prep = con.prepareStatement(sql);
			prep.setString(1, module.getFullName());
			prep.setString(2, module.getCode());
			prep.setString(3, module.getLecturer());
			prep.setString(4, module.getPT().toString());
			prep.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
	}

	public void insertInDegree_Modules(ApprovedModule dm) throws Exception {
		PreparedStatement prep = null;

		try {
			String sql = "INSERT INTO degree_modules VALUES (?,?,?,?,?)";
			prep = con.prepareStatement(sql);
			prep.setString(1, dm.getDegreeName());
			prep.setString(2, dm.getModuleName());
			prep.setBoolean(3, dm.isCore());
			prep.setInt(4, dm.getLevel());
			prep.setInt(5, dm.getCredit());

			prep.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
	}

	public void insertInStudents(MyStudent student) throws Exception {
		PreparedStatement prep = null;

		try {
			String sql = "INSERT INTO students VALUES (?,?,?,?,?,?,?,?)";
			prep = con.prepareStatement(sql);
			prep.setInt(1, student.getRegNr());
			prep.setString(2, student.getTitle());
			prep.setString(3, student.getSurname());
			prep.setString(4, student.getForename());
			prep.setString(5, student.getDegree_name());
			prep.setString(6, student.getEmail());
			prep.setString(7, student.getPersonalTutor());
			prep.setInt(8, 0);
			prep.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
	}

	public void insertInPeriods_Of_Study(PeriodOfStudy p) throws SQLException, ParseException {
		PreparedStatement prep = null;

		String sd = p.getStartDate().dateInString();
		String ed = p.getEndDate().dateInString();

		try {
			String sql = "INSERT INTO periods_of_study(start_date,end_date,label,level,registration_number,confirmed_modules) VALUES (?,?,?,?,?,?)";
			prep = con.prepareStatement(sql);

			prep.setDate(1, java.sql.Date.valueOf(sd));
			prep.setDate(2, java.sql.Date.valueOf(ed));
			prep.setString(3, p.getLabel() + "");
			prep.setInt(4, p.getLevel());
			prep.setInt(5, p.getRegNr());
			prep.setBoolean(6, false);
			prep.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}

	}

	public void insertInModule_Grades(int regNr, char label, String module_name) throws SQLException {
		PreparedStatement prep = null;

		try {
			String sql = "INSERT INTO module_grades(registration_number,label,module_name) VALUES (?,?,?)";

			prep = con.prepareStatement(sql);
			prep.setInt(1, regNr);
			prep.setString(2, label + "");
			prep.setString(3, module_name);
			prep.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
	}

	public void setFirstGrade(int regNr, char label, String module_name, int grade) throws SQLException {
		PreparedStatement prep = null;

		try {
			String sql = "UPDATE module_grades SET first_grade = ? WHERE registration_number = ? AND label = ? AND module_name = ? ";
			prep = con.prepareStatement(sql);
			prep.setInt(1, grade);
			prep.setInt(2, regNr);
			prep.setNString(3, label + "");
			prep.setString(4, module_name);

			prep.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
	}

	public void setResitGrade(int regNr, char label, String module_name, Integer grade) throws SQLException {
		PreparedStatement prep = null;

		try {
			String sql = "UPDATE module_grades SET resit_grade = ? WHERE registration_number = ? AND label = ? AND module_name = ? ";
			prep = con.prepareStatement(sql);
			prep.setInt(1, grade);
			prep.setInt(2, regNr);
			prep.setNString(3, label + "");
			prep.setString(4, module_name);

			prep.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
	}

	public void setOverallGrade(int regNr, char label, double overallGrade) throws SQLException {
		PreparedStatement prep = null;

		try {
			String sql = "UPDATE periods_of_study SET overall_grade = ? WHERE registration_number = ? AND label = ?";
			prep = con.prepareStatement(sql);
			prep.setDouble(1, overallGrade);
			prep.setInt(2, regNr);
			prep.setNString(3, label + "");

			prep.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
	}

	public void confirmedModules(int regNr, char label) throws SQLException {
		PreparedStatement prep = null;

		try {
			String sql = "UPDATE periods_of_study SET confirmed_modules = ? WHERE registration_number = ? AND label = ?";
			prep = con.prepareStatement(sql);
			prep.setBoolean(1, true);
			prep.setInt(2, regNr);
			prep.setNString(3, label + "");

			prep.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
	}

	public void setDegreeGrade(int regNr, double degree_grade) throws SQLException {
		PreparedStatement prep = null;

		try {
			String sql = "UPDATE students SET degree_grade = ? WHERE registration_number = ?";
			prep = con.prepareStatement(sql);
			prep.setDouble(1, degree_grade);
			prep.setInt(2, regNr);

			prep.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
	}

}
