package org.komea.product.eventory.storage.service;

import java.util.Map.Entry;

import org.komea.product.eventory.database.dao.EventAttrDao;
import org.komea.product.eventory.database.dao.EventMessageDao;
import org.komea.product.eventory.database.dao.EventStorageDao;
import org.komea.product.eventory.database.model.EventAttr;
import org.komea.product.eventory.database.model.EventMessage;
import org.komea.product.eventory.database.model.EventStorage;
import org.komea.product.eventory.rest.dto.ComplexEventDto;
import org.komea.product.eventory.rest.dto.MessageComplexEventDto;
import org.komea.product.eventory.rest.dto.SimpleEventDto;
import org.komea.product.eventory.storage.api.IEventStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.Validate;

@Service
@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
public class EventStorageService implements IEventStorageService {

	@Autowired
	private EventStorageDao eventStorageDao;

	@Autowired
	private EventAttrDao eventAttrDao;

	@Autowired
	private EventMessageDao eventMessageDao;

	@Autowired
	private EventStorageValidator eventStorageValidator;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EventStorageService.class);

	private EventStorage convertEventDto(final SimpleEventDto simpleEventDto) {
		final EventStorage eventStorage = new EventStorage();
		eventStorage.setEventKey(simpleEventDto.getEventKey());
		eventStorage.setProvider(simpleEventDto.getProvider());
		eventStorage.setDate(simpleEventDto.getDate());
		eventStorage.setMessage(simpleEventDto.getMessage());
		eventStorage.setEntity1(simpleEventDto.getEntityKey1());
		eventStorage.setEntity2(simpleEventDto.getEntityKey2());
		eventStorage.setEntity3(simpleEventDto.getEntityKey3());
		eventStorage.setEntity4(simpleEventDto.getEntityKey4());
		eventStorage.setValue1(simpleEventDto.getValue());
		eventStorage.setValue2(simpleEventDto.getValue2());
		eventStorage.setValue3(simpleEventDto.getValue3());
		eventStorage.setValue4(simpleEventDto.getValue4());
		eventStorage.setValue5(simpleEventDto.getValue5());
		return eventStorage;
	}

	@Override
	public void store(final ComplexEventDto simpleEventDto) {
		final EventStorage convertEventDto = this
				.convertEventDto(simpleEventDto);
		final Integer storeEvent = this.storeEvent(convertEventDto);
		this.storeAttributes(storeEvent, simpleEventDto);

	}

	@Override
	public void store(final MessageComplexEventDto simpleEventDto) {
		final EventStorage convertEventDto = this
				.convertEventDto(simpleEventDto);
		final Integer storeEvent = this.storeEvent(convertEventDto);
		this.storeAttributes(storeEvent, simpleEventDto);
		if (!(simpleEventDto.getLongMessage() == null || simpleEventDto
				.getLongMessage().isEmpty())) {
			this.eventMessageDao.insert(new EventMessage(storeEvent,
					simpleEventDto.getLongMessage()));
		}

	}

	@Override
	public void store(final SimpleEventDto simpleEventDto) {
		final EventStorage convertEventDto = this
				.convertEventDto(simpleEventDto);
		this.storeEvent(convertEventDto);

	}

	private void storeAttributes(final Integer storeEvent,
			final ComplexEventDto simpleEventDto) {
		Validate.notNull(storeEvent, "Primary key not found");
		for (final Entry<String, String> entry : simpleEventDto
				.getExtraFields().entrySet()) {
			if (entry.getKey() == null || entry.getValue() == null) {
				LOGGER.warn("Skipping attr {}->{} in event {}", entry.getKey(),
						entry.getValue(), simpleEventDto);
				continue;
			}
			this.eventAttrDao.insert(new EventAttr(storeEvent, entry.getKey(),
					entry.getValue()));
		}

	}

	@Override
	public Integer storeEvent(final EventStorage eventStorage) {
		final EventStorage eventStorageRefined = this.eventStorageValidator
				.validateAndRefine(eventStorage);
		this.eventStorageDao.insert(eventStorageRefined);
		return eventStorage.getId();

	}

}
