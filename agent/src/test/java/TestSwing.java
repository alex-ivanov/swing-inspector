import javax.swing.*;

/**
 * author: alex
 * date  : 11/10/13
 */
public class TestSwing {
	public static void main(String[] args) {
		JFrame frame = new JFrame("hello");
		JPanel panel = new JPanel();
		panel.add(new JButton("Hello A"));
		panel.add(new JButton("Hello B"));

		frame.getContentPane().add(panel);

		frame.pack();
		frame.setVisible(true);
	}
}
