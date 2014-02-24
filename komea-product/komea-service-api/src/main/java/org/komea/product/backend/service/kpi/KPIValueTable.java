
package org.komea.product.backend.service.kpi;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.komea.product.database.api.IEntity;
import org.komea.product.database.model.Kpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 */
public class KPIValueTable<T extends IEntity> implements Serializable
{
    
    
    private static final Logger   LOGGER = LoggerFactory.getLogger("kpi-results");
    private Kpi                   kpi;
    
    private Number                singleValue;
    
    private List<KpiLineValue<T>> values = new ArrayList<KpiLineValue<T>>(50);
    
    
    
    public KPIValueTable() {
    
    
        super();
    }
    
    
    /**
     * @param _kpi
     * @param _values
     */
    public KPIValueTable(final Kpi _kpi, final List<KpiLineValue<T>> _values) {
    
    
        super();
        kpi = _kpi;
        values = _values;
    }
    
    
    /**
     * Dump Informations
     */
    public void dump() {
    
    
        if (!values.isEmpty()) {
            for (final KpiLineValue value : values) {
                LOGGER.info("kpi|{}|\t{}\t{}", kpi.getKpiKey(), value.getEntity(), value.getValue());
            }
        } else {
            LOGGER.info("kpi|{}| has no result", kpi.getKpiKey());
        }
        
    }
    
    
    /**
     * @return the kpi
     */
    public Kpi getKpi() {
    
    
        return kpi;
    }
    
    
    /**
     * @return
     */
    public int getNumberOfRecords() {
    
    
        return this.values.size();
    }
    
    
    public Number getSingleValue() {
    
    
        return singleValue;
    }
    
    
    /**
     * @return the values
     */
    public List<KpiLineValue<T>> getValues() {
    
    
        return values;
    }
    
    
    /**
     * @param _kpi
     *            the kpi to set
     */
    public void setKpi(final Kpi _kpi) {
    
    
        kpi = _kpi;
    }
    
    
    public void setSingleValue(final Number _singleValue) {
    
    
        singleValue = _singleValue;
    }
    
    
    /**
     * @param _values
     *            the values to set
     */
    public void setValues(final List<KpiLineValue<T>> _values) {
    
    
        values = _values;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "KPIValueTable [kpi="
                + kpi + ", singleValue=" + singleValue + ", values=" + values + "]";
    }
    
}
