package auxclasses;

import java.util.Random;

import org.mindrot.jbcrypt.BCrypt;

public class Hashing {
	/**
	 *
	 * @author andrianos michail and vasile alexandru apetri
	 */
	public static String generateRandomPW() {
		// generates a randomAlphaNumericPassword of size 8-10
		Random rand = new Random();
		int size = rand.nextInt(2) + 8;
		String password = "";
		for (int i = 0; i < size; i++) {
			int current = rand.nextInt(60) + 1;
			char addition = 'A';
			if (current < 26) {
				addition = (char) (65 + current);
			} else if ((current >= 26) && (current < 52)) {
				addition = (char) (97 + current - 26);
			} else {
				addition = (char) (48 + current - 52);
			}
			password = password + addition;
		}
		return password;

	}

	public static boolean isValid(String input) {
		// only allows things that are Capital, lowercase or numbers
		boolean ok = true;
		for (int i = 0; i < input.length(); i++) {
			char curchar = input.charAt(i);
			if (curchar == ';') {
				return false;
			}

			if (!((curchar >= 'A') && (curchar <= 'Z') || ((curchar >= 'a') && (curchar <= 'z'))
					|| ((curchar >= '0') && (curchar <= '9')) || curchar == ' ')) {
				ok = false;
				break;
			}

		}
		return ok;
	}

	public static void main(String[] args) {
		// Test
		BCrypt encryption = new BCrypt();
		String stringUsername = "theusername/";

		boolean valid = Hashing.isValid(stringUsername);
		// System.out.print(valid);

		String sql_select = "SELECT password FROM users where username=stringUsername ";

		String ecrypted = encryption.hashpw(stringUsername, encryption.gensalt());
		// System.out.print(ecrypted);

		boolean decrypted = encryption.checkpw(stringUsername, ecrypted);
		// System.out.print(decrypted);
		for(int i=1;i<100;i++) {
		System.out.println(Hashing.generateRandomPW());}
	}

}