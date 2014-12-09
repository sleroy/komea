package org.komea.orientdb.session.document;

import java.util.Iterator;
import java.util.List;

import com.orientechnologies.orient.core.iterator.ORecordIteratorClass;
import com.orientechnologies.orient.core.record.impl.ODocument;

public interface IODocumentToolbox {

	public ORecordIteratorClass<ODocument> browseClass(String _eventType);

	public ORecordIteratorClass<ODocument> browseClass(String _eventType, boolean _polymorphic);

	public IODocument newDocument();

	public IODocument newDocument(String className);

	public Iterator<IODocument> query(String _query);

	public List<ODocument> rawQuery(String _sqlQuery);

	public void save(ODocument _event);

}