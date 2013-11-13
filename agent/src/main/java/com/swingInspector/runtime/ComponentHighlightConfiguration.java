package com.swingInspector.runtime;

import java.awt.*;

/**
 * author: alex
 * date  : 11/10/13
 */
public class ComponentHighlightConfiguration {
	private final Color borderColor;
	private final BorderType type;

	private ComponentHighlightConfiguration(Color borderColor, BorderType type) {
		this.borderColor = borderColor;
		this.type = type;
	}

	public static ComponentHighlightConfiguration none() {
		return new ComponentHighlightConfiguration(null, BorderType.NONE);
	}

	public static ComponentHighlightConfiguration single(Color color) {
		return new ComponentHighlightConfiguration(color, BorderType.SINGLE_COMPONENT);
	}

	public static ComponentHighlightConfiguration tree(Color color) {
		return new ComponentHighlightConfiguration(color, BorderType.WHOLE_TREE);
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public BorderType getType() {
		return type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ComponentHighlightConfiguration that = (ComponentHighlightConfiguration) o;

		if (!borderColor.equals(that.borderColor)) return false;

		return type == that.type;
	}

	@Override
	public int hashCode() {
		int result = borderColor.hashCode();
		result = 31 * result + type.hashCode();
		return result;
	}

	public enum BorderType {
		NONE,
		SINGLE_COMPONENT,
		WHOLE_TREE
	}
}
