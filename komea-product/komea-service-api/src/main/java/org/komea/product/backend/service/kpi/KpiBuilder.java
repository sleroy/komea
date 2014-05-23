/**
 *
 */
package org.komea.product.backend.service.kpi;

import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.api.engine.IDynamicDataQuery;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.GroupFormula;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sleroy
 */
public class KpiBuilder {

    public static KpiBuilder create() {

        return new KpiBuilder().groupFormula(GroupFormula.AVG_VALUE);
    }

    public static KpiBuilder createAscending() {

        final KpiBuilder kpiBuilder = create();
        kpiBuilder.kpi.setValueMin(0d);
        kpiBuilder.kpi.setValueMax(Double.valueOf(Integer.MAX_VALUE));
        kpiBuilder.kpi.setValueDirection(ValueDirection.NONE);
        kpiBuilder.kpi.setValueType(ValueType.FLOAT);
        return kpiBuilder;
    }

    private final Kpi kpi = new Kpi();

    private static final Logger LOGGER = LoggerFactory.getLogger(KpiBuilder.class);

    /**
     *
     */
    private KpiBuilder() {

        super();
    }

    /**
     *
     */
    public Kpi build() {

        return kpi;

    }

    public KpiBuilder cronBeginMonth() {

        kpi.setCronExpression(BackupDelay.MONTH.name());
        return this;
    }

    /**
     *
     */
    public KpiBuilder cronOneDay() {

        kpi.setCronExpression(BackupDelay.DAY.name());
        return this;

    }

    /**
     *
     */
    public KpiBuilder cronOneHours() {

        kpi.setCronExpression(BackupDelay.HOUR.name());
        return this;

    }

    /**
     * Returns the kpi builder.
     *
     * @return the kpi builder.
     */
    public KpiBuilder cronWeek() {

        kpi.setCronExpression(BackupDelay.WEEK.name());
        return this;
    }

    /**
     * Defines the kpi as a daily kpi (cron 1 day, expiration year) Retruns the
     * kpi builder
     *
     * @return
     */
    public KpiBuilder dailyKPI() {

        kpi.setCronExpression(BackupDelay.DAY.name());
        return this;
    }

    public KpiBuilder description(final String _description) {

        kpi.setDescription(_description);
        return this;
    }

    public KpiBuilder dynamicQuery(final Class<? extends IDynamicDataQuery> _query) {

        kpi.setEsperRequest("new " + _query.getName() + "()");

        return this;
    }

    public KpiBuilder entityType(final EntityType _entityType) {

        kpi.setEntityType(_entityType);
        return this;
    }

    public KpiBuilder forProject() {

        kpi.setEntityType(EntityType.PROJECT);
        return this;
    }

    public KpiBuilder hourly() {
        kpi.setCronExpression(BackupDelay.HOUR.name());
        return this;
    }

    /**
     * @param _minValue
     * @param _maxValue
     * @return
     */
    public KpiBuilder interval(final Double _minValue, final Double _maxValue) {

        kpi.setValueMin(_minValue);
        kpi.setValueMax(_maxValue);
        kpi.setValueDirection(_minValue < _maxValue ? ValueDirection.BETTER : ValueDirection.WORST);
        return this;
    }

    public KpiBuilder key(final String _key) {

        kpi.setKpiKey(_key);

        return this;
    }

    public KpiBuilder name(final String _name) {

        kpi.setName(_name);

        return this;
    }

    /**
     * Name and key generated from name.
     *
     * @param _string
     * @return
     */
    public KpiBuilder nameAndKey(final String _string) {

        kpi.setName(_string);
        kpi.setKpiKey(_string.toUpperCase().replace(' ', '_'));
        return this;
    }

    /**
     * @param _string
     * @return
     */
    public KpiBuilder nameAndKeyDescription(final String _string) {

        nameAndKey(_string);
        kpi.setDescription(_string);
        return this;
    }

    public KpiBuilder produceValue(final ValueType _valueType, final ValueDirection _valueDirection) {

        kpi.setValueDirection(_valueDirection);
        kpi.setValueType(_valueType);
        return this;
    }

    public KpiBuilder providerType(final ProviderType _providerType) {

        kpi.setProviderType(_providerType);

        return this;
    }

    public KpiBuilder groupFormula(final GroupFormula _groupFormula) {

        kpi.setGroupFormula(_groupFormula);

        return this;
    }

    public KpiBuilder query(final Class<? extends ICEPQueryImplementation> _query) {

        String script = "##notloaded##";
        try {
            final GroovyScriptLoader groovyScriptLoader = new GroovyScriptLoader(Thread.currentThread()
                    .getContextClassLoader(), "org/komea/product/backend/service/kpi/cepQueryScript.groovy");
            groovyScriptLoader.addParameter("##KPI##", _query.getSimpleName() + "Script");
            groovyScriptLoader.addParameter("##QUERY##", _query.getCanonicalName());
            script = groovyScriptLoader.load();
            kpi.setEsperRequest(script);
        } catch (final Exception e) {
            LOGGER.error("Impossible to retrieve Groovy script template : script {}", script, e);
        }

        return this;
    }

    public KpiBuilder queryScript(final String _groovyScript) {
        kpi.setEsperRequest(_groovyScript);
        return this;
    }
}
