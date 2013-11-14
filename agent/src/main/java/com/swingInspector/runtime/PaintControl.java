package com.swingInspector.runtime;

import com.swingInspector.utils.Listener;
import com.swingInspector.utils.Listeners;

import javax.swing.*;
import java.awt.*;

/**
 * author: alex
 * date  : 11/13/13
 */
public class PaintControl {
	private final Listeners<ComponentPaintEvent> listeners = new Listeners<ComponentPaintEvent>();

	public void onComponentPaint(JComponent c, Graphics graphics) {
		listeners.pushEvent(new ComponentPaintEvent(c, graphics));
	}

	public void addListener(Listener<ComponentPaintEvent> listener) {
		listeners.addListener(listener);
	}

	public void removeListener(Listener<ComponentPaintEvent> listener) {
		listeners.removeListener(listener);
	}
}
