package com.swingInspector.runtime;

import javax.swing.*;
import java.util.*;

/**
 * author: alex
 * date  : 11/10/13
 */
public class Components {
	private final WeakHashMap<JComponent, ComponentInformationHolder> components =
			new WeakHashMap<JComponent, ComponentInformationHolder>();

	private final Set<ComponentListener> listeners = new HashSet<ComponentListener>();

	public Exception stackTrace(JComponent target) {
		ComponentInformationHolder holder = components.get(target);
		return holder != null ? holder.stackTrace : null;
	}

	@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
	public void register(JComponent c) {
		registerPrivate(c, new Exception());
	}

	@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
	public void registerSoft(JComponent c) {
		if (!components.containsKey(c)) {
			registerPrivate(c, null);//
		}
	}

	private void registerPrivate(JComponent c, Exception stackTrace) {
		components.put(c, new ComponentInformationHolder(stackTrace));
		for (ComponentListener listener : listeners) {
			listener.onComponent(c);
		}
	}

	public Set<JComponent> componentsSet() {
		return Collections.unmodifiableSet(components.keySet());
	}

	public ComponentInformationHolder getData(JComponent component) {
		return components.get(component);
	}

	public void addListener(ComponentListener listener) {
		listeners.add(listener);
	}

	public void removeListener(ComponentListener listener) {
		listeners.remove(listener);
	}

	@SuppressWarnings("unchecked")
	public static class ComponentInformationHolder {
		private final Exception stackTrace;
		private final Map<String, Object> data = new HashMap<String, Object>();

		public ComponentInformationHolder(Exception stackTrace) {
			this.stackTrace = stackTrace;
		}

		public <T> T addData(String key, T data) {
			return (T) this.data.put(key, data);
		}

		public <T> T getData(String key) {
			return (T) this.data.get(key);
		}
	}
}
