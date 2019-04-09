package types;

import auxclasses.MyDate;

/**
 *
 * @author vasile alexandru apetri
 */
public class PeriodOfStudy {
	private char label;
	private MyDate start_date;
	private MyDate end_date;
	private int level;
	private int regNr;
	private boolean empty;
	private boolean lockedModules;

	public PeriodOfStudy(char label, MyDate start_date, MyDate end_date, int level, int regNr) {
		this.label = label;
		this.start_date = start_date;
		this.end_date = end_date;
		this.level = level;
		this.regNr = regNr;
	}

	public PeriodOfStudy() {
		this.empty = true;
	}

	public boolean getEmpty() {
		return empty;
	}

	public char getLabel() {
		return label;
	}

	public int getLevel() {
		return level;
	}

	public int getRegNr() {
		return regNr;
	}

	public MyDate getStartDate() {
		return start_date;
	}

	public MyDate getEndDate() {
		return end_date;
	}

	public void setLockedModules(boolean yes) {
		lockedModules = yes;
	}

	public boolean getLockedModules() {
		return lockedModules;
	}

	public String toString() {
		return "Label: " + label + " start_date: " + start_date.dateInString() + " end_date: " + end_date.dateInString()
				+ " level: " + level;

	}
}
