package org.komea.orientdb.session.document.impl;

import java.util.Iterator;
import java.util.List;

import org.komea.orientdb.session.document.IODocument;
import org.komea.orientdb.session.document.IODocumentToolbox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Iterators;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.iterator.ORecordIteratorClass;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

/**
 * A utitlity class to manipulate OrientDB document
 *
 * @see ODatabaseDocumentTx
 */
public abstract class AbstractOrientDocumentToolbox implements IODocumentToolbox {

	private static final Logger	LOGGER	= LoggerFactory.getLogger(AbstractOrientDocumentToolbox.class);

	public AbstractOrientDocumentToolbox() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.orientdb.session.impl.IOrientDocumentToolbox#browseClass(java
	 * .lang.String)
	 */
	@Override
	public ORecordIteratorClass<ODocument> browseClass(final String _eventType) {

		return this.getDocumentTx().browseClass(_eventType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.orientdb.session.impl.IOrientDocumentToolbox#browseClass(java
	 * .lang.String, boolean)
	 */
	@Override
	public ORecordIteratorClass<ODocument> browseClass(final String _eventType, final boolean _polymorphic) {

		return this.getDocumentTx().browseClass(_eventType, _polymorphic);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.orientdb.session.impl.IOrientDocumentToolbox#newDocument()
	 */
	@Override
	public IODocument newDocument() {

		return new ODocumentProxy(this.getDocumentTx().newInstance());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.orientdb.session.impl.IOrientDocumentToolbox#newDocument(java
	 * .lang.String)
	 */
	@Override
	public IODocument newDocument(final String className) {

		return new ODocumentProxy(this.getDocumentTx().newInstance(className));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.orientdb.session.impl.IOrientDocumentToolbox#query(java.lang
	 * .String)
	 */
	@Override
	public Iterator<IODocument> query(final String _query) {
		final List<ODocument> results = this.rawQuery(_query);
		return Iterators.transform(results.iterator(), new ODocumentProxyConversionFunction());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.orientdb.session.impl.IOrientDocumentToolbox#rawQuery(java.
	 * lang.String)
	 */
	@Override
	public List<ODocument> rawQuery(final String _sqlQuery) {
		LOGGER.trace("Executing query {}", _sqlQuery);
		return this.getDocumentTx().query(new OSQLSynchQuery<ODocument>(_sqlQuery));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.orientdb.session.impl.IOrientDocumentToolbox#save(com.
	 * orientechnologies.orient.core.record.impl.ODocument)
	 */
	@Override
	public void save(final ODocument _event) {
		this.getDocumentTx().save(_event);

	}

	protected abstract ODatabaseDocumentTx getDocumentTx();

}