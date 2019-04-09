package types;

/**
*
* @author vasile alexandru apetri
*/
public class MyStudent {

	private int registartionNR;
	private String title;
	private String surname;
	private String forename;
	private String degree_name;
	private String email;
	private String personalTutor;
	private int degree_grade;

	public MyStudent(String title, String surname, String forename, String degree_name, String personalTutor) {
		this.title = title;
		this.surname = surname;
		this.forename = forename;
		this.degree_name = degree_name;
		this.personalTutor = personalTutor;
		degree_grade = 0;
	}

	public MyStudent(int regNr, String title, String surname, String forename, String degree_name, String personalTutor,
			String email) {
		registartionNR = regNr;
		this.title = title;
		this.surname = surname;
		this.forename = forename;
		this.degree_name = degree_name;
		this.personalTutor = personalTutor;
		this.email = email;
		degree_grade = 0;
	}

	public void setDegreeGrade(int deggrade) {
		degree_grade = deggrade;
	}

	public void setRegistartionNR(int regNr) {
		registartionNR = regNr;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getRegNr() {
		return registartionNR;
	}

	public String getTitle() {
		return title;
	}

	public String getSurname() {
		return surname;
	}

	public String getForename() {
		return forename;
	}

	public String getDegree_name() {
		return degree_name;
	}

	public String getEmail() {
		return email;
	}

	public String getPersonalTutor() {
		return personalTutor;
	}

	public String toString() {
		return "RegNr: " + registartionNR + " Title: " + title + " Surname: " + surname + " Forename: " + forename
				+ " Degree: " + degree_name + " Email: " + email + " PersonalTutor: " + personalTutor;

	}

}
