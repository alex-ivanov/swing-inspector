package com.swingInspector.runtime;

import javax.swing.*;
import java.awt.*;

/**
 * author: alex
 * date  : 11/13/13
 */
public class ComponentPaintEvent {
	private final JComponent component;
	private final Graphics graphics;

	public ComponentPaintEvent(JComponent component, Graphics graphics) {
		this.component = component;
		this.graphics = graphics;
	}

	public JComponent getComponent() {
		return component;
	}

	public Graphics getGraphics() {
		return graphics;
	}
}
