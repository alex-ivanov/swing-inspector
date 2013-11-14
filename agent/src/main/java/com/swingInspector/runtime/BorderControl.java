package com.swingInspector.runtime;

import com.swingInspector.utils.Listener;
import com.swingInspector.utils.Listeners;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * author: alex
 * date  : 11/10/13
 */
public class BorderControl {
	private static final Object PLACE_HOLDER = new Object();

	private final Map<JComponent, Object> borderedComponents = new WeakHashMap<JComponent, Object>();

	private ComponentHighlightConfiguration currentConfiguration;


	public BorderControl(ComponentSelectionControl selection,
						 PaintControl paintControl)
	{
		selection.addComponentMouseListener(new BorderListener(this));
		paintControl.addListener(new Listener<ComponentPaintEvent>() {
			@Override
			public void onEvent(ComponentPaintEvent update) {
				if (currentConfiguration != null && borderedComponents.containsKey(update.getComponent())) {
					SwingHelper.paintBorder(update.getComponent(),
							update.getGraphics(),
							currentConfiguration.getBorderColor());
				}
			}
		});
	}

	public void enableBorder(ComponentHighlightConfiguration configuration) {
		if (currentConfiguration != null && currentConfiguration.equals(configuration)) {
			return;//current border is the same
		}

		if (currentConfiguration != null) {
			disableBorder();//remove all old borders
		}
		currentConfiguration = configuration;
	}

	public void disableBorder() {
		currentConfiguration = null;
		Set<JComponent> components = new HashSet<JComponent>(borderedComponents.keySet());
		borderedComponents.clear();
		for (JComponent component : components) {
			component.repaint();
		}
	}

	private void addBorder(JComponent component) {
		if (borderedComponents.put(component, PLACE_HOLDER) == null)
			component.repaint();
	}

	private void disableBorder(JComponent component) {
		if (borderedComponents.remove(component) == PLACE_HOLDER)
			component.repaint();
	}

	private class BorderListener implements Listener<ComponentsSelectionEvent> {
		private final BorderControl borderControl;

		public BorderListener(BorderControl borderControl) {
			this.borderControl = borderControl;
		}

		@Override
		public void onEvent(ComponentsSelectionEvent update) {
			switch (update.getType()) {
				case ENTER:
					borderControl.addBorder(update.getComponent());
					break;
				case EXIT:
					borderControl.disableBorder(update.getComponent());
					break;
			}
		}
	}
}
