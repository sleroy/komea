/**
 *
 */

package org.komea.product.wicket.kpiimport;



import java.io.Serializable;



public class KpiEntry implements Serializable
{
    
    
    public enum Status {
        ERROR, IMPORTED, NO_IMPORT, UPDATED
    }
    
    
    
    private String entry;
    
    
    private String kpiName;
    
    
    private Status status;



    /**
     * Returns the value of the field entry.
     *
     * @return the entry
     */
    public String getEntry() {
    
    
        return entry;
    }


    /**
     * Returns the value of the field kpiName.
     *
     * @return the kpiName
     */
    public String getKpiName() {
    
    
        return kpiName;
    }
    
    
    /**
     * Returns the value of the field status.
     *
     * @return the status
     */
    public Status getStatus() {
    
    
        return status;
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
     * Sets the field kpiName with the value of _kpiName.
     *
     * @param _kpiName
     *            the kpiName to set
     */
    public void setKpiName(final String _kpiName) {
    
    
        kpiName = _kpiName;
    }
    
    
    /**
     * Sets the field status with the value of _status.
     *
     * @param _status
     *            the status to set
     */
    public void setStatus(final Status _status) {
    
    
        status = _status;
    }
    
    
}
