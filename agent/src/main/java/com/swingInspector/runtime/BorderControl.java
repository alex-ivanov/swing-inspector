package com.swingInspector.runtime;

import com.swingInspector.utils.Listener;
import com.swingInspector.utils.Listeners;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * author: alex
 * date  : 11/10/13
 */
public class BorderControl {
	private final PaintControl paintControl;
	private ComponentHighlightConfiguration currentConfiguration;

	public BorderControl(final Components components,
						 ComponentSelectionControl selection,
						 PaintControl paintControl)
	{
		this.paintControl = paintControl;
		selection.addComponentMouseListener(new BorderListener(components));
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
		Components components = SwingInspectorConsole.components;
		for (JComponent component : components.componentsSet()) {
			restoreOriginalBorder(components, component);
		}
	}

	private static void restoreOriginalBorder(Components components, JComponent c) {
		Components.ComponentInformationHolder data = components.getData(c);
		Border b = data.getData("border");
		Boolean hasBorder = data.getData("has_border");
		if (hasBorder != null && hasBorder) {
			c.setBorder(b);
			data.addData("has_border", null);
		}
		Container parent = c.getParent();
		if (parent instanceof JComponent) {
			restoreOriginalBorder(components, (JComponent) parent);
		}
	}

	private static void setupCustomBorder(Components components,
										ComponentHighlightConfiguration configuration,
										JComponent c)
	{
		Border newBorder = BorderFactory.createLineBorder(configuration.getBorderColor());
		Border currentBorder = c.getBorder();

		Components.ComponentInformationHolder data = components.getData(c);
		Boolean definedBefore = data.getData("has_border");

		if (definedBefore == null || !definedBefore) {
			data.addData("border", currentBorder);
			data.addData("has_border", true);
			c.setBorder(newBorder);
			if (configuration.getType() == ComponentHighlightConfiguration.BorderType.WHOLE_TREE) {
				Container parent = c.getParent();
				if (parent instanceof JComponent) {
					setupCustomBorder(components, configuration, (JComponent) parent);
				}
			}
		}
	}

	private class BorderListener implements Listener<ComponentsSelectionEvent> {
		private final Components components;

		public BorderListener(Components components) {
			this.components = components;
		}

		@Override
		public void onEvent(ComponentsSelectionEvent update) {
			switch (update.getType()) {
				case ENTER:
					if (currentConfiguration != null)
						paintControl.addComponentToBordering(update.getComponent());
					break;
				case EXIT:
					paintControl.removeComponentFromBordering(update.getComponent());
					break;
			}
		}
	}
}
