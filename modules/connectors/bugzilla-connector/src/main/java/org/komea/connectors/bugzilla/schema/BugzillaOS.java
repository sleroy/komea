package org.komea.connectors.bugzilla.schema;

public class BugzillaOS {

	private String	name;

	public BugzillaOS() {
		super();
	}

	public BugzillaOS(final String _name) {
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
		return "BugzillaOS [name=" + this.name + "]";
	}

}
