package org.komea.connectors.bugzilla.schema;


public class BugzillaProduct {

	private Integer	productId;

	private String	name;

	private String	description;

	public BugzillaProduct() {

	}

	public BugzillaProduct(final Integer _id, final String _name, final String _description) {
		super();
		this.productId = _id;
		this.name = _name;
		this.description = _description;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (this.getClass() != obj.getClass()) { return false; }
		final BugzillaProduct other = (BugzillaProduct) obj;
		if (this.productId == null) {
			if (other.productId != null) { return false; }
		} else if (!this.productId.equals(other.productId)) { return false; }
		return true;
	}

	public String getDescription() {
		return this.description;
	}

	public Integer getProductId() {
		return this.productId;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.productId == null ? 0 : this.productId.hashCode());
		return result;
	}

	public void setDescription(final String _description) {
		this.description = _description;
	}

	public void setProductId(final Integer _id) {
		this.productId = _id;
	}

	public void setName(final String _name) {
		this.name = _name;
	}

	@Override
	public String toString() {
		return "BugzillaProduct [id=" + this.productId + ", name=" + this.name + "]";
	}

}
