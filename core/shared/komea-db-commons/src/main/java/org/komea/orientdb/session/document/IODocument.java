package org.komea.orientdb.session.document;

import java.io.Serializable;

import com.orientechnologies.orient.core.record.impl.ODocument;

public interface IODocument {
	/**
	 * Tests if a field with the given identifier is declared.
	 */
	boolean containsField(String _fieldIdentifier);

	/**
	 * Dump the content of a document into a string
	 *
	 * @return the content.
	 */
	String dump();

	/**
	 * Returns a field with the given name/
	 */
	Object field(String _fieldIdentifier);

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
	 * Sets a field of the document.
	 *
	 * @param _key
	 *            the key
	 * @param _value
	 *            the value.
	 */
	<T extends Serializable> T getField(String _key, Class<T> _value);

	/**
	 * Returns the OrientDB document.
	 *
	 * @return the document
	 */
	ODocument getOrientDBDocument();

	void save();

	/**
	 * Serialization in Json
	 */
	String toJSON();

	/**
	 * Converts the ODocument into a pojo of the given implementation.
	 *
	 * @param _impl
	 *            the implementation.
	 */
	<T> T toPojo(Class<T> _impl);

	/**
	 * Infer the fields into the pojo.
	 *
	 * @param _pojo
	 *            the pojo.
	 */
	void toPojo(Object _pojo);
}
