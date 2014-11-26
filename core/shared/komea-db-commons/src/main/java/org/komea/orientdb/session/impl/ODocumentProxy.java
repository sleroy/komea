package org.komea.orientdb.session.impl;

import java.io.Serializable;

import org.apache.commons.lang.Validate;
import org.komea.orientdb.session.document.IODocument;

import com.orientechnologies.orient.core.record.impl.ODocument;

public class ODocumentProxy implements IODocument {

	private final ODocument newInstance;

	public ODocumentProxy(final ODocument _newInstance) {
		this.newInstance = _newInstance;
		Validate.notNull(_newInstance);

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

}
