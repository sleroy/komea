package org.komea.core.schema;

public interface IPrimitiveType extends IType {

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

	Primitive getPrimitive();

}
