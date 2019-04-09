package connection;

import java.sql.*;

/**
 *
 * @author andrianos michail and vasile alexandru apetri
 */
public class CreateDB {
	public static void createUsers() throws Exception {
		Statement stmt = null;
		DBconnect dbcon = new DBconnect();
		Connection con = dbcon.getConnection();

		try {
			//creating the tables
			stmt = con.createStatement();
			String sql_users = "CREATE TABLE IF NOT EXISTS users (username VARCHAR(255) NOT NULL, "
					+ "password VARCHAR(255), " + "privilege VARCHAR(255), " + "PRIMARY KEY (username)) ";

			String sql_departments = "CREATE TABLE IF NOT EXISTS departments (department_name VARCHAR(30) NOT NULL, "
					+ "abbreviation VARCHAR(3), " + "PRIMARY KEY (department_name))";

			String sql_degrees = "CREATE TABLE IF NOT EXISTS degrees (degree_name VARCHAR(100) NOT NULL, "
					+ "abbreviation VARCHAR(7) NOT NULL, " + "degree_duration integer NOT NULL, "
					+ "department_name VARCHAR(30) NOT NULL, " + "PRIMARY KEY (degree_name), "
					+ "FOREIGN KEY (department_name) REFERENCES departments (department_name))";

			String sql_modules = "CREATE TABLE IF NOT EXISTS modules (module_name VARCHAR(50), "
					+ "abbreviation VARCHAR(7), " + "lecturer VARCHAR(50), " + "period_of_teaching VARCHAR(30), "
					+ "PRIMARY KEY (module_name))";

			String sql_students = "CREATE TABLE IF NOT EXISTS students (registration_number INTEGER, "
					+ "title VARCHAR(10), " + "family_name VARCHAR(30), " + "forename VARCHAR(30), "
					+ "degree_name VARCHAR(100), " + "email_address VARCHAR(100), " + "personal_tutor VARCHAR(30), "
					+ "degree_grade INTEGER, " + "PRIMARY KEY (registration_number), "
					+ "FOREIGN KEY (degree_name) REFERENCES degrees(degree_name))";

			String sql_periods_of_study = "CREATE TABLE IF NOT EXISTS periods_of_study (start_date DATE, "
					+ "end_date DATE, " + "label CHAR(1), " + "level INTEGER, "
					+ "confirmed_modules BOOLEAN NOT NULL DEFAULT 0, " + "overall_grade INTEGER, "
					+ "registration_number INTEGER, " + "PRIMARY KEY (registration_number,label), "
					+ "FOREIGN KEY (registration_number) REFERENCES students (registration_number))";

			String sql_module_grades = "CREATE TABLE IF NOT EXISTS module_grades(registration_number INTEGER, "
					+ "label CHAR(1), " + "module_name VARCHAR(50), " + "first_grade INTEGER," + "resit_grade INTEGER,"
					+ "PRIMARY KEY (registration_number,label,module_name), "
					+ "FOREIGN KEY (registration_number,label) REFERENCES periods_of_study(registration_number,label), "
					+ "FOREIGN KEY (module_name) REFERENCES modules(module_name))";

			String sql_degree_modules = "CREATE TABLE IF NOT EXISTS degree_modules(degree_name VARCHAR(100), "
					+ "module_name VARCHAR(50), " + "core BOOLEAN NOT NULL DEFAULT 0, " + "level INTEGER NOT NULL ,"
					+ "credit INTEGER NOT NULL ," + "PRIMARY KEY (degree_name,module_name), "
					+ "FOREIGN KEY (degree_name) REFERENCES degrees(degree_name), "
					+ "FOREIGN KEY (module_name) REFERENCES modules(module_name))";

			stmt.executeUpdate(sql_users);
			stmt.executeUpdate(sql_departments);
			stmt.executeUpdate(sql_degrees);
			stmt.executeUpdate(sql_modules);
			stmt.executeUpdate(sql_students);
			stmt.executeUpdate(sql_periods_of_study);
			stmt.executeUpdate(sql_module_grades);
			stmt.executeUpdate(sql_degree_modules);
			// TOTAL OF 8 TABLES

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (stmt != null)
				stmt.close();
			dbcon.close();
		}
	}

	public static void main(String[] args) throws Exception {
		createUsers();
	}
}
