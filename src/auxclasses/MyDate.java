package auxclasses;

import java.sql.Date;

/**
 *
 * @author vasile alexandru apetri
 */
public class MyDate {
	//implementing my date class
	private String day;
	private String month;
	private String year;
	private String dateInString;

	public MyDate(String day, String month, String year) {
		//initialising
		this.day = day;
		this.month = month;
		this.year = year;
		dateInString = "" + year + "-" + month + "-" + day;
	}

	public MyDate(String d) {
		dateInString = d;
		this.year = d.substring(0, 4);
		this.month = d.substring(5, 7);
		this.day = d.substring(8, 10);
	}

	public static MyDate yearPlusOne(MyDate d) {
		return new MyDate(d.getDay(), d.getMonth(), ("" + (Integer.valueOf(d.getYear()) + 1)));
	}

	public String dateInString() {
		return dateInString;
	}
        //get methods
	public String getDay() {
		return day;
	}

	public String getMonth() {
		return month;
	}

	public String getYear() {
		return year;
	}

	public static void main(String[] args) {
		MyDate d = new MyDate("2018-01-02");
		System.out.println(d.getDay());
	}

}
