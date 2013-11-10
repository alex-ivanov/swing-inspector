package com.swingInspector.runtime;

import javax.swing.*;
import java.util.WeakHashMap;

/**
 * author: alex
 * date  : 11/10/13
 */
public class Components {
	private final WeakHashMap<JComponent, Exception> components = new WeakHashMap<JComponent, Exception>();

	public Exception stackTrace(JComponent target) {
		return components.get(target);
	}

	@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
	public void register(JComponent c) {
		components.put(c, new Exception());
	}
}
