package com.swingInspector.utils;

/**
 * author: alex
 * date  : 11/12/13
 */
public interface Listener<T> {
	public void onEvent(T update);
}
