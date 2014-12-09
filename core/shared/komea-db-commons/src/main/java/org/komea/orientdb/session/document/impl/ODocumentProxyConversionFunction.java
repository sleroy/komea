package org.komea.orientdb.session.document.impl;

import org.apache.commons.lang.Validate;
import org.komea.orientdb.session.document.IODocument;

import com.google.common.base.Function;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * This class implements a function that convetts an ODocument into an
 * ODocumentProxy
 *
 * @author sleroy
 *
 */
public class ODocumentProxyConversionFunction implements Function<ODocument, IODocument> {

	@Override
	public IODocument apply(final ODocument _arg0) {
		Validate.notNull(_arg0);

		return new ODocumentProxy(_arg0);
	}

}
