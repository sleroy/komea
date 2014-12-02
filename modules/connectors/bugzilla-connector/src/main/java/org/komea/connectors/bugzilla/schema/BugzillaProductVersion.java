package org.komea.connectors.bugzilla.schema;

public class BugzillaProductVersion {

	private int	   versionId;

	private String	name;

	public BugzillaProductVersion() {
		super();
	}

	public BugzillaProductVersion(final int _id, final String _name) {
		super();
		this.versionId = _id;
		this.name = _name;
	}

	public int getVersionId() {
		return this.versionId;
	}

	public String getName() {
		return this.name;
	}

	public void setVersionId(final int _id) {
		this.versionId = _id;
	}

	public void setName(final String _name) {
		this.name = _name;
	}

	@Override
	public String toString() {
		return "BugzillaProductVersion [id=" + this.versionId + ", name=" + this.name + "]";
	}

}
