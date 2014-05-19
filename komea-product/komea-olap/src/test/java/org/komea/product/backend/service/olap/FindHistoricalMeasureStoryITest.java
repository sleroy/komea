package org.komea.product.backend.service.olap;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.komea.product.backend.api.exceptions.EntityNotFoundException;
import org.komea.product.backend.exceptions.KPINotFoundRuntimeException;
import org.komea.product.backend.service.kpi.IMeasureService;
import org.komea.product.backend.service.kpi.KpiLoadingService;
import org.komea.product.database.enums.EntityType;
import org.komea.product.model.timeserie.GroupFormula;
import org.komea.product.model.timeserie.PeriodTimeSerieOptions;
import org.komea.product.model.timeserie.dto.TimeCoordinateDTO;
import org.komea.product.model.timeserie.dto.TimeSerieDTO;
import org.komea.product.service.dto.KpiStringKey;
import org.komea.product.service.dto.KpiStringKeyList;
import org.komea.product.test.spring.AbstractSpringDBunitIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.google.common.collect.Sets;

@DatabaseTearDown(value = "measures.xml", type = DatabaseOperation.DELETE_ALL)
public class FindHistoricalMeasureStoryITest extends AbstractSpringDBunitIntegrationTest {

	@Autowired
	private IMeasureService	  measureService;

	@Autowired
	private KpiLoadingService	kpiLoading;

	@Before
	public void setpUp() {

		kpiLoading.initLoadingService();
	}

	@Ignore
	@Test
	@DatabaseSetup("measures.xml")
	public void test__only_one_get_historic_with_end_date_before_first_value() {

		// GIVEN the database contain the KPI branch_coverage
		// AND the project Komea has two value for this KPI : 35% (5/01/2014)
		// and 60% ((1/05/2014)
		// WHEN the user looking for the coverage-branch for the project komea
		// between 1/1/2013 and 1/1/2014
		final KpiStringKey kpiKey = KpiStringKey.ofKpiNameAndEntityDetails("BRANCH_COVERAGE(%)", EntityType.PROJECT,
		        "KOMEA");
		final PeriodTimeSerieOptions period = new PeriodTimeSerieOptions();
		period.setFromPeriod(new DateTime(2013, 1, 1, 0, 0, 0));
		period.setToPeriod(new DateTime(2014, 1, 1, 0, 0));
		period.pickBestGranularity();
		period.setGroupFormula(GroupFormula.AVG_VALUE);

		final TimeSerieDTO measure = measureService.findHistoricalMeasure(kpiKey, period);

		// THEN the measure must have only no values
		final List<TimeCoordinateDTO> historicalValues = measure.getCoordinates();
		Assert.assertEquals(0, historicalValues.size());
	}

	@Ignore
	@Test
	@DatabaseSetup("measures.xml")
	public void test__only_one_get_historic_with_start_date_after_first_value() {

		// GIVEN the database contain the KPI branch_coverage
		// AND the project Komea has two value for this KPI : 35% (5/01/2014)
		// and 60% ((1/05/2014)
		// WHEN the user looking for the coverage-branch for the project komea
		// between 1/04/2014 and now
		final KpiStringKey kpiKey = KpiStringKey.ofKpiNameAndEntityDetails("BRANCH_COVERAGE(%)", EntityType.PROJECT,
		        "KOMEA");
		final PeriodTimeSerieOptions period = new PeriodTimeSerieOptions();
		period.setFromPeriod(new DateTime(2014, 4, 1, 0, 0, 0));
		period.setToPeriod(new DateTime());
		period.pickBestGranularity();
		period.setGroupFormula(GroupFormula.AVG_VALUE);

		final TimeSerieDTO measure = measureService.findHistoricalMeasure(kpiKey, period);

		// THEN the measure must have only one values
		final List<TimeCoordinateDTO> historicalValues = measure.getCoordinates();
		Assert.assertEquals(1, historicalValues.size());
		// the first value must be 60%
		Assert.assertEquals(60, historicalValues.get(0).getValue(), 0.001);
	}

