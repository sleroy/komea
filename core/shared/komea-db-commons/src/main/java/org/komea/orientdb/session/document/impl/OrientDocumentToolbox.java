package org.komea.orientdb.session.document.impl;

import java.io.IOException;

import org.apache.commons.lang.Validate;
import org.springframework.orientdb.orm.session.IOrientSessionFactory;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

public class OrientDocumentToolbox extends AbstractOrientDocumentToolbox {

	private final IOrientSessionFactory	documentTx;

	public OrientDocumentToolbox(final IOrientSessionFactory _sessionFactory) {
		super();
		this.documentTx = _sessionFactory;
		Validate.notNull(this.documentTx);
	}

	public void close() throws IOException {
		this.documentTx.close();
	}

	@Override
	protected ODatabaseDocumentTx getDocumentTx() {

		return this.documentTx.db();
	}
}
