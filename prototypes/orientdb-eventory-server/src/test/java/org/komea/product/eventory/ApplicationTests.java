package org.komea.product.eventory;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.eventory.dao.api.IEventDao;
import org.komea.product.eventory.database.model.AggregationFormula;
import org.komea.product.eventory.rest.dto.SimpleEventDto;
import org.komea.product.eventory.sql.api.ISqlQueryService;
import org.komea.product.eventory.storage.api.IEventStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApplicationTests {

	@Autowired
	private IEventStorageService eventStorage;

	@Autowired
	private IEventDao eventStorageDao;

	@Autowired
	private ISqlQueryService sqlQueryService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ApplicationTests.class);

	@Test
	public void contextLoads() {
		LOGGER.info("Initialization");
		final SimpleEventDto simpleEventDto = new SimpleEventDto();
		simpleEventDto.setEventKey("start");
		simpleEventDto.setValue(1d);
		simpleEventDto.setProvider("jenkins");
		simpleEventDto.setEntityKey1("personA");
		this.eventStorage.store(simpleEventDto);

		assertEquals(1, this.eventStorageDao.countEvents("start"));

		final AggregationFormula aggregationFormula = new AggregationFormula();
		aggregationFormula.setFromClause("start");
		aggregationFormula.setAggformula("COUNT(*)");
		aggregationFormula.setFrom(new DateTime().minusYears(1).toDate());
		aggregationFormula.setTo(new DateTime().plusYears(1).toDate());
		aggregationFormula.setFilter("AND provider='jenkins'");
		aggregationFormula.setGroupBy("entityKey1");
		assertEquals(1,
				this.sqlQueryService.aggregateOnPeriod(aggregationFormula)
				.size());
	}
}
