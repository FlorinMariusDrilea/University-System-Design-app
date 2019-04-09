package types;

/**
*
* @author vasile alexandru apetri
*/
public class Department {
	private String full_name;
	private String code;

	public Department(String full_name) {
		this.full_name = full_name;
		this.code = full_name.substring(0, 3).toUpperCase();
	}

	public String getCode() {
		return code;

	}
	
	public String getFullName() {
		return full_name;

	}
	
	public String toString() {
		return "Department: " + full_name + " Code: " + code;
		
	}
}
