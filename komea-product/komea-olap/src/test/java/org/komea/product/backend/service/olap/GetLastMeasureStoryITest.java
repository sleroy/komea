package org.komea.product.backend.service.olap;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.google.common.collect.Sets;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.backend.api.exceptions.EntityNotFoundException;
import org.komea.product.backend.exceptions.KPINotFoundRuntimeException;
import org.komea.product.backend.service.kpi.IMeasureService;
import org.komea.product.backend.service.kpi.KpiLoadingService;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Measure;
import org.komea.product.service.dto.KpiStringKey;
import org.komea.product.service.dto.KpiStringKeyList;
import org.komea.product.service.dto.MeasureResult;
import org.komea.product.test.spring.AbstractSpringDBunitIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

@DatabaseTearDown(value = "measures2.xml", type = DatabaseOperation.DELETE_ALL)
public class GetLastMeasureStoryITest extends AbstractSpringDBunitIntegrationTest {

    @Autowired
    private IMeasureService measureService;

    @Autowired
    private KpiLoadingService kpiLoading;

    @Autowired
    private MeasureDao dao;

    private Integer measureID1;
    private Integer measureID2;

    @Before
    public void setpUp() {

        kpiLoading.initLoadingService();

        DateTime yesterday = new DateTime();
        yesterday.minusDays(1);

        Measure measure1 = new Measure();
        measure1.setDate(yesterday.toDate());
        measure1.setEntityID(4);
        measure1.setIdKpi("2");
        measure1.setValue(60D);

        Measure measure2 = new Measure();
        measure2.setDate(yesterday.toDate());
        measure2.setEntityID(4);
        measure2.setIdKpi("2");
        measure2.setValue(70D);

        dao.insert(measure1);
        dao.insert(measure2);

        measureID1 = measure1.getId();
        measureID2 = measure2.getId();
    }

    @After
    public void tearDown() {

        dao.deleteByPrimaryKey(measureID1);
        dao.deleteByPrimaryKey(measureID2);
    }

    @Test
    @DatabaseSetup("measures2.xml")
    public void test_last_measure_for_kpi_branch_koverage_on_komea_project() {

        // Given thetwo last stored measurs in komea the same days the komea project value are 60 and 70
        // WHEN I ask for the current branch coverage on the komea project
        KpiStringKey kpiKey = KpiStringKey.ofKpiNameAndEntityDetails("BRANCH_COVERAGE(%)", EntityType.PROJECT, "KOMEA");
        double currentMeasure = measureService.lastMeasure(kpiKey);

        // Then the current branch coverage must be 65 (average between 60 ans 70
        Assert.assertEquals(65, currentMeasure, 0.001);
    }

    @Test
    @DatabaseSetup("measures2.xml")
    public void test_current_measure_for_kpi_branch_koverage_on_komea_project_many() {

        // Given the two last stored measurs in komea the same days the komea project value are 60 and 70 // WHEN I ash for the current
        // branch coverage on the komea project
        // WHEN I ask for the current branch coverage on the komea project
        KpiStringKeyList kpiKeys = new KpiStringKeyList(Sets.newHashSet("BRANCH_COVERAGE(%)"), Sets.newHashSet("KOMEA"), EntityType.PROJECT);
        double currentMeasure = measureService.lastMeasures(kpiKeys).get(0).getValue();

        // Then the current branch coverage must be 65 (average between 60 ans 70
        Assert.assertEquals(65, currentMeasure, 0.001);
    }

    @Test(expected = KPINotFoundRuntimeException.class)
    @DatabaseSetup("measures2.xml")
    public void test_last_measure_for_not_exising_kpi_on_komea_project() {

        // WHEN I ask for a non existing kpi on the komea project
        KpiStringKey kpiKey = KpiStringKey.ofKpiNameAndEntityDetails("NOT_EXIST", EntityType.PROJECT, "KOMEA");
        double currentMeasure = measureService.lastMeasure(kpiKey);

        // Then the KPINotFoundRuntimeException exception must be launched
    }

    @Test(expected = EntityNotFoundException.class)
    @DatabaseSetup("measures2.xml")
    public void test_last_measure_for_kpi_branch_koverage_on_not_exinsting_project() {

        // WHEN I ask for the current branch coverage on a non existing project project
        KpiStringKey kpiKey = KpiStringKey.ofKpiNameAndEntityDetails("BRANCH_COVERAGE(%)", EntityType.PROJECT, "NOT_EXIST");
        double currentMeasure = measureService.lastMeasure(kpiKey);

        // Then the EntityNotFoundException exception must be launched
    }

    @Test
    @DatabaseSetup("measures2.xml")
    public void test_last_measure_no_existing_kpi_on_komea_project_many() {

        // WHEN I ask for a non existing kpi on the komea project
        KpiStringKeyList kpiKeys = new KpiStringKeyList(Sets.newHashSet("NOT_EXIST"), Sets.newHashSet("KOMEA"), EntityType.PROJECT);
        List<MeasureResult> measures = measureService.lastMeasures(kpiKeys);

        // Then the measures list must be empty
        Assert.assertTrue("Then the measures list must be empty", measures.isEmpty());
    }

    @Test
    @DatabaseSetup("measures2.xml")
    public void test_last_measure_for_kpi_branch_koverage_on_not_exinsting_project_many() {

        // WHEN I ask for a the branch coverage kpi on not exintuing project
        KpiStringKeyList kpiKeys = new KpiStringKeyList(Sets.newHashSet("BRANCH_COVERAGE(%)"), Sets.newHashSet("NOT_EXIST"),
                EntityType.PROJECT);
        List<MeasureResult> measures = measureService.lastMeasures(kpiKeys);

        // Then the measures list must be empty
        Assert.assertTrue("Then the measures list must be empty", measures.isEmpty());
    }
}
