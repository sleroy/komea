
package org.komea.product.backend.service.alert;



import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.backend.api.IKpiLoadingService;
import org.komea.product.database.dto.KpiAlertDto;
import org.komea.product.database.dto.SearchKpiAlertsDto;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.enums.Severity;
import org.komea.product.test.spring.AbstractSpringDBunitIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;



@DatabaseTearDown(value = "alerts.xml", type = DatabaseOperation.DELETE_ALL)
public class AlertFinderITest extends AbstractSpringDBunitIntegrationTest
{
    
    
    @Autowired
    private IAlertFinderService finderService;

    @Autowired
    private IKpiLoadingService  kpiLoading;
    
    
    
    @Before
    public void setUp() throws Exception {
    
    
        kpiLoading.initLoadingService();
    }
    
    
    //

    @Test
    @DatabaseSetup("alerts.xml")
    public final void testFindAlerts_with_existing_alerts() throws Exception {
    
    
        final SearchKpiAlertsDto searchAlert = new SearchKpiAlertsDto();
        searchAlert.addAlertKey("COVERAGE_BRANCH_TOO_LOW");
        searchAlert.addEntityKey("KOMEA");
        searchAlert.setActivatedOnly(true);
        searchAlert.setExtendedEntityType(ExtendedEntityType.PROJECT);
        searchAlert.setSeverityMin(Severity.MINOR);
        final List<KpiAlertDto> alerts = finderService.findAlerts(searchAlert);

        Assert.assertFalse(alerts.isEmpty());
    }
    
    
    @Test
    public final void testFindAlerts_with_no_existing_alerts() throws Exception {
    
    
        final SearchKpiAlertsDto searchAlert = new SearchKpiAlertsDto();
        searchAlert.addAlertKey("COVERAGE_BRANCH_TOO_LOW");
        searchAlert.addEntityKey("KOMEA");
        searchAlert.setActivatedOnly(true);
        searchAlert.setExtendedEntityType(ExtendedEntityType.PROJECT);
        searchAlert.setSeverityMin(Severity.MINOR);
        final List<KpiAlertDto> alerts = finderService.findAlerts(searchAlert);

        Assert.assertTrue(alerts.isEmpty());
    }
}
