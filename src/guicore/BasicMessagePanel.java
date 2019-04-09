package guicore;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;

/**
 *
 * @author vasile alexandru apetri
 */
public class BasicMessagePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public BasicMessagePanel() {
		setLayout(null);
                //creates the basic message panel
		JLabel lblPleaseClickOn = new JLabel("Please click on a row to get started");
		lblPleaseClickOn.setBounds(27, 163, 277, 24);
		lblPleaseClickOn.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(lblPleaseClickOn);

	}

}
