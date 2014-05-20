package org.komea.product.backend.service.alert;

import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.KpiAlertDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.Operator;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.database.model.Measure;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
@Ignore
public class AlertFinderTest {

    @Mock
    private IAlertService alertSerice;

    @InjectMocks
    private final AlertFinderService finderSerice = new AlertFinderService();

    @Before
    public void setUp() throws Exception {

    }
    //

    /**
     * Test method for
     * {@link org.komea.product.backend.service.alert.AlertService#findAlert(org.komea.product.database.enums.EntityType, org.komea.product.database.dto.BaseEntityDto, org.komea.product.database.model.KpiAlertType, java.util.List, java.util.Map)}
     * .
     */
    @Test
    public final void testFindAlert() throws Exception {

        // GIVEN the alert COVERAGE_BRANCH_TOO_LOW
        final KpiAlertType alertType = new KpiAlertType();
        alertType.setKpiAlertKey("COVERAGE_BRANCH_TOO_LOW");
        alertType.setIdKpi(1);
        alertType.setId(1);
        alertType.setOperator(Operator.LESSER);
        alertType.setValue(60D);
        alertType.setName("branch coverage too small");
        alertType.setDescription("An alert is launched when the Branche coverage is < 60");
        alertType.setSeverity(Severity.CRITICAL);

        // AND the project Komea
        BaseEntityDto entity = new BaseEntityDto();
        entity.setId(1);
        entity.setEntityType(EntityType.PROJECT);
        entity.setDescription("KPI manager");
        entity.setName("Komea");

        // AND AlertCriteria to find on the project komea alerts of type COVERAGE_BRANCH_TOO_LOW
        List<Measure> measures = Lists.newArrayList();
        Measure measure = new Measure();
        measure.setIdKpi("1");
        measure.setEntityID(1);
        measure.setValue(36D);
        measures.add(measure);

        KpiAlertDto alert = finderSerice.findAlert(alertType, entity, null);

        org.junit.Assert.assertEquals("the result must be 36D", 36D, alert.getValue(), 0.001);
    }

    @Test
    public final void testFindAlert_not_associate_to_entity() throws Exception {

        // GIVEN the alert COVERAGE_BRANCH_TOO_LOW
        final KpiAlertType alertType = new KpiAlertType();
        alertType.setKpiAlertKey("COVERAGE_BRANCH_TOO_LOW");
        alertType.setIdKpi(1);
        alertType.setId(1);
        alertType.setOperator(Operator.LESSER);
        alertType.setValue(60D);
        alertType.setName("branch coverage too small");
        alertType.setDescription("An alert is launched when the Branche coverage is < 60");
        alertType.setSeverity(Severity.CRITICAL);

        // AND the project Komea
        BaseEntityDto entity = new BaseEntityDto();
        entity.setId(1);
        entity.setEntityType(EntityType.PROJECT);
        entity.setDescription("KPI manager");
        entity.setName("Komea");

        // AND AlertCriteria to find on the project komea alerts of type COVERAGE_BRANCH_TOO_LOW
        List<Measure> measures = Lists.newArrayList();
        Measure measure = new Measure();
        measure.setIdKpi("1");
        measure.setEntityID(1);
        measure.setValue(36D);
        measures.add(measure);

        KpiAlertDto alert = finderSerice.findAlert(alertType, entity, null);

        org.junit.Assert.assertNull("the result must null", alert);
    }

    @Test
    public final void testFindAlert_not_associate_to_kpi() throws Exception {

        // GIVEN the alert COVERAGE_BRANCH_TOO_LOW
        final KpiAlertType alertType = new KpiAlertType();
        alertType.setKpiAlertKey("COVERAGE_BRANCH_TOO_LOW");
        alertType.setIdKpi(1);
        alertType.setId(1);
        alertType.setOperator(Operator.LESSER);
        alertType.setValue(60D);
        alertType.setName("branch coverage too small");
        alertType.setDescription("An alert is launched when the Branche coverage is < 60");
        alertType.setSeverity(Severity.CRITICAL);

        // AND the project Komea
        BaseEntityDto entity = new BaseEntityDto();
        entity.setId(1);
        entity.setEntityType(EntityType.PROJECT);
        entity.setDescription("KPI manager");
        entity.setName("Komea");

        // AND AlertCriteria to find on the project komea alerts of type COVERAGE_BRANCH_TOO_LOW
        List<Measure> measures = Lists.newArrayList();
        Measure measure = new Measure();
        measure.setIdKpi("1");
        measure.setEntityID(1);
        measure.setValue(36D);
        measures.add(measure);

        KpiAlertDto alert = finderSerice.findAlert(alertType, entity, null);

        org.junit.Assert.assertNull("the result must null", alert);
    }
}
