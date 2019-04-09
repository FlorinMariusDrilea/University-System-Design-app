package types;

/**
 *
 * @author vasile alexandru apetri
 */
public class MyModule {

	public enum Period_of_teaching {
		AUTUMN, SPRING, YEAR, SUMMER
	};

	private String full_name;
	private String code;
	private String lecturer;
	private Period_of_teaching period_of_teaching;

	public MyModule(String full_name, String lecturer, Period_of_teaching period_of_teaching) {
		this.full_name = full_name;
		this.lecturer = lecturer;
		this.period_of_teaching = period_of_teaching;

	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFullName() {
		return full_name;
	}

	public String getCode() {
		return code;
	}

	public String getLecturer() {
		return lecturer;
	}

	public Period_of_teaching getPT() {
		return period_of_teaching;
	}

	public String toString() {
		return "Module: " + full_name + " Code: " + code + " Lecturer: " + lecturer + " Period of teaching: "
				+ period_of_teaching.toString();

	}

}
