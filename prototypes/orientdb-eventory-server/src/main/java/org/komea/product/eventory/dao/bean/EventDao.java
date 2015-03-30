package org.komea.product.eventory.dao.bean;

import java.util.Map;

import org.apache.commons.lang.Validate;
import org.komea.product.eventory.dao.api.IEventDao;
import org.komea.product.eventory.dao.api.IEventSerializable;
import org.komea.product.eventory.database.session.api.IDocumentSessionFactory;
import org.komea.product.eventory.database.session.api.IGraphSessionFactory;
import org.komea.product.eventory.utils.validation.ObjectValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.orientechnologies.orient.core.record.impl.ODocument;

@Repository
public class EventDao implements IEventDao {

	@Autowired
	private IDocumentSessionFactory dsf;

	@Autowired
	private IGraphSessionFactory gsf;

	@Override
	public long countEvents(final String _className) {
		return this.dsf.getOrCreateDB().countClusterElements(
				_className);

	}

	@Override
	public void insertGeneric(final String _eventKey,
			final Map<String, Object> _fields) {
		this.insertPojo(new AbstractEventSerializable(_eventKey, _fields));

	}

	@Override
	public void insertPojo(final IEventSerializable event) {
		new ObjectValidation().validateObject(event);
		Validate.notEmpty(event.getEventKey());
		final ODocument newDocument = this.dsf.newDocument(event.getEventKey());
		event.serialize(newDocument);
		System.out.println(newDocument.toJSON());
		newDocument.save();
	}

}
