package org.komea.orientdb.session.impl;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.komea.orientdb.session.document.IODocument;

import com.google.common.collect.Maps;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class ODocumentProxy implements IODocument {

	private final ODocument newInstance;

	public ODocumentProxy(final ODocument _newInstance) {
		this.newInstance = _newInstance;
		Validate.notNull(_newInstance);

	}

	@Override
	public boolean containsField(final String _fieldDate) {

		return this.newInstance.containsField(_fieldDate);
	}

	@Override
	public String dump() {
		final StringBuilder sb = new StringBuilder();
		final Map<String, Object> map = Maps.newHashMap();
		for (final String entry : this.newInstance.fieldNames()) {
			map.put(entry, this.newInstance.field(entry));
		}
		sb.append("Document of class ").append(this.newInstance.getClassName())
				.append("with fields ").append(map.toString());
		return sb.toString();
	}

	@Override
	public Object field(final String _fieldDate) {

		return this.newInstance.field(_fieldDate);
	}

	@Override
	public void field(final String _key, final Serializable _value) {
		this.newInstance.field(_key, _value);

	}

	@Override
	public ODocument getOrientDBDocument() {
		return this.newInstance;
	}

	@Override
	public void save() {
		this.newInstance.save();

	}

	@Override
	public String toJSON() {

		return this.newInstance.toJSON();
	}

	@Override
	public String toString() {
		return "ODocumentProxy [newInstance=" + this.newInstance + "]";
	}

}
