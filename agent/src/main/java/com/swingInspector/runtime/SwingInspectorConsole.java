package com.swingInspector.runtime;

/**
 * author: alex
 * date  : 11/10/13
 */
public class SwingInspectorConsole {
	/**
	 * All JComponent in the system.
	 */
	public static final Components components = new Components();

	public static final ComponentSelectionControl selectionControl = new ComponentSelectionControl(components);

	public static final PaintControl paintControl = new PaintControl();

	/**
	 * Control for border highlighter
	 */
	public static final BorderControl borderControl = new BorderControl(selectionControl, paintControl);
}
