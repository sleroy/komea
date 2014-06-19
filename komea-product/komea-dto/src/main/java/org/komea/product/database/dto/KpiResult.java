/**
 *
 */
package org.komea.product.database.dto;

import com.google.common.collect.Maps;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.Validate;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.komea.product.backend.groovy.QueryExecutionFailed;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.GroupFormula;
import org.komea.product.model.timeserie.EntityIdValue;
import org.komea.product.service.dto.EntityKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This object defines the container that stores the results of the execution of
 * a kpi.
 *
 * @author sleroy
 */
public class KpiResult implements Serializable {

    public static final KpiResult EMPTY = new KpiResult() {

        /**
         * Puts a value into the map.
         *
         * @param _entityKey the entity key
         * @param _value the value;
         */
        @Override
        public void put(
                final EntityKey _entityKey,
                final Number _value) {

            throw new IllegalAccessError();

        }
    };

    private static final Logger LOGGER = LoggerFactory.getLogger(KpiResult.class);

    private static final long serialVersionUID = -5012854632976327222L;

    public static KpiResult newResults(final Map<EntityKey, Number> _results) {

        final KpiResult kpiResult = new KpiResult();
        for (final Entry<EntityKey, Number> entry : _results.entrySet()) {
            kpiResult.put(entry.getKey(), entry.getValue());
        }
        return kpiResult;
    }

    public static KpiResult newResults2(final Map<IEntity, Number> _results) {

        final KpiResult kpiResult = new KpiResult();
        for (final Entry<IEntity, Number> entry : _results.entrySet()) {
            kpiResult.put(entry.getKey(), entry.getValue());
        }
        return kpiResult;
    }

    private Map<EntityKey, Number> map = Maps.newHashMap();

    private Throwable reasonOfFailure = null;

    public KpiResult() {

        super();
    }

    public KpiResult(final Map<EntityKey, Number> _map) {

        super();
        map = _map;

    }

    /**
     * Computes the unique value from a group formula;
     *
     * @param _groupFormula the group formula.
     * @return the group formula.
     */
    public Double computeUniqueValue(final GroupFormula _groupFormula) {

        checkNoFail();

        switch (_groupFormula) {
            case COUNT:
                return Integer.valueOf(size()).doubleValue();
            case AVG_VALUE:
                return buildMean();
            case SUM_VALUE:
                return buildSum();
            default:
                throw new UnsupportedOperationException("Unknown formula");
        }
    }

    /**
     * Fills the kpi result with an map and converts it into entity keys.
     *
     * @param _evaluateKpiValues
     * @param _entityType
     * @return
     */
    public KpiResult fill(final List<EntityIdValue> _evaluateKpiValues, final EntityType _entityType) {

        checkNoFail();
        map = Maps.newHashMapWithExpectedSize(_evaluateKpiValues.size());
        for (final EntityIdValue entry : _evaluateKpiValues) {
            put(EntityKey.of(_entityType, entry.getEntityID()), entry.getValue());
        }

        return this;
    }

    /**
     * Returns the value as a double value.
     *
     * @param _entityKey the entity key.
     * @return the double value converted from the original value.
     */
    public Double getDoubleValue(final EntityKey _entityKey) {

        checkNoFail();
//        return getValue(_entityKey).doubleValue();
        final Number value = getValue(_entityKey);
        return value == null ? null : value.doubleValue();
    }

    /**
     * Returns the numerical value as a double or null if not present.
     *
     * @param _entityKey the entity key
     * @return a double value or null.
     */
    public Double getDoubleValueOrNull(final EntityKey _entityKey) {

        checkNoFail();
        final Number number = map.get(_entityKey);

        if (number == null) {
            return null;
        }
        return number.doubleValue();
    }

    public Map<EntityKey, Number> getMap() {

        checkNoFail();

        return Collections.unmodifiableMap(map);
    }

    public Throwable getReasonOfFailure() {

        return reasonOfFailure;
    }

