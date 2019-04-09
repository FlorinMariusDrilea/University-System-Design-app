package people;

/**
 *
 * @author vasile alexandru apetri
 */
public class User {
	public enum Privilege {
		ADMIN, REGISTRAR, TEACHER, STUDENT
	};

	private String username;
	private String password;
	private Privilege privilege;
        //the superclass
	public User(String username, String password, Privilege priv) {

		this.username = username;
		this.password = password;
		this.privilege = priv;
	}

	public Privilege getPrivilege() {
		return this.privilege;
	}

	public String getUsername() {
		return this.username;
	}

	public String gePassword() {
		return this.password;
	}

	public String toString() {
		return "Username: " + username + " Password: " + password + " Privilege: " + privilege.toString();

	}
}
