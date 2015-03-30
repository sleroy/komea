package org.komea.product.eventory.storage.service;

import org.komea.product.eventory.dao.api.IEventDao;
import org.komea.product.eventory.dao.api.IEventSerializable;
import org.komea.product.eventory.rest.dto.ComplexEventDto;
import org.komea.product.eventory.rest.dto.SimpleEventDto;
import org.komea.product.eventory.storage.api.IEventStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventStorageService implements IEventStorageService {

	@Autowired
	private IEventDao eventStorageDao;

	@Autowired
	private EventStorageValidator eventStorageValidator;

	@Override
	public void store(final ComplexEventDto _event) {
		this.eventStorageValidator.validateAndRefine(_event);
		this.eventStorageDao.insertPojo(_event);

	}

	@Override
	public void store(final IEventSerializable eventStorage) {
		this.eventStorageValidator.validate(eventStorage);
		this.eventStorageDao.insertPojo(eventStorage);
	}

	@Override
	public void store(final SimpleEventDto _event) {
		this.eventStorageValidator.validateAndRefine(_event);
		this.eventStorageDao.insertPojo(_event);

	}

}
