package org.komea.connectors.git;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.komea.event.model.beans.AbstractEvent;
import org.komea.event.model.beans.BasicEvent;
import org.komea.event.model.beans.ComplexEvent;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.storage.IEventStorage;

public class CountingStorage implements IEventStorage {

	public int	count;

	@Override
	public void clearEventsOfType(final String _eventType) {

		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws IOException {

		// TODO Auto-generated method stub

	}

	@Override
	public void storeBasicEvent(final BasicEvent _event) {

		count++;

	}

	@Override
	public void storeComplexEvent(final ComplexEvent _event) {

		count++;

	}

	@Override
	public void storeEvent(final AbstractEvent _event) {

		count++;

	}

	@Override
	public void storeFlatEvent(final FlatEvent _event) {

		count++;
	}

	@Override
	public void storeMap(final Map<String, Serializable> _fieldMap) {

		count++;
	}

	@Override
	public void storePojo(final Object _pojo) {

		count++;
	}

    @Override
    public void declareEventType(final String type) {
    
        // TODO Auto-generated method stub
        
    }
    
}
