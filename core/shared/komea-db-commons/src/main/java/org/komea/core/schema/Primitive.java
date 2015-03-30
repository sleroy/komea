package org.komea.core.schema;

/**
 * Primitive data type.
 *
 * @author afloch
 *
 */
public enum Primitive implements IType {
	INTEGER("Integer"), DOUBLE("Double"), BOOLEAN("Boolean"), STRING("String"), DATE(
			"Date");

	private final String type;

	private Primitive(final String name) {
		this.type = name;
	}

	public String getName() {
		return this.type;
	}

	@Override
	public boolean isPrimitive() {
		return true;
	}

}