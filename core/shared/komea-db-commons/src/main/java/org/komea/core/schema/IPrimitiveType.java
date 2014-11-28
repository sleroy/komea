package org.komea.core.schema;

/**
 * Primitive data type.
 * 
 * @author afloch
 *
 */
public interface IPrimitiveType extends IType {

	/**
	 * Supported primitive types.
	 * 
	 * @author afloch
	 *
	 */
	public enum Primitive {
		INTEGER("Integer"), DOUBLE("Double"), BOOLEAN("Boolean"), STRING(
				"String");

		private final String type;

		private Primitive(final String name) {
			this.type = name;
		}

		public String getTypeName() {
			return type;
		}
	}

	/**
	 * Get the primitive type singleton.
	 * 
	 * @return
	 */
	Primitive getPrimitive();

}
