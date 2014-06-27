/**
 *
 */

package org.komea.product.model.timeserie.table.dto;



import java.util.Collections;
import java.util.Date;
import java.util.List;



/**
 * @author sleroy
 */
public class TimeSerieOptionsDTO
{


    private Date         beginDate = null;


    private Date         endDate   = null;


    private List<String> kpiNames  = Collections.EMPTY_LIST;
    
    
    
    /**
     * Returns the value of the field beginDate.
     *
     * @return the beginDate
     */
    public Date getBeginDate() {


        return beginDate;
    }


    /**
     * Returns the value of the field endDate.
     *
     * @return the endDate
     */
    public Date getEndDate() {


        return endDate;
    }


    /**
     * Returns the value of the field kpiNames.
     *
     * @return the kpiNames
     */
    public List<String> getKpiNames() {


        return kpiNames;
    }


    /**
     * @return
     */
    public boolean hasValidPeriod() {
    
    
        return beginDate != null && endDate != null;
    }
    
    
    /**
     * Sets the field beginDate with the value of _beginDate.
     *
     * @param _beginDate
     *            the beginDate to set
     */
    public void setBeginDate(final Date _beginDate) {


        beginDate = _beginDate;
    }
    
    
    /**
     * Sets the field endDate with the value of _endDate.
     *
     * @param _endDate
     *            the endDate to set
     */
    public void setEndDate(final Date _endDate) {


        endDate = _endDate;
    }
    
    
    /**
     * Sets the field kpiNames with the value of _kpiNames.
     *
     * @param _kpiNames
     *            the kpiNames to set
     */
    public void setKpiNames(final List<String> _kpiNames) {


        kpiNames = _kpiNames;
    }


    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {


        return "TimeSerieOptionsDTO [\\n\\tbeginDate="
                + beginDate + ", \\n\\tendDate=" + endDate + ", \\n\\tkpiNames=" + kpiNames + "]";
    }
}
