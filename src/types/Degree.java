package types;

/**
 *
 * @author vasile alexandru apetri
 */
public class Degree {
	private String full_name;
	private String code;
	private int duration;
	private Department lead_department;

	public Degree(String full_name, Department lead_department, Boolean yearInIndustry, int duration, boolean under) {
		this.full_name = full_name;
		this.lead_department = lead_department;
		this.duration = duration;
		if (yearInIndustry) {
			this.full_name = this.full_name + " with a Year in Industry";
			duration -= 1;
		}

		if (under)
			this.code = lead_department.getCode() + "U";
		else
			this.code = lead_department.getCode() + "P";

	}

	public void addCode(String code) {
		this.code += code;
	}

	public String getFullName() {
		return full_name;
	}

	public String getCode() {
		return code;
	}

	public int getDuration() {
		return duration;
	}

	public String getLeadDepartmentName() {
		return lead_department.getFullName();
	}

	public String toString() {
		return "Degree: " + full_name + " Code: " + code + " Duration: " + duration + " Lead department: "
				+ lead_department.getFullName();
	}
}
