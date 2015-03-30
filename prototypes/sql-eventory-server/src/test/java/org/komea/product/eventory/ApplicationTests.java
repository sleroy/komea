package org.komea.product.eventory;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.eventory.database.dao.EventStorageDao;
import org.komea.product.eventory.rest.dto.SimpleEventDto;
import org.komea.product.eventory.storage.api.IEventQueryService;
import org.komea.product.eventory.storage.api.IEventStorageService;
import org.komea.product.eventory.storage.api.IFieldPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApplicationTests {

	@Autowired
	private IEventStorageService eventStorage;

	@Autowired
	private IEventQueryService eventQueryDao;

	@Autowired
	private EventStorageDao eventStorageDao;

	@Test
	public void contextLoads() {

		final SimpleEventDto simpleEventDto = new SimpleEventDto();
		simpleEventDto.setEventKey("start");
		simpleEventDto.setProvider("jenkins");

		this.eventStorage.store(simpleEventDto);
		assertEquals(1, this.eventQueryDao.getNumberEvents());
		final int res = this.eventQueryDao
				.countWithFieldPredicate(new IFieldPredicate() {

					@Override
					public String getFieldName() {

						return "provider";
					}

					@Override
					public String getFieldValue() {

						return "jenkins";
					}
				});
		assertEquals(1, res);
	}
}
