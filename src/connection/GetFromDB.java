package connection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import auxclasses.MyDate;
import types.MyStudent;
import types.PeriodOfStudy;

/**
 *
 * @author vasile alexandru apetri
 */
public class GetFromDB {
        //establishing connection and getting from DB
	private DBconnect dbcon;
	private Connection con;

	public GetFromDB() {
		dbcon = new DBconnect();
		con = dbcon.getConnection();
	}

	public void closeConnection() {
		dbcon.close();
	}
        //everything done using try and exception
	public String getPassword(String user) throws Exception {
		PreparedStatement prep = null;

		try {

			String sql = "SELECT password FROM users WHERE username = ?";

			prep = con.prepareStatement(sql);
			prep.setString(1, user);

			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				String password = rs.getString("password");

				return password;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();

		}
		return null;
	}

	public String getPrivilege(String user) throws Exception {
		PreparedStatement prep = null;

		try {

			String sql = "SELECT privilege FROM users WHERE username = ?";

			prep = con.prepareStatement(sql);
			prep.setString(1, user);

			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				String privilege = rs.getString("privilege");

				return privilege;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();

		}
		return null;
	}

	public DefaultTableModel createBasicTable(String tablename) throws Exception {
		PreparedStatement prep = null;
		try {

			String sql = "SELECT * FROM " + tablename;
			prep = con.prepareStatement(sql);
			ResultSet rs = prep.executeQuery();

			ResultSetMetaData metaData = rs.getMetaData();

			Vector<String> columns = new Vector<String>();
			int count = metaData.getColumnCount();

			for (int i = 1; i <= count; i++) {
				String cn = metaData.getColumnName(i).toString();
				int index = cn.lastIndexOf("_");
				if (index > 0) {
					String cn1 = cn.substring(0, index);
					String cn2 = cn.substring(index + 1);
					cn1 = cn1.substring(0, 1).toUpperCase() + cn1.substring(1);
					cn2 = cn2.substring(0, 1).toUpperCase() + cn2.substring(1);
					cn = cn1 + " " + cn2;
				} else
					cn = cn.substring(0, 1).toUpperCase() + cn.substring(1);
				columns.add(cn);
			}

			Vector<Vector<Object>> table = new Vector<Vector<Object>>();
			while (rs.next()) {
				Vector<Object> row = new Vector<Object>();
				for (int j = 1; j <= count; j++) {
					row.add(rs.getObject(j));
				}
				table.add(row);
			}
			return new DefaultTableModel(table, columns);

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {

			if (prep != null)
				prep.close();
		}
		return null;
	}
        //extracting core Modules
	public ArrayList<String> getCoreModules(String degree_name, int level) throws Exception {
		PreparedStatement prep = null;

		try {

			String sql = "SELECT * FROM degree_modules WHERE degree_name = ? AND level = ? AND core = true";

			prep = con.prepareStatement(sql);
			prep.setString(1, degree_name);
			prep.setInt(2, level);

			ResultSet rs = prep.executeQuery();

			ArrayList<String> ar = new ArrayList<String>();

			while (rs.next()) {
				String module = rs.getString("module_name");
				ar.add(module);
			}

			return ar;

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();

		}
		return null;
	}

	public JTable modulesForCPS(String degree_name, int level) throws Exception {
		PreparedStatement prep = null;
                //trying to see if module for PCS
		try {

			String sql = "SELECT * FROM degree_modules WHERE level = ? AND degree_name = ?";
			prep = con.prepareStatement(sql);
			prep.setInt(1, level);
			prep.setString(2, degree_name);

			ResultSet rs = prep.executeQuery();

			Vector<Object> degrees = new Vector<Object>();
			Vector<Object> modules = new Vector<Object>();
			Vector<Object> credits = new Vector<Object>();
			Vector<Object> cores = new Vector<Object>();
			Vector<Object> printcores = new Vector<Object>();

			while (rs.next()) {
				String deg = rs.getString("degree_name");
				degrees.add(deg);
				String mod = rs.getString("module_name");
				modules.add(mod);
				int credit = rs.getInt("credit");
				credits.add(credit);
				Boolean core = rs.getBoolean("core");
				cores.add(core);
				if (core)
					printcores.add("Core");
				else
					printcores.add("Optional");

			}

			DefaultTableModel model = new DefaultTableModel() {

				private static final long serialVersionUID = 1L;

				public Class<?> getColumnClass(int columnIndex) {
					if (columnIndex == 4)
						return Boolean.class;
					else
						return String.class;
				}

				public boolean isCellEditable(int row, int column) {
					if (column == 4 && (Boolean) cores.get(row) == false)
						return true;
					else
						return false;
				}
			};

			JTable table = new JTable();
			table.setModel(model);

			model.addColumn("Degree", degrees);
			model.addColumn("Module", modules);
			model.addColumn("Credit", credits);
			model.addColumn("Core", printcores);
			model.addColumn("Approve");

			return table;

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {

			if (prep != null)
				prep.close();
		}
		return null;
	}

	public int getModuleCredit(String module, String Degree) {
		PreparedStatement prep = null;
                //getting the credits for each module
		try {
			String sql = "SELECT credit FROM degree_modules WHERE module_name =? AND degree_name=?";
			prep = con.prepareStatement(sql);
			prep.setString(1, module);
			prep.setString(2, Degree);
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				int r = rs.getInt("credit");

				return r;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				try {
					prep.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return 0;
	}

	public JTable moduleForGrades(int regNr1, char label) throws Exception {
		PreparedStatement prep = null;

		try {
			String sql = "SELECT * FROM module_grades WHERE registration_number = ? AND label = ?";
			prep = con.prepareStatement(sql);
			prep.setInt(1, regNr1);
			prep.setNString(2, label + "");
			ResultSet rs = prep.executeQuery();

			Vector<Object> modules = new Vector<Object>();
			Vector<Object> credits = new Vector<Object>();
			Vector<Object> fgrades = new Vector<Object>();
			Vector<Object> rgrades = new Vector<Object>();
                        //getting personal information
			while (rs.next()) {
				int regNr = rs.getInt("registration_number");

				String mod = rs.getString("module_name");
				modules.add(mod);

				String degree = getStudentDegree(regNr);
				int credit = getModuleCredit(mod, degree);
				credits.add(credit);

				int fgrade = rs.getInt("first_grade");
				fgrades.add(fgrade);
				int rgrade = rs.getInt("resit_grade");
				rgrades.add(rgrade);
			}

			DefaultTableModel model = new DefaultTableModel() {

				private static final long serialVersionUID = 1L;

				public Class<?> getColumnClass(int columnIndex) {
					if (columnIndex == 2 || columnIndex == 3)
						return Integer.class;
					else
						return String.class;
				}

				public boolean isCellEditable(int row, int column) {
					if (column == 2 || column == 3)
						return true;
					else
						return false;
				}
			};

			JTable table = new JTable();
			table.setModel(model);

			model.addColumn("Module", modules);
			model.addColumn("Credit", credits);
			model.addColumn("First grade", fgrades);
			model.addColumn("Resit Grade", rgrades);

			return table;

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
		return null;
	}

	public boolean usernameAlreadyInUsers(String user) throws SQLException {
		PreparedStatement prep = null;

		try {

			String sql = "SELECT * FROM users WHERE username = ?";
			prep = con.prepareStatement(sql);
			prep.setString(1, user);
			ResultSet rs = prep.executeQuery();

			if (rs.next())
				return true;
			else
				return false;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
		return false;
	}

	public String getLast2Digits(String depcode) {
		PreparedStatement prep = null;
                 //function to get last 2 Digits
		try {
			String sql = "SELECT MAX(abbreviation) abbreviation FROM degrees WHERE abbreviation LIKE ?";
			prep = con.prepareStatement(sql);
			prep.setString(1, depcode + "%");
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				String r = rs.getString("abbreviation");
				if (r == null)
					return "00";
				return r.substring(4, 6);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				try {
					prep.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return "00";
	}

	public String getLastEmail(String name) {
		PreparedStatement prep = null;

		try {
			String sql = "SELECT MAX(email_address) email_address FROM students WHERE email_address LIKE ?";
			prep = con.prepareStatement(sql);
			prep.setString(1, name + "%");
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				String r = rs.getString("email_address");
				if (r == null)
					return null;
				return r;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				try {
					prep.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return null;
	}

	public String getLast4Digits(String depcode) {
		PreparedStatement prep = null;

		try {
			String sql = "SELECT MAX(abbreviation) abbreviation FROM modules WHERE abbreviation LIKE ?";

			prep = con.prepareStatement(sql);
			prep.setString(1, depcode + "%");
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				String r = rs.getString("abbreviation");
				if (r == null)
					return "1000";
				return r.substring(3, 7);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				try {
					prep.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return "1000";
	}

	public String getListOfDepartments() throws SQLException {
		String strings = "";
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "SELECT * FROM departments";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String r = rs.getString("department_name");
				strings += r + ",";

			}
			return strings;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (stmt != null)
				stmt.close();
		}
		return null;
	}

	public String getListOfDegrees() throws SQLException {
		String strings = "";
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "SELECT * FROM degrees";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String r = rs.getString("degree_name");
				strings += r + ",";

			}
			return strings;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (stmt != null)
				stmt.close();
		}
		return null;
	}

	public int getLastRegNr() {
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "SELECT MAX(registration_number) registration_number FROM students";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int r = rs.getInt("registration_number");
				return r;
			}
			return 125621;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return 125621;
	}

	public PeriodOfStudy getPeriodOfStudy(int regNr, char label2) {
		PreparedStatement prep = null;
		try {
                        //get the periods of study
			String sql = "SELECT * FROM periods_of_study WHERE registration_number = ? AND label = ?";
			prep = con.prepareStatement(sql);
			prep.setInt(1, regNr);
			prep.setString(2, "" + label2);
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {

				int level = rs.getInt("level");

				java.sql.Date sd = rs.getDate("start_date");
				java.sql.Date ed = rs.getDate("end_date");
				boolean yes = rs.getBoolean("confirmed_modules");

				MyDate stdate = new MyDate(sd.toString());
				MyDate endate = new MyDate(ed.toString());

				char label = rs.getString("label").charAt(0);

				PeriodOfStudy ps = new PeriodOfStudy(label, stdate, endate, level, regNr);
				ps.setLockedModules(yes);
				return ps;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				try {
					prep.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return null;

	}

	public String getDegreeName(int regNr) throws SQLException {
		PreparedStatement prep = null;
                //get the deegree name for a registration number
		try {
			String sql = "SELECT * FROM students WHERE registration_number = ?";
			prep = con.prepareStatement(sql);
			prep.setInt(1, regNr);
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				String r = rs.getString("degree_name");
				return r;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
		return null;
	}

	public String getListOfModules(int level, String degree_name) throws SQLException {
		//gets the list of modules depending on level and degree
		String strings = "";
		PreparedStatement prep = null;

		try {
			String sql = "SELECT * FROM degree_modules WHERE level = ? AND degree_name = ?";
			prep = con.prepareStatement(sql);
			prep.setInt(1, level);
			prep.setString(2, degree_name);
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				String r = rs.getString("fk_module_name");
				strings += r + ",";

			}
			return strings;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
		return null;
	}

	public boolean isCore(String module_name) throws SQLException {
		PreparedStatement prep = null;
                //checks if a module isCore
		try {
			String sql = "SELECT * FROM degree_modules WHERE module_name = ?";
			prep = con.prepareStatement(sql);
			prep.setString(1, module_name);
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				Boolean b = rs.getBoolean("core");
				return b;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
		return false;
	}

	public int getCredit(String module_name) throws SQLException {
		PreparedStatement prep = null;
                //get credits for a module
		try {
			String sql = "SELECT * FROM degree_modules WHERE module_name = ?";
			prep = con.prepareStatement(sql);
			prep.setString(1, module_name);
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				int b = rs.getInt("credit");
				return b;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
		return 0;
	}

	public String getDepartmentCode(String department) throws SQLException {
		PreparedStatement prep = null;
                //get code for a department
		try {
			String sql = "SELECT * FROM departments WHERE department_name = ?";
			prep = con.prepareStatement(sql);
			prep.setString(1, department);
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				String r = rs.getString("abbreviation");
				return r;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();

		}
		return null;
	}

	public String getListOfModules() throws SQLException {
		String strings = "";
                //gets the list of modules
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "SELECT * FROM modules";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String r = rs.getString("module_name");
				strings += r + ",";

			}
			return strings;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (stmt != null)
				stmt.close();
		}
		return null;
	}

	public JTable studentHistory(int regNr) throws SQLException {
		PreparedStatement prep = null;
                //creates hte student history using the registration number
		try {

			String sql = "SELECT start_date, end_date, label, level, overall_grade FROM periods_of_study WHERE registration_number= ?";

			prep = con.prepareStatement(sql);
			prep.setInt(1, regNr);
			ResultSet rs = prep.executeQuery();

			Vector<Object> sdates = new Vector<Object>();
			Vector<Object> edates = new Vector<Object>();
			Vector<Object> labels = new Vector<Object>();
			Vector<Object> levels = new Vector<Object>();
			Vector<Object> ogrades = new Vector<Object>();

			while (rs.next()) {
				Date sd = rs.getDate("start_date");
				sdates.add(sd);
				Date ed = rs.getDate("end_date");
				edates.add(ed);
				String label = rs.getString("label");
				labels.add(label);
				int level = rs.getInt("level");
				if (level > 9)
					levels.add("P");
				else
					levels.add(level);
				int og = rs.getInt("overall_grade");
				ogrades.add(og);

			}
			DefaultTableModel model = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};

			JTable table = new JTable();
			table.setModel(model);

			model.addColumn("Start Date", sdates);
			model.addColumn("End Date", edates);
			model.addColumn("Label", labels);
			model.addColumn("Level", levels);
			model.addColumn("Year Grade", ogrades);

			return table;

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {

			if (prep != null)
				prep.close();
		}
		return null;
	}

	public MyStudent getStudent(int regNr) throws SQLException {
		PreparedStatement prep = null;
                //get student using registration number
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

	public String getStudentDegree(int regNr) throws SQLException {
		PreparedStatement prep = null;
                //get the degree of a student using registration number
		try {
			String sql = "SELECT degree_name FROM students WHERE registration_number = ?";
			prep = con.prepareStatement(sql);
			prep.setInt(1, regNr);
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				String degree = rs.getString("degree_name");
				return degree;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
		return null;
	}

	public boolean moduleAlreadyIn(int regNr, char label, String module) throws SQLException {
		PreparedStatement prep = null;
                //check if a  module already exists
		try {
			String sql = "SELECT * FROM module_grades WHERE registration_number = ? AND label = ? AND module_name = ?";
			prep = con.prepareStatement(sql);
			prep.setInt(1, regNr);
			prep.setString(2, label + "");
			prep.setString(3, module);
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				String r = rs.getString("module_name");
				if (r == null)
					return false;
				else
					return true;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {

			if (prep != null)
				prep.close();
		}
		return false;
	}

	public int getDegreeDuration(String degree) throws SQLException {
		PreparedStatement prep = null;
                //get duration for a degeree
		try {
			String sql = "SELECT * FROM degrees WHERE degree_name = ?";
			prep = con.prepareStatement(sql);
			prep.setString(1, degree);
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				int r = rs.getInt("degree_duration");
				return r;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
		return 0;
	}

	public int getTotalCredits(String degree, String module) throws SQLException {
		int total = 0;
		PreparedStatement prep = null;
                //get the total credits for a deegree 
		try {
			String sql = "SELECT * FROM degree_modules WHERE degree_name = ? AND module_name = ?";
			prep = con.prepareStatement(sql);
			prep.setString(1, degree);
			prep.setString(2, module);
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				boolean core = rs.getBoolean("core");
				if (core) {
					int r = rs.getInt("credit");
					total += r;
				}

			}
			return total;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
		return total;
	}

	public JTable getTableForApprovedModules(String degree, int level) throws SQLException {
		PreparedStatement prep = null;
                 //get approved modules table
		try {

			String sql = "SELECT * FROM degree_modules WHERE degree_name = ? and level = ?";
			prep = con.prepareStatement(sql);
			prep.setString(1, degree);
			prep.setInt(2, level);
			ResultSet rs = prep.executeQuery();

			Vector<Object> degrees = new Vector<Object>();
			Vector<Object> modules = new Vector<Object>();
			Vector<Object> levels = new Vector<Object>();
			Vector<Object> credits = new Vector<Object>();
			Vector<Object> cores = new Vector<Object>();

			while (rs.next()) {
				String deg = rs.getString("degree_name");
				degrees.add(deg);
				String mod = rs.getString("module_name");
				modules.add(mod);
				int credit = rs.getInt("credit");
				credits.add(credit);
				Boolean core = rs.getBoolean("core");
				if (core)
					cores.add("Core");
				else
					cores.add("Optional");
				int lev = rs.getInt("level");
				levels.add(lev);

			}

			DefaultTableModel model = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};

			JTable table = new JTable();
			table.setModel(model);

			model.addColumn("Degree", degrees);
			model.addColumn("Module", modules);
			model.addColumn("Level", levels);
			model.addColumn("Core", cores);
			model.addColumn("Credit", credits);

			return table;

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {

			if (prep != null)
				prep.close();
		}
		return null;
	}

	public JTable getTableForDegree(String department) throws SQLException {
		PreparedStatement prep = null;
                //get the degrees table for each department
		try {

			String sql = "SELECT * FROM degrees WHERE department_name = ?";
			prep = con.prepareStatement(sql);
			prep.setString(1, department);
			ResultSet rs = prep.executeQuery();

			Vector<Object> degrees = new Vector<Object>();
			Vector<Object> abbreviations = new Vector<Object>();
			Vector<Object> degree_durations = new Vector<Object>();

			while (rs.next()) {
				String deg = rs.getString("degree_name");
				degrees.add(deg);
				String abb = rs.getString("abbreviation");
				abbreviations.add(abb);
				int duration = rs.getInt("degree_duration");
				degree_durations.add(duration);
			}

			DefaultTableModel model = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};

			JTable table = new JTable();
			table.setModel(model);

			model.addColumn("Degree", degrees);
			model.addColumn("Code", abbreviations);
			model.addColumn("Duration", degree_durations);

			return table;

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {

			if (prep != null)
				prep.close();
		}
		return null;
	}

	public JTable tableWithRegNRs() throws SQLException {
		PreparedStatement prep = null;

		try {
                        //selects all students
			String sql = "SELECT * FROM students";
			prep = con.prepareStatement(sql);

			ResultSet rs = prep.executeQuery();

			Vector<Object> regNrs = new Vector<Object>();
			Vector<Object> degrees = new Vector<Object>();
			Vector<Object> forenames = new Vector<Object>();
			Vector<Object> family_names = new Vector<Object>();

			while (rs.next()) {
				int regNr = rs.getInt("registration_number");
				regNrs.add(regNr);
				String deg = rs.getString("degree_name");
				degrees.add(deg);
				String fo = rs.getString("forename");
				forenames.add(fo);
				String fam = rs.getString("family_name");
				family_names.add(fam);
			}

			DefaultTableModel model = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};

			JTable table = new JTable();
			table.setModel(model);

			model.addColumn("Registration Number", regNrs);
			model.addColumn("First Name", forenames);
			model.addColumn("Family Name", family_names);
			model.addColumn("Degree", degrees);

			return table;

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {

			if (prep != null)
				prep.close();
		}
		return null;
	}

	public JTable getTableForModules(String department) throws SQLException {
		PreparedStatement prep = null;
                //get table so we use it in modules
		try {

			String sql = "SELECT * FROM modules WHERE abbreviation LIKE ?";
			prep = con.prepareStatement(sql);
			if (department.length() >= 3)
				department = department.substring(0, 3).toUpperCase();
			else
				department = "";

			prep.setString(1, department + "%");
			ResultSet rs = prep.executeQuery();

			Vector<Object> modules = new Vector<Object>();
			Vector<Object> abbreviations = new Vector<Object>();
			Vector<Object> lecturers = new Vector<Object>();
			Vector<Object> teaching = new Vector<Object>();

			while (rs.next()) {
				String mod = rs.getString("module_name");
				modules.add(mod);
				String abb = rs.getString("abbreviation");
				abbreviations.add(abb);
				String lec = rs.getString("lecturer");
				lecturers.add(lec);
				String tec = rs.getString("period_of_teaching");
				teaching.add(tec);
			}

			DefaultTableModel model = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};

			JTable table = new JTable();
			table.setModel(model);

			model.addColumn("Module", modules);
			model.addColumn("Code", abbreviations);
			model.addColumn("Lecturer", lecturers);
			model.addColumn("Thought", teaching);
			return table;

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {

			if (prep != null)
				prep.close();
		}
		return null;
	}

	public int getStudentRegNrFromEmail(String email) {
		PreparedStatement prep = null;
                //get Registration number from email
		try {
			String sql = "SELECT registration_number FROM students WHERE email_address LIKE ?";
			prep = con.prepareStatement(sql);
			prep.setString(1, email + "%");
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				int r = rs.getInt("registration_number");
				return r;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				try {
					prep.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return 0;
	}

	public boolean moduleForDegreeAlreadyApprove(String degree, String module) throws SQLException {
		PreparedStatement prep = null;
                //get approved modules for degrees
		try {
			String sql = "SELECT * FROM degree_modules WHERE degree_name = ? AND module_name = ?";
			prep = con.prepareStatement(sql);
			prep.setString(1, degree);
			prep.setString(2, module);
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				String mod = rs.getString("core");
				if (mod != null) {
					return true;
				}

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				prep.close();
		}
		return false;
	}

	public JTable specialTableForStudents() throws SQLException {
		PreparedStatement prep = null;
                //students table
		try {

			String sql = "SELECT p.registration_number, p.level, p.label, p.confirmed_modules "
					+ "FROM periods_of_study p " + "INNER JOIN (SELECT registration_number,  MAX(label) AS NewTable "
					+ "FROM periods_of_study  GROUP BY registration_number) q "
					+ "ON p.registration_number = q.registration_number AND p.label = q.NewTable";
			prep = con.prepareStatement(sql);
			ResultSet rs = prep.executeQuery();

			Vector<Object> regNrs = new Vector<Object>();
			Vector<Object> completed = new Vector<Object>();
			Vector<Object> levels = new Vector<Object>();
			Vector<Object> labels = new Vector<Object>();

			while (rs.next()) {
				int regNr = rs.getInt("registration_number");
				double i = studentDegreeGrade(regNr);
				if (i == 0) {
					regNrs.add(regNr);

					int level = rs.getInt("level");
					levels.add(level);
					char label = rs.getString("label").charAt(0);
					labels.add(label);

					boolean yes = rs.getBoolean("confirmed_modules");

					if (yes)
						completed.add("Completed");
					else
						completed.add("In progress");
				}
			}

			DefaultTableModel model = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;
                                
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
                        //adding the columns
			JTable table = new JTable();
			table.setModel(model);
			model.addColumn("Registration Number", regNrs);
			model.addColumn("Confirmed Modules", completed);
			model.addColumn("Level", levels);
			model.addColumn("Label", labels);

			return table;

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {

			if (prep != null)
				prep.close();
		}
		return null;
	}

	public int getMaxLevelForDegree(String degree) {
		PreparedStatement prep = null;
                //getting maximum level for a specific degree
		try {
			String sql = "SELECT * FROM degrees WHERE degree_name = ?";
			prep = con.prepareStatement(sql);
			prep.setString(1, degree);
			ResultSet rs = prep.executeQuery();

			boolean year = degree.endsWith(" with a Year in Industry");

			while (rs.next()) {
				int duration = rs.getInt("degree_duration");
				if (year)
					return duration - 1;
				else
					return duration;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				try {
					prep.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return 0;
	}

	public JTable userTable() throws SQLException {
		PreparedStatement prep = null;
                //user table
		try {

			String sql = "SELECT * FROM users ";
			prep = con.prepareStatement(sql);
			ResultSet rs = prep.executeQuery();

			Vector<Object> usernames = new Vector<Object>();
			Vector<Object> passwords = new Vector<Object>();
			Vector<Object> privilleges = new Vector<Object>();

			while (rs.next()) {
				String user = rs.getString("username");
				usernames.add(user);
				passwords.add("********");
				String tec = rs.getString("privilege");
				privilleges.add(tec);
			}

			DefaultTableModel model = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};

			JTable table = new JTable();
			table.setModel(model);

			model.addColumn("Username", usernames);
			model.addColumn("Password", passwords);
			model.addColumn("Privilege", privilleges);

			return table;

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {

			if (prep != null)
				prep.close();
		}
		return null;
	}

	public boolean isPostgraduate(String degree) {
		PreparedStatement prep = null;
		boolean ok = false;
		//checks that its postgraduate or not
		try {
			String sql = "SELECT * FROM degrees WHERE degree_name = ?";
			prep = con.prepareStatement(sql);
			prep.setString(1, degree);
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				String abb = rs.getString("abbreviation");
				if (abb.substring(3, 4).equals("P"))
					ok = true;

			}
			return ok;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				try {
					prep.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return ok;

	}

	public boolean totalCreditInModuleGrades(int regNr, char label) {
		PreparedStatement prep = null;
		int total = 0;
		//checks if total credit is ok
		int maxcred = 120;
		try {
			String sql = "SELECT * FROM module_grades WHERE registration_number = ? AND label =?";
			prep = con.prepareStatement(sql);
			prep.setInt(1, regNr);
			prep.setString(2, "" + label);
			ResultSet rs = prep.executeQuery();
			String degree = getStudentDegree(regNr);
			boolean post = isPostgraduate(degree);
			if (post)
				maxcred = 180;

			while (rs.next()) {
				String mod = rs.getString("module_name");
				int c = getModuleCredit(mod, degree);
				total += c;
			}
			return total == maxcred;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				try {
					prep.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return false;

	}

	public double calculateDegreeGrade(int regNr) {
		PreparedStatement prep = null;
		//calculate final grade
		try {
			String sql = "SELECT * FROM periods_of_study WHERE registration_number = ?";
			prep = con.prepareStatement(sql);
			prep.setInt(1, regNr);
			ResultSet rs = prep.executeQuery();

			Vector<Object> ogs = new Vector<Object>();

			int count = 0;
			double total = 0;
			while (rs.next()) {
				double og = rs.getDouble("overall_grade");
				int lev = rs.getInt("level");
				if (lev < 10) {
					total += og;
					count = count + 1;
				}

			}
			double r = total / count;
			return r;

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				try {
					prep.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return 0;
	}

	public boolean tookLevelAlready(int regNr) {
		PreparedStatement prep = null;
		//checks if took level more than once
		try {
			String sql = "SELECT level FROM periods_of_study WHERE registration_number = ?";
			prep = con.prepareStatement(sql);
			prep.setInt(1, regNr);
			ResultSet rs = prep.executeQuery();

			int prevlevel = 0;
			while (rs.next()) {
				int lev = rs.getInt("level");
				
				if (prevlevel == lev) {
					return true;
					}
				else
					prevlevel = lev;
			
			}

			return false;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				try {
					prep.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return false;
	}

	public double studentDegreeGrade(int regNr) {
		PreparedStatement prep = null;
		try {
			String sql = "SELECT degree_grade FROM students WHERE registration_number = ?";
			prep = con.prepareStatement(sql);
			prep.setInt(1, regNr);
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				double dg = rs.getInt("degree_grade");

				return dg;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (prep != null)
				try {
					prep.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return 0;
	}

}
