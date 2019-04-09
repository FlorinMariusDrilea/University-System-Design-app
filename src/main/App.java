package main;
import java.awt.EventQueue;


import guicore.LoginBox;
/**
*
* @author vasile alexandru apetri
*/
public class App {
	//run the application
	public static void main(String[] args) throws Exception {	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginBox window = new LoginBox();
					window.setVisible();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