	@Ignore
	@Test
	@DatabaseSetup("measures.xml")
	public void test__only_one_get_historic_with_start_date_sup_end_date() {

		// GIVEN the database contain the KPI branch_coverage
		// AND the project Komea has two value for this KPI : 35% (5/01/2014)
		// and 60% ((1/05/2014)
		// WHEN the user looking for the coverage-branch for the project komea
		// between 5/5/2014 and 1/4/2014
		final KpiStringKey kpiKey = KpiStringKey.ofKpiNameAndEntityDetails("BRANCH_COVERAGE(%)", EntityType.PROJECT,
		        "KOMEA");
		final PeriodTimeSerieOptions period = new PeriodTimeSerieOptions();
		period.setFromPeriod(new DateTime(2014, 5, 5, 0, 0, 0));
		period.setToPeriod(new DateTime(2014, 4, 1, 0, 0, 0));
		period.pickBestGranularity();
		period.setGroupFormula(GroupFormula.AVG_VALUE);

		final TimeSerieDTO measure = measureService.findHistoricalMeasure(kpiKey, period);

		// THEN the measure list must be empty
		final List<TimeCoordinateDTO> historicalValues = measure.getCoordinates();
		Assert.assertEquals(0, historicalValues.size());
	}

	@Ignore
	@Test
	@DatabaseSetup("measures.xml")
	public void test_get_historic_measures() {

		// GIVEN the database contain the KPI branch_coverage
		// AND the project Komea has two value for this KPI : 35% (5/01/2014)
		// and 60% ((1/05/2014)
		// WHEN the user looking for the coverage-branch for the project komea
		// between 1/4/2014 and now
		final KpiStringKey kpiKey = KpiStringKey.ofKpiNameAndEntityDetails("BRANCH_COVERAGE(%)", EntityType.PROJECT,
		        "KOMEA");
		final PeriodTimeSerieOptions period = new PeriodTimeSerieOptions();
		period.setFromPeriod(new DateTime(2014, 1, 4, 0, 0, 0));
		period.setToPeriod(new DateTime());
		period.pickBestGranularity();
		period.setGroupFormula(GroupFormula.AVG_VALUE);

		final TimeSerieDTO measure = measureService.findHistoricalMeasure(kpiKey, period);

		// THEN the measure must have two values
		final List<TimeCoordinateDTO> historicalValues = measure.getCoordinates();
		Assert.assertEquals(2, historicalValues.size());
		// the first value must be 35%
		Assert.assertEquals(35, historicalValues.get(0).getValue(), 0.001);
		// the second value must be 60%
		Assert.assertEquals(60, historicalValues.get(1).getValue(), 0.001);

	}

	@Ignore
	@Test
	@DatabaseSetup("measures.xml")
	public void test_get_historic_measures_many() {

		// GIVEN the database contain the KPI branch_coverage
		// AND the project Komea has two value for this KPI : 35% (5/01/2014)
		// and 60% ((1/05/2014)
		// WHEN the user looking for the coverage-branch for the project komea
		// between 1/4/2014 and now
		final KpiStringKeyList kpiKeyList = new KpiStringKeyList(Sets.newHashSet("BRANCH_COVERAGE(%)"),
		        Sets.newHashSet("KOMEA"), EntityType.PROJECT);
		final PeriodTimeSerieOptions period = new PeriodTimeSerieOptions();
		period.setFromPeriod(new DateTime(2014, 1, 4, 0, 0, 0));
		period.setToPeriod(new DateTime());
		period.pickBestGranularity();
		period.setGroupFormula(GroupFormula.AVG_VALUE);

		final TimeSerieDTO measure = measureService.findMupltipleHistoricalMeasure(kpiKeyList, period).get(0);

		// THEN the measure must have two values
		final List<TimeCoordinateDTO> historicalValues = measure.getCoordinates();
		Assert.assertEquals(2, historicalValues.size());
		// the first value must be 35%
		Assert.assertEquals(35, historicalValues.get(0).getValue(), 0.001);
		// the second value must be 60%
		Assert.assertEquals(60, historicalValues.get(1).getValue(), 0.001);

	}

