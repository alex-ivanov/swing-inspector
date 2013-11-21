import javax.swing.*;
import java.awt.*;

/**
 * author: alex
 * date  : 11/10/13
 */
public class TestSwing {
	public static void main(String[] args) {
		JFrame frame = new JFrame("hello");
		JPanel mainPanel = new JPanel(new GridBagLayout());

		JPanel simpleButtonPanel = new JPanel();
		simpleButtonPanel.add(new JButton("Hello A"));
		simpleButtonPanel.add(new JButton("Hello B"));

		JPanel simpleTextComponentsPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		simpleTextComponentsPanel.add(new JTextField("Simple text field"), c);

		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		simpleTextComponentsPanel.add(new JTextArea("Long text area", 20, 30), c);

		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridx = 0;
		c2.gridy = 0;
		mainPanel.add(simpleButtonPanel, c2);
		c2.gridy = 1;
		mainPanel.add(simpleTextComponentsPanel, c2);

		frame.getContentPane().add(mainPanel);

		frame.pack();
		frame.setVisible(true);
	}
}
