/**
 *
 */
package org.komea.event.storage.impl;

import org.komea.event.model.beans.FlatEvent;
import org.skife.jdbi.v2.ResultIterator;

/**
 * @author sleroy
 */
public final class EmptyResultIterator implements
ResultIterator<FlatEvent> {
	public static final ResultIterator<FlatEvent>	EMPTY	= new EmptyResultIterator();

	@Override
	public void close() {

	}

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public FlatEvent next() {
		return null;
	}

	@Override
	public void remove() {

	}
}