/**
 *
 */

package org.komea.product.wicket.kpiimport;



import java.io.Serializable;

import org.komea.product.backend.service.kpi.KpiDefinition;



public class KpiEntry implements Serializable
{
    
    
    private String        entry;


    private KpiDefinition kpiDefinition;



    /**
     * Returns the value of the field entry.
     *
     * @return the entry
     */
    public String getEntry() {


        return entry;
    }


    /**
     * Returns the value of the field kpiDefinition.
     *
     * @return the kpiDefinition
     */
    public KpiDefinition getKpiDefinition() {


        return kpiDefinition;
    }


    public boolean hasNoDef() {
    
    
        return kpiDefinition == null;
    }


    /**
     * Sets the field entry with the value of _entry.
     *
     * @param _entry
     *            the entry to set
     */
    public void setEntry(final String _entry) {


        entry = _entry;
    }


    /**
     * Sets the field kpiDefinition with the value of _kpiDefinition.
     *
     * @param _kpiDefinition
     *            the kpiDefinition to set
     */
    public void setKpiDefinition(final KpiDefinition _kpiDefinition) {


        kpiDefinition = _kpiDefinition;
    }
}
