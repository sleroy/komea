package org.komea.connectors.bugzilla.schema;

public class BugzillaPlatform {

	private String	name;

	public BugzillaPlatform() {
		super();
	}

	public BugzillaPlatform(final String _name) {
		super();
		this.name = _name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String _name) {
		this.name = _name;
	}

	@Override
	public String toString() {
		return "BugzillaPlatform [name=" + this.name + "]";
	}

}
