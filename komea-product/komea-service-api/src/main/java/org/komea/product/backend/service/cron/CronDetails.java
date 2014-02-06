
package org.komea.product.backend.service.cron;



import java.util.Date;



/**
 * This class defines the cron details.
 * 
 * @author sleroy
 */
public class CronDetails
{
    
    
    private String cronName;
    
    
    private String cronExpression;
    
    private Date   nextTime;
    
    
    
    public CronDetails() {
    
    
        super();
    }
    
    
    /**
     * @param _cronName
     * @param _cronExpression
     */
    public CronDetails(final String _cronName, final String _cronExpression) {
    
    
        super();
        cronName = _cronName;
        cronExpression = _cronExpression;
    }
    
    
    public String getCronExpression() {
    
    
        return cronExpression;
    }
    
    
    public String getCronName() {
    
    
        return cronName;
    }
    
    
    public Date getNextTime() {
    
    
        return nextTime;
    }
    
    
    public void setCronExpression(final String _cronExpression) {
    
    
        cronExpression = _cronExpression;
    }
    
    
    public void setCronName(final String _cronName) {
    
    
        cronName = _cronName;
    }
    
    
    public void setNextTime(final Date _nextTime) {
    
    
        nextTime = _nextTime;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "CronDetails [cronName=" + cronName + ", cronExpression=" + cronExpression + "]";
    }
}
