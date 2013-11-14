package com.swingInspector.runtime;

import javax.swing.*;
import java.awt.*;
import java.util.WeakHashMap;

/**
 * author: alex
 * date  : 11/13/13
 */
public class PaintControl {
	private static final Object PLACE_HOLDER = new Object();
	private final WeakHashMap<JComponent, Object> enabledComponents =
			new WeakHashMap<JComponent, Object>();

	public void addComponentToBordering(JComponent c) {
		if (enabledComponents.put(c, PLACE_HOLDER) == null)
			c.repaint();
	}

	public void removeComponentFromBordering(JComponent c) {
		if (enabledComponents.remove(c) == PLACE_HOLDER)
			c.repaint();
	}

	public void onComponentPaint(JComponent c, Graphics graphics) {
		if (enabledComponents.containsKey(c)) {
			int x = c.getWidth() - 1;
			int y = c.getHeight() - 1;
			if (x > 0 && y > 0) {
				Graphics g = graphics.create();
				g.setColor(Color.RED);
				g.drawLine(0, 0, x, 0);
				g.drawLine(x, 0, x, y);
				g.drawLine(0, 0, 0, y);
				g.drawLine(0, y, x, y);
				g.dispose();
			}
		}
	}
}
