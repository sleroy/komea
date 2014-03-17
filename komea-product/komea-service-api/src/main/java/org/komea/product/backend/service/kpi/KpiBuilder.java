/**
 *
 */

package org.komea.product.backend.service.kpi;



import org.komea.product.cep.api.ICEPQueryImplementation;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.EvictionType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;



/**
 * @author sleroy
 */
public class KpiBuilder
{
    
    
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
    
    
    
    private final Kpi kpi = new Kpi();
    
    
    
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
    
    
    public KpiBuilder cron(final String _cronExpr) {
    
    
        kpi.setCronExpression(_cronExpr);
        
        return this;
    }
    
    
    public KpiBuilder cronBeginMonth() {
    
    
        kpi.setCronExpression("0 0 12 ? 1/1 MON#1 *");
        return this;
    }
    
    
    /**
     * @param _i
     * @return
     */
    public KpiBuilder cronDays(final int _i) {
    
    
        kpi.setCronExpression("0 0 0 /" + _i + " * ? *");
        return this;
    }
    
    
    public KpiBuilder cronFiveMinutes() {
    
    
        kpi.setCronExpression("0 0/5 * * * ? *");
        return this;
        
    }
    
    
    /**
     *
     */
    public KpiBuilder cronOneDay() {
    
    
        cronDays(1);
        return this;
        
    }
    
    
    /**
     *
     */
    public KpiBuilder cronOneHours() {
    
    
        kpi.setCronExpression("0 0 * * * ? *");
        return this;
        
    }
    
    
    /**
     *
     */
    public KpiBuilder cronSixHours() {
    
    
        kpi.setCronExpression("0 0 0/6 * * ? *");
        return this;
        
    }
    
    
    /**
     * @return
     */
    public KpiBuilder cronThreeDays() {
    
    
        cronDays(3);
        return this;
        
    }
    
    
    /**
     * Returns the kpi builder.
     * 
     * @return the kpi builder.
     */
    public KpiBuilder cronWeek() {
    
    
        kpi.setCronExpression("0 0 0 ? * MON *");
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
    
    
    public KpiBuilder expirationMonth() {
    
    
        kpi.setEvictionRate(1);
        kpi.setEvictionType(EvictionType.MONTHS);
        return this;
    }
    
    
    public KpiBuilder expirationWeek() {
    
    
        kpi.setEvictionRate(7);
        kpi.setEvictionType(EvictionType.DAYS);
        return this;
    }
    
    
    public KpiBuilder expirationYear() {
    
    
        kpi.setEvictionRate(12);
        kpi.setEvictionType(EvictionType.MONTHS);
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
