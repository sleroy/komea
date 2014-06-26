
package org.komea.product.backend.service.cron;



import java.io.Serializable;
import java.util.Date;

import org.quartz.Trigger.TriggerState;



/**
 * This class defines the cron details.
 *
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class CronDetails implements Serializable
{


    private String       cronExpression;


    private String       cronName;

    private Date         lastTime;

    private Date         nextTime;


    private TriggerState triggerState;



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


    /**
     * Method getCronExpression.
     * 
     * @return String
     */
    public String getCronExpression() {


        return cronExpression;
    }


    /**
     * Method getCronName.
     * 
     * @return String
     */
    public String getCronName() {


        return cronName;
    }


    public Date getLastTime() {


        return lastTime;
    }


    /**
     * Method getNextTime.
     * 
     * @return Date
     */
    public Date getNextTime() {


        return nextTime;
    }


    /**
     * Method getTriggerState.
     * 
     * @return TriggerState
     */
    public TriggerState getTriggerState() {


        return triggerState;
    }


    /**
     * Method setCronExpression.
     * 
     * @param _cronExpression
     *            String
     */
    public void setCronExpression(final String _cronExpression) {


        cronExpression = _cronExpression;
    }


    /**
     * Method setCronName.
     * 
     * @param _cronName
     *            String
     */
    public void setCronName(final String _cronName) {


        cronName = _cronName;
    }


    public void setLastTime(final Date _lastTime) {


        lastTime = _lastTime;
    }


    /**
     * Method setNextTime.
     * 
     * @param _nextTime
     *            Date
     */
    public void setNextTime(final Date _nextTime) {


        nextTime = _nextTime;
    }


    /**
     * Method setStatus.
     * 
     * @param _triggerState
     *            TriggerState
     */
    public void setStatus(final TriggerState _triggerState) {


        triggerState = _triggerState;


    }
    
    
    /**
     * Method setTriggerState.
     * 
     * @param _triggerState
     *            TriggerState
     */
    public void setTriggerState(final TriggerState _triggerState) {


        triggerState = _triggerState;
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
