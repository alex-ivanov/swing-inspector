package com.swingInspector.runtime;

import javax.swing.*;

/**
 * author: alex
 * date  : 11/12/13
 */
@SuppressWarnings("UnusedDeclaration")
public class Hooks {
	/**
	 * This method will be called on each JComponent constructor call.
	 */
	public static void onComponentCreate(JComponent c) {
		SwingInspectorConsole.components.register(c);
	}
}
