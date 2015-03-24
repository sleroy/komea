/**
 *
 */
package org.komea.demo.gitspy.widgets.toppanel;

/**
 * This class defines how to represent a top entry to build a top score.
 *
 * @author sleroy
 *
 */
public class TopRecord {
	private String	name;

	private Double	value;

	private String	imageLink;

	public TopRecord() {
		super();
	}

	/**
	 * @param _name
	 * @param _value
	 * @param _imageLink
	 */
	public TopRecord(final String _name, final Double _value,
			final String _imageLink) {
		super();
		this.name = _name;
		this.value = _value;
		this.imageLink = _imageLink;
	}

	/**
	 * @return the imageLink
	 */
	public String getImageLink() {
		return this.imageLink;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the value
	 */
	public Double getValue() {
		return this.value;
	}

	/**
	 * @param _imageLink
	 *            the imageLink to set
	 */
	public void setImageLink(final String _imageLink) {
		this.imageLink = _imageLink;
	}

	/**
	 * @param _name
	 *            the name to set
	 */
	public void setName(final String _name) {
		this.name = _name;
	}

	/**
	 * @param _value
	 *            the value to set
	 */
	public void setValue(final Double _value) {
		this.value = _value;
	}
}
