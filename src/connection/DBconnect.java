package connection;

import java.sql.*;

/**
 *
 * @author vasile alexandru apetri
 */
public class DBconnect {
	//connecting the database
	private static final String URL = "jdbc:mysql://stusql.dcs.shef.ac.uk/team021";
	private static final String USERNAME = "team021";
	private static final String PASSWORD = "27bbc7b1";
	private Connection con;
	private static boolean prevconClose = true;

	public DBconnect() {

		if (!prevconClose) {
			try {
				//System.out.println("Connection already open");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			if (prevconClose) {
				con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				//System.out.println("CONNECTED");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		prevconClose = false;

	}

	public Connection getConnection() {
		return con;
	}

	public boolean isClose() {
		try {
			return con.isClosed();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void close() {

		try {
			con.close();
			//System.out.println("Connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prevconClose = true;

	}

	
}
