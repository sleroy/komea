/**
 *
 */

package org.komea.product.backend.service.kpi;

import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;

/**
 * @author sleroy
 */
public class KpiBuilder {

	public static KpiBuilder create() {

		return new KpiBuilder();
	}

	public static KpiBuilder createAscending() {

		final KpiBuilder kpiBuilder = new KpiBuilder();
		kpiBuilder.kpi.setValueMin(Double.MIN_VALUE);
		kpiBuilder.kpi.setValueMax(Double.MAX_VALUE);
		kpiBuilder.kpi.setValueDirection(ValueDirection.BETTER);
		kpiBuilder.kpi.setValueType(ValueType.INT);
		return kpiBuilder;
	}

	private final Kpi	kpi	= new Kpi();

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

	public KpiBuilder query(final Class<? extends ICEPQueryImplementation> _query) {

		kpi.setEsperRequest("new " + _query.getName() + "()");

		return this;
	}

	/**
	 * @param _query
	 * @return
	 */
	public KpiBuilder query(final String _query) {

		kpi.setEsperRequest(_query);
		return this;
	}
}
