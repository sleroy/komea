package org.komea.orientdb.session;

import java.io.Closeable;
import java.util.List;

import org.komea.orientdb.session.document.IODocument;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;

public interface IDocumentSessionFactory extends Closeable {

	/**
	 * Obtains the database session. Returns null if not created / existing.
	 */
	public ODatabaseDocumentTx getDatabaseSession();

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
	 * Executes an sql query
	 *
	 * @param _sqlQuery
	 *            the sql query to execute
	 */
	public List<ODocument> query(String _sqlQuery);

	public void save(ODocument _event);

}