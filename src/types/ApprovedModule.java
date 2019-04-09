package types;

/**
 *
 * @author vasile alexandru apetri
 */
public class ApprovedModule {
	private String degree_name;
	private String module_name;
	private Boolean core;
	private int level;
	private int credit;

	public ApprovedModule(String degree_name, String module_name, Boolean core, int level, int credit) {
		this.degree_name = degree_name;
		this.module_name = module_name;
		this.core = core;
		this.level = level;
		this.credit = credit;

	}

	public String getDegreeName() {
		return degree_name;
	}

	public String getModuleName() {
		return module_name;
	}

	public Boolean isCore() {
		return core;
	}

	public int getLevel() {
		return level;
	}

	public int getCredit() {
		return credit;
	}

	public String toString() {
		return "Degree: " + degree_name + " Module_name: " + module_name + " Core: " + core.toString() + " Level: "
				+ level + " Credit: " + credit;

	}
}
