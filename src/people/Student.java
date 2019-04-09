package people;

/**
*
* @author vasile alexandru apetri
*/
public class Student extends User{

	public Student(String username, String password) {
		//student subclass, doesnt need anything else
		super(username, password, Privilege.STUDENT);
	}
}
