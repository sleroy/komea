package org.komea.orientdb.session.document;

import java.io.Serializable;

import com.orientechnologies.orient.core.record.impl.ODocument;

public interface IODocument {
	/**
	 * Sets a field of the document.
	 *
	 * @param _key
	 *            the key
	 * @param _value
	 *            the value.
	 */
	void field(String _key, Serializable _value);

	/**
	 * Returns the OrientDB document.
	 *
	 * @return the document
	 */
	ODocument getOrientDBDocument();

	void save();
}