	@Ignore
	@Test
	@DatabaseSetup("measures2.xml")
	@DatabaseTearDown(value = "measures2.xml", type = DatabaseOperation.DELETE_ALL)
	public void test_get_historic_measures_with_average() {

		// GIVEN the database contain the KPI branch_coverage
		// AND the project Komea has two value for this KPI : 35% (5/01/2014)
		// and 60% ((14/05/2014)
		// and 70% ((16/05/2014)
		// WHEN the user looking for the coverage-branch for the project komea
		// between 1/4/2014 and now
		final KpiStringKey kpiKey = KpiStringKey.ofKpiNameAndEntityDetails("BRANCH_COVERAGE(%)", EntityType.PROJECT,
		        "KOMEA");
		final PeriodTimeSerieOptions period = new PeriodTimeSerieOptions();
		period.setFromPeriod(new DateTime(2014, 1, 4, 0, 0, 0));
		period.setToPeriod(new DateTime());
		period.pickBestGranularity();
		period.setGroupFormula(GroupFormula.AVG_VALUE);

		final TimeSerieDTO measure = measureService.findHistoricalMeasure(kpiKey, period);

		// THEN the measure must have two values
		final List<TimeCoordinateDTO> historicalValues = measure.getCoordinates();
		Assert.assertEquals(2, historicalValues.size());
		// the first value must be 35%
		Assert.assertEquals(35, historicalValues.get(0).getValue(), 0.001);
		// the second value must be 65% (average between 60 and 70)
		Assert.assertEquals(65, historicalValues.get(1).getValue(), 0.001);

	}

	@Ignore
	@Test(expected = KPINotFoundRuntimeException.class)
	@DatabaseSetup("measures.xml")
	public void test_get_historic_not_existing_kpi() {

		// GIVEN the database contain the KPI branch_coverage
		// AND the project Komea has two value for this KPI : 35% (5/01/2014)
		// and 60% ((1/05/2014)
		// WHEN the user looking for the coverage-branch for the project komea
		final KpiStringKey kpiKey = KpiStringKey.ofKpiNameAndEntityDetails("NOT_EXIST", EntityType.PROJECT, "KOMEA");
		final PeriodTimeSerieOptions period = new PeriodTimeSerieOptions();
		period.setFromPeriod(new DateTime(2013, 1, 1, 0, 0, 0));
		period.setToPeriod(new DateTime(2014, 4, 1, 0, 0));
		period.pickBestGranularity();
		period.setGroupFormula(GroupFormula.AVG_VALUE);

		final TimeSerieDTO measure = measureService.findHistoricalMeasure(kpiKey, period);

		// THEN a KPINotFoundRuntimeException must be launched
	}

	@Ignore
	@Test(expected = EntityNotFoundException.class)
	@DatabaseSetup("measures.xml")
	public void test_get_historic_not_existing_Project() {

		// GIVEN the database contain the KPI branch_coverage
		// AND the project Komea has two value for this KPI : 35% (5/01/2014)
		// and 60% ((1/05/2014)
		// WHEN the user looking for the coverage-branch for the project komea
		final KpiStringKey kpiKey = KpiStringKey.ofKpiNameAndEntityDetails("BRANCH_COVERAGE(%)", EntityType.PROJECT,
		        "NOT_EXIST");
		final PeriodTimeSerieOptions period = new PeriodTimeSerieOptions();
		period.setFromPeriod(new DateTime(2014, 5, 5, 0, 0, 0));
		period.setToPeriod(new DateTime(2014, 4, 1, 0, 0, 0));
		period.pickBestGranularity();
		period.setGroupFormula(GroupFormula.AVG_VALUE);

		final TimeSerieDTO measure = measureService.findHistoricalMeasure(kpiKey, period);

		// THEN a KPINotFoundRuntimeException must be launched
	}

	@Ignore
	@Test(expected = IllegalArgumentException.class)
	@DatabaseSetup("measures.xml")
	public void test_get_historic_with_null_kpiKey() {

		// GIVEN the database contain the KPI branch_coverage
		// AND the project Komea has two value for this KPI : 35% (5/01/2014)
		// and 60% ((1/05/2014)
		// WHEN the user looking for the null KPI for the project komea
		final KpiStringKey kpiKey = KpiStringKey.ofKpiNameAndEntityDetails(null, EntityType.PROJECT, "NOT_EXIST");
		final PeriodTimeSerieOptions period = new PeriodTimeSerieOptions();
		period.setFromPeriod(new DateTime(2014, 5, 5, 0, 0, 0));
		period.setToPeriod(new DateTime(2014, 4, 1, 0, 0, 0));
		period.pickBestGranularity();
		period.setGroupFormula(GroupFormula.AVG_VALUE);

		final TimeSerieDTO measure = measureService.findHistoricalMeasure(kpiKey, period);

		// THEN the measure list must be empty
		// List<HistoricalValue> historicalValues =
		// measure.getHistoricalValues();
		// Assert.assertEquals(0, historicalValues.size());
	}
}
