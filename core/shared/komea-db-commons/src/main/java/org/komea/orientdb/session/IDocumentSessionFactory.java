package org.komea.orientdb.session;

import java.io.Closeable;
import java.util.Iterator;
import java.util.List;

import org.komea.orientdb.session.document.IODocument;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.iterator.ORecordIteratorClass;
import com.orientechnologies.orient.core.record.impl.ODocument;

public interface IDocumentSessionFactory extends Closeable {

	/**
	 * Browse all events of a class
	 *
	 * @param _eventType
	 *            the event type
	 * @return the list of events as documents.
	 */
	public ORecordIteratorClass<ODocument> browseClass(String _eventType);

	/**
	 * Obtains the database session. Creates it if not already created through a
	 * pool of connexion.
	 */
	public ODatabaseDocumentTx getOrCreateDatabaseSession();

	/**
	 * Creates a new document
	 *
	 * @return the document
	 */
	public IODocument newDocument();

	/**
	 * Creates a new document with the given class name.
	 *
	 * @param className
	 *            the class name
	 * @return the document
	 */
	public IODocument newDocument(String className);

	/**
	 * Returns the list of records of the query once proxified.
	 *
	 * @param _query
	 *            the queyr
	 * @return the list of IO documents.
	 */
	public Iterator<IODocument> query(String _query);

	/**
	 * Executes an sql query
	 *
	 * @param _sqlQuery
	 *            the sql query to execute
	 */
	public List<ODocument> rawQuery(String _sqlQuery);

	public void save(ODocument _event);

	ORecordIteratorClass<ODocument> browseClass(String _eventType, boolean _polymorphic);

}