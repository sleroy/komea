/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.bugtracker.service;



import javax.annotation.PostConstruct;

import org.komea.product.backend.api.IGroovyEngineService;
import org.komea.product.backend.service.kpi.GroovyScriptLoader;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.KpiBuilder;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * This class defines the bugzilla provider plugin. Informations are collected
 * at runtime.
 *
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
@Service
public class IssueKpiPlugin
{


    public static final String   BUGZILLA_PROVIDER_PLUGIN = "BugZilla Provider plugin";

    private static final Logger  LOGGER                   = LoggerFactory
            .getLogger(IssueKpiPlugin.class);


    @Autowired
    private IGroovyEngineService groovyEngineService;

    @Autowired
    private IKPIService          kpiService;



    public Kpi closedBugsKpi() {


        return createKpi("bugs_status_closed", "Closed bugs", "Number of closed bugs per project",
                ValueDirection.BETTER, new GroovyScriptLoader(
                        "scripts/ProjectClosedBugScript.groovy").load());
    }


    public IGroovyEngineService getGroovyEngineService() {


        return groovyEngineService;
    }


    public IKPIService getKpiService() {


        return kpiService;
    }


    @PostConstruct
    public void init() {


        LOGGER.info("Loading bugZilla plugin");
        groovyEngineService.registerStarImport("org.komea.product.plugins.bugtracker.kpis");
        groovyEngineService.registerStarImport("org.komea.product.plugins.bugtracking.model");

        addKpi(totalBugsKpi());
        addKpi(openBugsKpi());
        addKpi(openNotFixedBugsKpi());
        addKpi(closedBugsKpi());
        for (final Severity severity : Severity.values()) {
            addKpi(openBySeverityBugsKpi(severity.name()));
        }

    }


    public Kpi openBugsKpi() {


        return createKpi("bugs_status_open", "Open bugs", "Number of open bugs per project",
                ValueDirection.WORST,
                new GroovyScriptLoader("scripts/ProjectOpenBugScript.groovy").load());
    }


    public Kpi openBySeverityBugsKpi(final String severity) {


        final GroovyScriptLoader groovyScriptLoader =
                new GroovyScriptLoader("scripts/ProjectOpenBySeverityBugs.groovy");
        groovyScriptLoader.addParameter("#SEV#", severity);
        return createKpi("bugs_status_open_severity_" + severity, "Open " + severity + " bugs",
                "Number of open bugs with " + severity + " severity", ValueDirection.WORST,
                groovyScriptLoader.load());
    }


    public Kpi openNotFixedBugsKpi() {


        return createKpi("bugs_status_open_not_fixed", "Open NOT fixed bugs",
                "Number of open but not fixed bugs", ValueDirection.WORST, new GroovyScriptLoader(
                        "scripts/ProjectOpenNotFixedBugs.groovy").load());
    }


    public void setGroovyEngineService(final IGroovyEngineService _groovyEngineService) {


        groovyEngineService = _groovyEngineService;
    }
    
    
    public void setKpiService(final IKPIService _kpiService) {


        kpiService = _kpiService;
    }
    
    
    public Kpi totalBugsKpi() {


        return createKpi("bugs_total", "Total bugs", "Total number of bugs per project",
                ValueDirection.WORST,
                new GroovyScriptLoader("scripts/ProjectTotalBugs.groovy").load());
    }
    
    
    private void addKpi(final Kpi _kpi) {


        if (!kpiService.exists(_kpi.getKpiKey())) {
            kpiService.saveOrUpdate(_kpi);
        }
    }
    
    
    private Kpi createKpi(
            final String key,
            final String name,
            final String description,
            final ValueDirection valueDirection,
            final String formula) {


        return KpiBuilder.create().name(name).key(key).description(description).interval(0d, 1000d)
                .providerType(ProviderType.BUGTRACKER).queryScript(formula).forProject()
                .produceValue(ValueType.INT, valueDirection).build();

    }
}
