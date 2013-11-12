package com.swingInspector.ui;

import com.swingInspector.runtime.ComponentHighlightConfiguration;
import com.swingInspector.runtime.SwingInspectorConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.swingInspector.runtime.ComponentHighlightConfiguration.BorderType;

/**
 * author: alex
 * date  : 11/10/13
 */
public class SwingDevelopmentConsoleUI extends JFrame {
	private final AtomicBoolean bordersHighlight = new AtomicBoolean(false);
	private final JButton enableDisableBorderButton;

	public SwingDevelopmentConsoleUI() throws HeadlessException {
		super("Swing development console");
		JPanel basePanel = new JPanel(new GridBagLayout());

		enableDisableBorderButton = new JButton("Enable locator");
		enableDisableBorderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (bordersHighlight.get()) {
					enableBorderLocator();
				} else {
					disableBorderLocator();
				}
			}
		});
		basePanel.add(enableDisableBorderButton);
		this.getContentPane().add(basePanel);
		pack();
	}

	private void disableBorderLocator() {
		bordersHighlight.set(true);
		enableDisableBorderButton.setText("Disable locator");
		ComponentHighlightConfiguration configuration =
				new ComponentHighlightConfiguration(Color.RED, BorderType.WHOLE_TREE);
		SwingInspectorConsole.borderControl.enableBorder(configuration);
	}

	private void enableBorderLocator() {
		SwingInspectorConsole.borderControl.disableBorder();
		bordersHighlight.set(false);
		enableDisableBorderButton.setText("Enable locator");
	}

	public static void main(String[] args) {
		SwingDevelopmentConsoleUI ui = new SwingDevelopmentConsoleUI();
		ui.setVisible(true);
	}
}
