package connection;

import java.sql.*;

/**
 *
 * @author vasile alexandru apetri
 */
public class DeleteDB {
        //deleting the Database
	private DBconnect dbcon;
	private Connection con;

	public DeleteDB() {
		dbcon = new DBconnect();
		con = dbcon.getConnection();
	}

	public void closeConnection() {
		dbcon.close();
	}

	public void dropTable(String table_name) throws Exception {
		PreparedStatement prep = null;

		try {
			String sql = "DROP TABLE " + table_name;
			prep = con.prepareStatement(sql);

			prep.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
	}

	public void deleteUser(String name) throws Exception {
		PreparedStatement prep = null;

		try {
			String sql = "DELETE FROM users WHERE username = ?";
			prep = con.prepareStatement(sql);
			prep.setString(1, name);
			prep.executeUpdate();


		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
	}

	public void deleteDepartment(String name) throws SQLException {
		PreparedStatement prep = null;

		try {
			String sql = "DELETE FROM departments WHERE department_name = ?";
			prep = con.prepareStatement(sql);
			prep.setString(1, name);
			prep.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}

	}

	public void deleteDegree(String name) throws SQLException {
		PreparedStatement prep = null;

		try {
			String sql = "DELETE FROM degrees WHERE degree_name = ?";
			prep = con.prepareStatement(sql);
			prep.setString(1, name);
			prep.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();

		}

	}

	public void deleteDegreeModules(int id) throws SQLException {
		PreparedStatement prep = null;

		try {
			String sql = "DELETE FROM degree_modules WHERE deg_mod_id =  = ?";
			prep = con.prepareStatement(sql);
			prep.setInt(1, id);
			prep.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}

	}

	public void deleteModule_Degree(String degree, String module) throws SQLException {
		PreparedStatement prep = null;

		try {
			String sql = "DELETE FROM degree_modules WHERE degree_name = ? AND module_name = ?";
			prep = con.prepareStatement(sql);
			prep.setString(1, degree);
			prep.setString(2, module);

			prep.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}

	}

	public void deleteStudent(int id) throws SQLException {
		PreparedStatement prep = null;

		try {
			String sql = "DELETE FROM students WHERE registration_number = ?";
			prep = con.prepareStatement(sql);
			prep.setInt(1, id);
			prep.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}

	}

	public void deletePSs(int id) throws SQLException {
		PreparedStatement prep = null;

		try {
			String sql = "DELETE FROM periods_of_study WHERE registration_number = ?";
			prep = con.prepareStatement(sql);
			prep.setInt(1, id);
			prep.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}

	}

	public void deleteMGS(int id) throws SQLException {
		PreparedStatement prep = null;

		try {
			String sql = "DELETE FROM module_grades WHERE registration_number = ?";
			prep = con.prepareStatement(sql);
			prep.setInt(1, id);
			prep.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
	}

	public void deleteModule(String module) throws SQLException {
		PreparedStatement prep = null;

		try {
			String sql = "DELETE FROM modules WHERE module_name = ?";
			prep = con.prepareStatement(sql);
			prep.setString(1, module);
			prep.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}

	}

	public static void main(String[] args) throws Exception {
		DeleteDB delete = new DeleteDB();
		// delete.dropTable("users");
		// delete.dropTable("degree_modules");
		// delete.dropTable("degrees");
		// delete. dropTable("departments");
		// delete.dropTable("module_grades");
		// delete.dropTable("modules");
		// delete.dropTable("periods_of_study");
		// delete.dropTable("students");

	}
}
