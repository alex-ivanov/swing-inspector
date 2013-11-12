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
	private final JTextArea stackTracePane;

	public SwingDevelopmentConsoleUI() throws HeadlessException {
		super("Swing development console");
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

		stackTracePane = new JTextArea("");
		stackTracePane.setEditable(false);
		stackTracePane.setRows(200);
		stackTracePane.setColumns(200);

		JPanel basePanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		basePanel.add(enableDisableBorderButton, c);
		c.fill = GridBagConstraints.BOTH;
		c.gridy = 1;
		c.gridx = 0;
		c.weightx = 1;
		c.weighty = 1;
		basePanel.add(stackTracePane, c);

		this.getContentPane().add(basePanel);
		this.setMaximumSize(new Dimension(400, 400));
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
