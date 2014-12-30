package org.komea.connectors.git;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.komea.event.model.beans.AbstractEvent;
import org.komea.event.model.beans.BasicEvent;
import org.komea.event.model.beans.ComplexEvent;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.storage.IEventStorage;

import com.google.common.collect.Lists;

public class BasicEventsListStorage implements IEventStorage {

	List<AbstractEvent>	events	= Lists.newArrayList();

	@Override
	public void clearEventsOfType(final String _eventType) {

	}

	@Override
	public void close() throws IOException {

	}

	public List<AbstractEvent> getEvents() {

		return events;
	}

	@Override
	public void storeBasicEvent(final BasicEvent _event) {

		events.add(_event);

	}

	@Override
	public void storeComplexEvent(final ComplexEvent _event) {

		events.add(_event);

	}

	@Override
	public void storeEvent(final AbstractEvent _event) {

		events.add(_event);

	}

	@Override
	public void storeFlatEvent(final FlatEvent _event) {

	}

	@Override
	public void storeMap(final Map<String, Serializable> _fieldMap) {

	}

	@Override
	public void storePojo(final Object _pojo) {

	}

}
