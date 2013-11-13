package com.swingInspector.runtime;

import javax.swing.*;

/**
 * author: alex
 * date  : 11/13/13
 */
public class ComponentsSelectionEvent {
	private final Type type;
	private final JComponent component;

	private ComponentsSelectionEvent(Type type, JComponent component) {
		this.type = type;
		this.component = component;
	}

	public static ComponentsSelectionEvent enter(JComponent c) {
		return new ComponentsSelectionEvent(Type.ENTER, c);
	}

	public static ComponentsSelectionEvent exit(JComponent c) {
		return new ComponentsSelectionEvent(Type.EXIT, c);
	}

	public Type getType() {
		return type;
	}

	public JComponent getComponent() {
		return component;
	}

	public enum Type {
		ENTER,
		EXIT
	}
}
