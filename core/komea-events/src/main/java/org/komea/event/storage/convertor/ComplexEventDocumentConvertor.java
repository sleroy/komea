package org.komea.event.storage.convertor;

import java.io.Serializable;
import java.util.Map.Entry;

import org.komea.event.model.api.IComplexEvent;
import org.komea.orientdb.session.document.IODocument;

/**
 * This class defines a convertor from a complex event to OrientDB.
 *
 * @author sleroy
 *
 */
public class ComplexEventDocumentConvertor extends
		BasicEventDocumentConvertor<IComplexEvent> {

	public ComplexEventDocumentConvertor(final IComplexEvent _event) {
		super(_event);
	}

	@Override
	public void convert(final IODocument _newDocument) {
		super.convert(_newDocument);
		for (final Entry<String, ? extends Serializable> entry : this.event
				.getProperties().entrySet()) {
			_newDocument.field(entry.getKey(), entry.getValue());
		}
	}
}
