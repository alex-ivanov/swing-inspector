package com.swingInspector.ui;

import com.swingInspector.runtime.ComponentHighlightConfiguration;
import com.swingInspector.runtime.ComponentsSelectionEvent;
import com.swingInspector.runtime.SwingInspectorConsole;
import com.swingInspector.utils.Listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
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
		this.setPreferredSize(new Dimension(400, 400));
		SwingInspectorConsole.selectionControl.addComponentMouseListener(
				new Listener<ComponentsSelectionEvent>() {
			@Override
			public void onEvent(ComponentsSelectionEvent c) {
				if (c.getType() == ComponentsSelectionEvent.Type.ENTER) {
					Exception exception = SwingInspectorConsole.components.stackTrace(c.getComponent());
					if (exception != null) {
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						PrintStream stream = new PrintStream(out);
						exception.printStackTrace(stream);
						try {
							stackTracePane.setText("");
							String str = out.toString("UTF8");
							stackTracePane.setText(str);
						} catch (UnsupportedEncodingException e) {
							throw new RuntimeException(e);
						}
					}
				}
			}
		});

		pack();
	}

	private void disableBorderLocator() {
		bordersHighlight.set(true);
		enableDisableBorderButton.setText("Disable locator");
		ComponentHighlightConfiguration configuration = ComponentHighlightConfiguration.tree(Color.RED);
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
