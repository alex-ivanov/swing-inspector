package com.swingInspector.runtime;

import com.swingInspector.utils.Listener;
import com.swingInspector.utils.Listeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

/**
 * This class is responsible for informing listeners about component selection.
 * author: alex
 * date  : 11/13/13
 */
public class ComponentSelectionControl {
	private final BorderMouseListeners BORDER_MOUSE_LISTENERS = new BorderMouseListeners(this);
	private final Listeners<ComponentsSelectionEvent> listeners = new Listeners<ComponentsSelectionEvent>();

	public ComponentSelectionControl(Components components) {
		Set<JComponent> allKnownComponents = components.componentsSet();
		for (JComponent component : allKnownComponents)
			component.addMouseListener(BORDER_MOUSE_LISTENERS);
		components.addListener(new Listener<JComponent>() {
			@Override
			public void onEvent(JComponent update) {
				update.addMouseListener(BORDER_MOUSE_LISTENERS);
			}
		});
	}

	public void addComponentMouseListener(Listener<ComponentsSelectionEvent> listener) {
		listeners.addListener(listener);
	}

	public void removeComponentMouseListener(Listener<ComponentsSelectionEvent> listener) {
		listeners.removeListener(listener);
	}

	private static class BorderMouseListeners extends MouseAdapter {
		private final ComponentSelectionControl componentsSelection;

		public BorderMouseListeners(ComponentSelectionControl componentsSelection)
		{
			this.componentsSelection = componentsSelection;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof JComponent) {
				JComponent c = (JComponent) component;
				componentsSelection.listeners.pushEvent(ComponentsSelectionEvent.enter(c));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof JComponent) {
				JComponent c = (JComponent) component;
				componentsSelection.listeners.pushEvent(ComponentsSelectionEvent.exit(c));
			}
		}
	}

}
