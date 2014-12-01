package org.komea.event.storage.convertor;

import java.io.Serializable;

import org.komea.core.utils.PojoToMap;
import org.komea.event.model.beans.FlatEvent;

public class PojoToEventDocumentConvertor {

	public PojoToEventDocumentConvertor() {
		super();
	}

	public FlatEvent convert(final Serializable _pojo) {

		final FlatEvent complexEvent = new FlatEvent(new PojoToMap().convertPojoInMap(_pojo));
		return complexEvent;
	}

}
