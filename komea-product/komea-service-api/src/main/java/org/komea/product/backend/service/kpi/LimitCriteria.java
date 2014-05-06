
package org.komea.product.backend.service.kpi;


import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;

public final class LimitCriteria {
    
    //
    private final int  limitNumber;
    
    private final Date startDate;
    
    private final Date endDate;
    
    /**
     * This method create a criteria with limit date between 1/01/0000 and now. with a limit of 1 value per entity
     * 
     * @return
     */
    public static LimitCriteria createDefaultLimitCriteria() {
    
        Date endDate = Calendar.getInstance().getTime();
        Date startDate = new DateTime(0, 1, 1, 0, 0, 0).toDate();
        return new LimitCriteria(startDate, endDate, 1);
    }
    
    /**
     * This method create a criteria with limit date between 1/01/0000 and now. with a limit of : value per entity
     * 
     * @param _limitNumber
     *            the limit number of values / entities
     * @return the criteria
     */
    public static LimitCriteria CreateLimitCriteriaForNValues(final int _limitNumber) {
    
        Date endDate = Calendar.getInstance().getTime();
        Date startDate = new DateTime(0, 1, 1, 0, 0, 0).toDate();
        return new LimitCriteria(startDate, endDate, _limitNumber);
    }
    
    /**
     * This method create a criteria with limit date between a start Date and and now. with a limit of : value per entity
     * 
     * @param _startDate
     *            the start date to find the historical values
     * @param _limitNumber
     *            the limit number of values / entities
     * @return the criteria
     */
    public static LimitCriteria CreateLimitCriteriaFromStartingDate(final Date _startDate, final int _limitNumber) {
    
        return new LimitCriteria(_startDate, Calendar.getInstance().getTime(), _limitNumber);
    }
    
    /**
     * This method create a criteria with limit date between a start Date and an end date. with a limit of : value per entity
     * 
     * @param _startDate
     *            the start date to find the historical values
     * @param _endDate
     *            the end date to find the historical values
     * @param _limitNumber
     *            the limit number of values / entities
     * @return the criteria
     */
    public static LimitCriteria CreateLimitCriteria(final Date _startDate, final Date _endDate, final int _limitNumber) {
    
        return new LimitCriteria(_startDate, _endDate, _limitNumber);
    }
    
    private LimitCriteria(final Date _startDate, final Date _endDate, final int _limitNumber) {
    
        super();
        limitNumber = _limitNumber;
        startDate = _startDate;
        endDate = _endDate;
    }
    //
    
    public int getLimitNumber() {
    
        return limitNumber;
    }
    
    public Date getStartDate() {
    
        return startDate;
    }
    
    public Date getEndDate() {
    
        return endDate;
    }
}
