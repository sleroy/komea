
package org.komea.product.backend.service.kpi;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.komea.product.database.model.Kpi;



public class KPIValueTable<T> implements Serializable
{
    
    
    private Kpi                   kpi;
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
     * @return the kpi
     */
    public Kpi getKpi() {
    
    
        return kpi;
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
    
    
        return "KPIValueTable [kpi=" + kpi + ", values=" + values + "]";
    }
    
}
