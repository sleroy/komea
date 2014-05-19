/**
 *
 */
package org.komea.product.backend.olap;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.komea.eventory.query.predefined.EmptyQueryDefinition;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.backend.service.kpi.KpiBuilder;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.model.timeserie.GroupFormula;
import org.komea.product.model.timeserie.PeriodTimeSerieOptions;
import org.komea.product.model.timeserie.TimeSerieOptions;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sleroy
 */
@Transactional
public abstract class AbstractPerformanceTest extends AbstractSpringIntegrationTestCase {

	private static final int	 MAX_BUILD_PER_HOUR	    = 5;
	/**
     *
     */
	private static final int	 MAX_NUMBER_OF_PROJECTS	= 5;
	private static List<Measure>	measures	        = new ArrayList<Measure>(20000);
	protected static final int	 MILLI_INTERVAL	        = 1000;

	// @Rule
	// public final H2ProfilerRule h2ProfilerRule = new H2ProfilerRule();
	@Autowired
	private KpiDao	             kpiDao;
	@Autowired
	private MeasureDao	         measureDao;
	@Autowired
	protected IStatisticsAPI	 statisticsAPI;

	/**
     *
     */
	public AbstractPerformanceTest() {

		super();
	}

	public Kpi beforeInitialization() {

		final Kpi generatedKpi = initFakeKPi();

		kpiDao.insert(generatedKpi);

		System.out.println("< Number of kpis : " + kpiDao.selectByCriteria(new KpiCriteria()).size() + " <> ID ="
		        + generatedKpi.getId());
		generatedKpi.setEsperRequest(String.valueOf(generatedKpi.getId()));

		measures.clear();
		measures = FakeMeasures.generateHourlyDataForKpi(generatedKpi.getEsperRequest(), 2, MAX_NUMBER_OF_PROJECTS,
		        MAX_BUILD_PER_HOUR);
		LOGGER.info("Number of elements {}", measures.size());

		measureDao.deleteByCriteria(new MeasureCriteria());
		for (final Measure measure : measures) {

			measureDao.insert(measure);
		}
		System.out.println("Number of kpis : " + kpiDao.selectByCriteria(new KpiCriteria()).size());
		return generatedKpi;
	}

	private Kpi initFakeKPi() {

		final Kpi build = KpiBuilder.create().cronOneDay().description("").entityType(EntityType.PROJECT)
		        .query(EmptyQueryDefinition.class).key("bla" + new Date().getTime()).name("Kpi Olap")
		        .providerType(ProviderType.BUGTRACKER).interval(0d, 100d)
		        .produceValue(ValueType.INT, ValueDirection.BETTER).build();
		System.out.println("Generated kpi");
		return build;
	}

	protected void sameTimeSerieConfig(final PeriodTimeSerieOptions timeSerieOptions, final Kpi _kpi) {

		timeSerieOptions.untilNow();
		timeSerieOptions.lastYears(10);

		sameTimeSerieTimeConfig(timeSerieOptions, _kpi);
		assertTrue(timeSerieOptions.isValid());
	}

	protected void sameTimeSerieTimeConfig(final TimeSerieOptions _timeSerieOptions, final Kpi _kpi) {
		_timeSerieOptions.setKpiID(_kpi.getId());
		_timeSerieOptions.setGroupFormula(GroupFormula.COUNT);

	}
}
