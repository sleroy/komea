package org.komea.orientdb.session.impl;

import org.komea.orientdb.session.document.IODocument;

import com.google.common.base.Function;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class IODocumentFunction implements Function<ODocument, IODocument> {

	@Override
	public IODocument apply(final ODocument _arg0) {

		return new ODocumentProxy(_arg0);
	}

}
