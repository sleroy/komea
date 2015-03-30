package org.komea.product.eventory.dao.api;

import com.orientechnologies.orient.core.record.impl.ODocument;

public interface IObjectSerializable {

	void serialize(ODocument _document);
}
