package org.komea.event.storage.convertor;

import java.io.Serializable;

import org.apache.commons.beanutils.BeanMap;
import org.komea.event.model.beans.FlatEvent;

public class PojoToEventDocumentConvertor {

	public PojoToEventDocumentConvertor() {
		super();
	}

	public FlatEvent convert(final Serializable _pojo) {

		final BeanMap beanMap = new BeanMap(_pojo);
		final FlatEvent complexEvent = new FlatEvent(beanMap);
		return complexEvent;
	}

}
