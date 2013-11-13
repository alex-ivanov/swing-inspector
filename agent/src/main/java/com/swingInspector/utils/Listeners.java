package com.swingInspector.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * author: alex
 * date  : 11/12/13
 */
public class Listeners<T> {
	private final Set<Listener<T>> listeners = new HashSet<Listener<T>>();

	public void addListener(Listener<T> listener) {
		listeners.add(listener);
	}

	public void removeListener(Listener<T> listener) {
		listeners.remove(listener);
	}

	public void pushEvent(T update) {
		for (Listener<T> listener : listeners) {
			listener.onEvent(update);
		}
	}
}
