package org.komea.connectors.jenkins.launch;

import java.util.ArrayList;
import java.util.List;
import org.komea.product.company.database.enums.EntityType;
import org.komea.product.database.enums.GroupFormula;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;

public class JenkinsKpis {

    public static final String BUILDS_TOTAL = "builds_total";
    public static final String BUILDS_FAILED = "builds_failed";
    public static final String BUILDS_INTERRUPTED = "builds_interrupted";
    public static final String BUILDS_SUCCESSFUL = "builds_successful";
    public static final String BUILDS_UNSTABLE = "builds_unstable";

    public static List<Kpi> getKpis() {
        final List<Kpi> kpis = new ArrayList<>();
        kpis.add(buildsFailed());
        kpis.add(buildsInterrupted());
        kpis.add(buildsSuccessful());
        kpis.add(buildsTotal());
        kpis.add(buildsUnstable());
        return kpis;
    }

    private static Kpi buildsTotal() {
        return baseKpi(BUILDS_TOTAL, "Total builds", "Number of total builds of a job");
    }

    private static Kpi buildsFailed() {
        return baseKpi(BUILDS_FAILED, "Failed builds", "Number of failed builds of a job");
    }

    private static Kpi buildsInterrupted() {
        return baseKpi(BUILDS_INTERRUPTED, "Interrupted builds", "Number of interrupted builds of a job");
    }

    private static Kpi buildsSuccessful() {
        return baseKpi(BUILDS_SUCCESSFUL, "Successful builds", "Number of successful builds of a job");
    }

    private static Kpi buildsUnstable() {
        return baseKpi(BUILDS_UNSTABLE, "Unstable builds", "Number of unstable builds of a job");
    }

    private static Kpi baseKpi(final String key, final String name, final String description) {
        final Kpi kpi = new Kpi();
        kpi.setDescription(description);
        kpi.setEntityType(EntityType.PROJECT);
        kpi.setEsperRequest(EmptyKpi.getFormula());
        kpi.setGroupFormula(GroupFormula.SUM_VALUE);
        kpi.setKpiKey(key);
        kpi.setName(name);
        kpi.setProviderType(ProviderType.CI_BUILD);
        kpi.setValueDirection(ValueDirection.NONE);
        kpi.setValueType(ValueType.INT);
        return kpi;
    }

}
