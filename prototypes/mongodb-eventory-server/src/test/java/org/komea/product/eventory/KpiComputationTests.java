package org.komea.product.eventory;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.eventory.database.dao.EventStorageDao;
import org.komea.product.eventory.database.model.AggregationFormula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class KpiComputationTests {

	@Autowired
	private EventStorageDao eventStorageDao;

	@Test
	public void contextLoads() {
		final AggregationFormula formula = new AggregationFormula();
		formula.setGroupBy("es.entityKey");
		formula.setAggformula("COUNT(*)");
		formula.setFrom(new DateTime().minusYears(4).toDate());
		formula.untilNow();

		System.out.println(this.eventStorageDao.aggregateOnPeriod(formula));
	}
}
