package org.komea.connectors.bugzilla.schema;

public class BugzillaComponent {

	private String	name;

	public BugzillaComponent() {
		super();
	}

	public BugzillaComponent(final String _name) {
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
		return "BugzillaComponent [name=" + this.name + "]";
	}

}
