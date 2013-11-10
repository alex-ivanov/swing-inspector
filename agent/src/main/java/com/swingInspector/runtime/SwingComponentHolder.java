package com.swingInspector.runtime;

import javax.swing.*;

/**
 * author: alex
 * date  : 11/10/13
 */
public class SwingComponentHolder {
	public static final Components components = new Components();

	public static void registerJComponent(JComponent component) {
		components.register(component);
	}
}
