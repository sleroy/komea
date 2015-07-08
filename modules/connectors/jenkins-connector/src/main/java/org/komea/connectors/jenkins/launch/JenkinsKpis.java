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
    public static final String JOBS = "jobs";
    public static final String JOBS_EXECUTED = "jobs_executed";
    public static final String JOBS_NOT_EXECUTED = "jobs_not_executed";
    public static final String FAILED_STATUS_DURATION = "failed_status_duration";
    public static final String LAST_BUILDS_FAILED = "last_builds_failed";
    public static final String FIX_TIME = "fix_time";

    public static List<Kpi> getKpis() {
        final List<Kpi> kpis = new ArrayList<>();
        kpis.add(buildsFailed());
        kpis.add(buildsInterrupted());
        kpis.add(buildsSuccessful());
        kpis.add(buildsTotal());
        kpis.add(buildsUnstable());
        kpis.add(nbJobs());
        kpis.add(nbExecutedJobs());
        kpis.add(nbNotExecutedJobs());
        kpis.add(failedStatusDuration());
        kpis.add(lastBuildsFailed());
        kpis.add(fixTime());
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

    private static Kpi nbJobs() {
        return baseKpi(JOBS, "Jobs", "Number of jobs");
    }

    private static Kpi nbExecutedJobs() {
        return baseKpi(JOBS_EXECUTED, "Jobs executed", "Number of jobs executed");
    }

    private static Kpi nbNotExecutedJobs() {
        return baseKpi(JOBS_NOT_EXECUTED, "Jobs not executed", "Number of jobs not executed");
    }

    private static Kpi lastBuildsFailed() {
        return baseKpi(LAST_BUILDS_FAILED, "Last failed builds", "Number of last failed builds",
                GroupFormula.SUM_VALUE, ValueDirection.WORST);
    }

    private static Kpi failedStatusDuration() {
        return baseKpi(FAILED_STATUS_DURATION, "Failed duration (days)",
                "Number of days since the job is in failed status.",
                GroupFormula.AVG_VALUE, ValueDirection.WORST, ValueType.FLOAT);
    }

    private static Kpi fixTime() {
        return baseKpi(FIX_TIME, "Time to fix builds (days)",
                "Average time in days for fixing a failed build.",
                GroupFormula.AVG_VALUE, ValueDirection.WORST, ValueType.FLOAT);
    }

    private static Kpi baseKpi(final String key, final String name, final String description,
            final GroupFormula groupFormula, final ValueDirection valueDirection) {
        return baseKpi(key, name, description, groupFormula, valueDirection, ValueType.INT);
    }

    private static Kpi baseKpi(final String key, final String name, final String description,
            final GroupFormula groupFormula, final ValueDirection valueDirection,
            final ValueType valueType) {
        final Kpi kpi = new Kpi();
        kpi.setDescription(description);
        kpi.setEntityType(EntityType.PROJECT);
        kpi.setEsperRequest(EmptyKpi.getFormula());
        kpi.setGroupFormula(groupFormula);
        kpi.setKpiKey(key);
        kpi.setName(name);
        kpi.setProviderType(ProviderType.CI_BUILD);
        kpi.setValueDirection(valueDirection);
        kpi.setValueType(valueType);
        return kpi;
    }

    private static Kpi baseKpi(final String key, final String name, final String description) {
        return baseKpi(key, name, description, GroupFormula.SUM_VALUE, ValueDirection.NONE);
    }

}
