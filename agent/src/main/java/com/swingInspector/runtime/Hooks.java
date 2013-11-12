package com.swingInspector.runtime;

import javax.swing.*;

/**
 * author: alex
 * date  : 11/12/13
 */
public class Hooks {
	public static void onComponentCreate(JComponent c) {
		SwingInspectorConsole.components.register(c);
	}
}