    /**
     * Returns the value of an entity.
     *
     * @param _entityKey the entity key.
     * @return the value.
     */
    public Number getValue(final EntityKey _entityKey) {

        checkNoFail();
        return map.get(_entityKey);
//        final Number number = map.get(_entityKey);
//        if (number == null) {
//            return 0d;
//        }
//        return number;
    }

    public boolean hasFailed() {

        return reasonOfFailure != null;
    }

    /**
     * Marks the result has failed.
     *
     * @param _reasonOfFailure the result;
     */
    public void hasFailed(final Throwable _reasonOfFailure) {

        reasonOfFailure = _reasonOfFailure;

    }

    /**
     * Tests if a key is present into the kpi result
     *
     * @param _entityKey
     * @return
     */
    public boolean hasKey(final EntityKey _entityKey) {

        return map.containsKey(_entityKey);
    }

    /**
     * Generate minimal values when entities are missing.
     *
     * @param entitiesByEntityType the list of entities.
     * @return the kpi result after modification
     */
    public KpiResult inferResults(final List<IEntity> entitiesByEntityType, final Double _minValue) {

        checkNoFail();
        final KpiResult kpiResult = new KpiResult(new HashMap<EntityKey, Number>(map));
        if (hasFailed()) {
            kpiResult.hasFailed(reasonOfFailure);
            return kpiResult;
        }
        LOGGER.trace("From {}", kpiResult.map);
        for (final IEntity entity : entitiesByEntityType) {
            final EntityKey entityKey = entity.getEntityKey();
            if (!kpiResult.map.containsKey(entityKey)) {
                kpiResult.put(entityKey, _minValue);
            }
        }
        LOGGER.trace("To {}", kpiResult.map);
        return kpiResult;
    }

    /**
     * Tests if the result of the kpi is empty
     *
     * @return
     */
    public boolean isEmpty() {

        return map.isEmpty();
    }

    /**
     * Puts a value into the map.
     *
     * @param _entityKey the entity key
     * @param _value the value;
     */
    public void put(final EntityKey _entityKey, final Number _value) {

        checkNoFail();
        if (_entityKey == null) {
            LOGGER.error("Trying to store values for a null key, ignoring the value {}", _value);
            return;
        }
        Validate.isTrue(_entityKey.isEntityReferenceKey());
        map.put(_entityKey, _value);

    }

    /**
     * @param _entity
     * @param _value
     */
    public void put(final IEntity _entity, final Number _value) {

        checkNoFail();
        Validate.notNull(_entity);
        Validate.notNull(_value);
        put(_entity.getEntityKey(), _value);

    }

    public void putResults(final Map<IEntity, Number> _results) {

        checkNoFail();

        for (final Entry<IEntity, Number> entry : _results.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }

    }

    /**
     * Returns the size of the results stored
     */
    public int size() {

        checkNoFail();
        return map.size();
    }


    /**
     * @return Returns a smart display of the kpi result.
     */
    public String smartDisplay() {


        if (hasFailed()) {
            return "##ERROR##";
        }
        final StringBuilder sbBuilder = new StringBuilder();
        sbBuilder.append("EntityKey;Value\n");
        for (final Entry<EntityKey, Number> entry : map.entrySet()) {
            sbBuilder.append(entry.getKey().toString()).append(";").append(entry.getValue())
            .append("\n");
        }

        return sbBuilder.toString();
    }


    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "KpiResult [\\n\\tmap=" + map + ", \\n\\treasonOfFailure=" + reasonOfFailure + "]";
    }

    private Double buildMean() {

        {
            return buildSum();
        }
    }

    private Double buildSum() {

        final DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
        for (final Number dbValue : map.values()) {
            descriptiveStatistics.addValue(dbValue.doubleValue());
        }
        return descriptiveStatistics.getSum();
    }

    /**
     * Check if no failure happened.
     */
    private void checkNoFail() {

        if (hasFailed()) {
            throw new QueryExecutionFailed(reasonOfFailure);
        }

    }
}
