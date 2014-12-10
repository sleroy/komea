package org.komea.product.eventory.database.session.api;

import java.util.List;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;

public interface IDocumentSessionFactory {

	/**
	 * Obtains the database session. Returns null if not created / existing.
	 */
	public ODatabaseDocumentTx getDatabaseSession();

	/**
	 * Obtains the database session. Creates it if not already created through a
	 * pool of connexion.
	 */
	public ODatabaseDocumentTx getOrCreateDB();

	/**
	 * Creates a new document
	 *
	 * @return the document
	 */
	public ODocument newDocument();

	/**
	 * Creates a new document with the given class name.
	 *
	 * @param className
	 *            the class name
	 * @return the document
	 */
	public ODocument newDocument(String className);

	/**
	 * Executes an sql query
	 *
	 * @param _sqlQuery
	 *            the sql query to execute
	 */
	public List<ODocument> query(String _sqlQuery);

	public void save(ODocument _event);

}