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
	public SwingDevelopmentConsoleUI() throws HeadlessException {
		super("Swing development console");
		JPanel basePanel = new JPanel(new GridBagLayout());

		final JButton enableDisableBorderButton = new JButton("Enable locator");
		final AtomicBoolean enabled = new AtomicBoolean(false);
		enableDisableBorderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (enabled.get()) {
					SwingInspectorConsole.borderControl.disableBorder();
					enabled.set(false);
					enableDisableBorderButton.setText("Enable locator");
				} else {
					enabled.set(true);
					enableDisableBorderButton.setText("Disable locator");
					ComponentHighlightConfiguration configuration =
							new ComponentHighlightConfiguration(Color.RED, BorderType.SINGLE_COMPONENT);
					SwingInspectorConsole.borderControl.enableBorder(configuration);
				}
			}
		});
		basePanel.add(enableDisableBorderButton);
		this.getContentPane().add(basePanel);
		pack();
	}

	public static void main(String[] args) {
		SwingDevelopmentConsoleUI ui = new SwingDevelopmentConsoleUI();
		ui.setVisible(true);
	}
}
