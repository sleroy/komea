
package org.komea.product.service.dto;


import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;

public final class LimitCriteria {
    
    /**
     * This method create a criteria with limit date between 1/01/0000 and now. with a limit of Integer.MAX_VALUE value per entity
     *
     * @return
     */
    public static LimitCriteria createDefaultLimitCriteria() {
    
        Date endDate = Calendar.getInstance().getTime();
        Date startDate = new DateTime(0, 1, 1, 0, 0, 0).toDate();
        return new LimitCriteria(startDate, endDate, Integer.MAX_VALUE);
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
    
    //
    private Integer limitNumber;
    
    private Date    startDate;
    
    private Date    endDate;
    
    public LimitCriteria() {
    
        // TODO Auto-generated LimitCriteria stub
    }
    
    private LimitCriteria(final Date _startDate, final Date _endDate, final int _limitNumber) {
    
        super();
        limitNumber = _limitNumber;
        startDate = _startDate;
        endDate = _endDate;
    }
    //
    
    public Integer getLimitNumber() {
    
        return limitNumber;
    }
    
    public void setLimitNumber(final Integer _limitNumber) {
    
        limitNumber = _limitNumber;
    }
    
    public Date getStartDate() {
    
        return startDate;
    }
    
    public void setStartDate(final Date _startDate) {
    
        startDate = _startDate;
    }
    
    public Date getEndDate() {
    
        return endDate;
    }
    
    public void setEndDate(final Date _endDate) {
    
        endDate = _endDate;
    }
    
}
