package people;

import java.sql.SQLException;

import connection.DeleteDB;
import connection.GetFromDB;
import connection.InsertInDB;
import types.Degree;
import types.ApprovedModule;
import types.Department;
import types.MyModule;

/**
 *
 * @author vasile alexandru apetri
 */
public class Admin extends User {
        //admin subclass of user
	public Admin(String username, String password) {
		super(username, password, Privilege.ADMIN);
	}

	public void addUser(User user) {
		InsertInDB insert = new InsertInDB();
		try {
			insert.insertInUsers(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		insert.closeConnection();
	}

	public void addDepartment(Department department) {
		InsertInDB insert = new InsertInDB();
                //add in department
		try {
			insert.insertInDepartments(department);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		insert.closeConnection();

	}

	public void addDegree(Degree degree, Department lead_depart) {
		String depcode = lead_depart.getCode();
		String last2digits = getCurrentMaxDegreeCode(depcode);
                //adds in degree
		String code;
		int id = Integer.valueOf(last2digits) + 1;
		if (id < 10) {
			String ls = "0" + id;
			code = ls;
		} else
			code = "" + id;

		degree.addCode(code);
		InsertInDB insert = new InsertInDB();

		try {
			insert.insertInDegrees(degree);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		insert.closeConnection();

	}

	public void addModule(MyModule module, String department) {
		//adds a module
		String depcode = getDepartmentCode(department);
		String modid = getCurrentMaxModuleCode(depcode);
		String code = depcode + (Integer.valueOf(modid) + 1);
		module.setCode(code);

		InsertInDB insert = new InsertInDB();
		try {
			insert.insertInModules(module);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		insert.closeConnection();

	}

	public void deleteUser(String user) {
		DeleteDB delete = new DeleteDB();
		try {
			delete.deleteUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		delete.closeConnection();
	}

	public void deleteDepartment(String department) {
		DeleteDB delete = new DeleteDB();
                //deleting department
		try {
			delete.deleteDepartment(department);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		delete.closeConnection();

	}

	public void deleteDegree(String degree) {
		DeleteDB delete = new DeleteDB();
                //deleting degrees
		try {
			delete.deleteDegree(degree);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		delete.closeConnection();

	}

	public void deleteModule(String module) {
		DeleteDB delete = new DeleteDB();

		try {
			delete.deleteModule(module);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		delete.closeConnection();

	}

	public void approveModule(ApprovedModule dm) {
		InsertInDB insert = new InsertInDB();
                //approving Modules
		try {
			insert.insertInDegree_Modules(dm);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		insert.closeConnection();
	}

	public void disapproveModule(String degree, String module) {
		DeleteDB delete = new DeleteDB();
                //disapproving Modules
		try {
			delete.deleteModule_Degree(degree, module);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		delete.closeConnection();

	}

//------------------------------------------------------------
	public boolean usernameAlreadyInUsers(String username) {
		GetFromDB get = new GetFromDB();

		Boolean ok = false;
		try {
			ok = get.usernameAlreadyInUsers(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		get.closeConnection();

		return ok;
	}

	public String getDepartmentCode(String department) {
		GetFromDB get = new GetFromDB();
                //getting departmentCode
		String depcode = null;
		try {
			depcode = get.getDepartmentCode(department);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		get.closeConnection();

		return depcode;
	}

	public String getCurrentMaxDegreeCode(String depcode) {
		GetFromDB get = new GetFromDB();
		String maxDegCode = get.getLast2Digits(depcode);
		get.closeConnection();

		return maxDegCode;
	}

	public String getCurrentMaxModuleCode(String modcode) {
		//gettingCurrentMaxModuleCodes
		GetFromDB get = new GetFromDB();
		String modid = get.getLast4Digits(modcode);
		get.closeConnection();
		return modid;
	}

}
