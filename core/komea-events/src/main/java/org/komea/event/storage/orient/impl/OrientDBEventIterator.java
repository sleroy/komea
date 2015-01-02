package org.komea.event.storage.orient.impl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import org.komea.event.model.beans.FlatEvent;

import com.google.common.collect.Maps;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class OrientDBEventIterator implements Iterator<FlatEvent> {

	private final Iterator<ODocument>	iterator;

	public OrientDBEventIterator(final Iterator<ODocument> _iterator) {
		this.iterator = _iterator;

	}

	@Override
	public boolean hasNext() {

		return this.iterator.hasNext();
	}

	@Override
	public FlatEvent next() {

		final ODocument next = this.iterator.next();
		if (next == null) { return null; }
		final Map<String, Serializable> map = Maps.newHashMap();
		for (final String key : next.fieldNames()) {
			map.put(key, Serializable.class.cast(next.field(key)));
		}
		return new FlatEvent(map);
	}

	@Override
	public void remove() {
		this.iterator.remove();

	}

}
