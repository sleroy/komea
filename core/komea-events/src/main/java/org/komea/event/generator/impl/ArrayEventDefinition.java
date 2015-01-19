/**
 *
 */
package org.komea.event.generator.impl;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.komea.event.generator.IEventArrayDefinition;
import org.komea.event.generator.IEventDefinition;
import org.komea.event.model.beans.FlatEvent;

/**
 * @author sleroy
 */
public class ArrayEventDefinition implements IEventDefinition {

	private final IEventArrayDefinition	definition;

	/**
	 * @param _definition
	 */
	public ArrayEventDefinition(final IEventArrayDefinition _definition) {
		definition = _definition;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.komea.event.generator.IEventDefinition#decorateEvent(org.joda.time
	 * .DateTime, int, org.komea.event.model.beans.FlatEvent)
	 */
	@Override
	public void decorateEvent(final DateTime _date, final int _nthEventOfDay,
			final FlatEvent _flatEvent) {
		for (final Serializable[] keyValue : definition.decorateEvent(_date,
		        _nthEventOfDay)) {
			_flatEvent.put(keyValue[0].toString(), keyValue[1]);

		}

	}
	
}
