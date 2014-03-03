/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.backend.plugins.bugzilla.api;



import org.komea.product.database.dto.EventSimpleDto;



/**
 * @author rgalerme
 */
public interface IBugZillaAlertFactory
{
    
    
    /**
     * @param _alert
     * @param _project
     * @return
     */
    public EventSimpleDto newAssignedBugs(long _alert, String _project);
    
    
    /**
     * @param _alert
     * @param _project
     * @return
     */
    public EventSimpleDto newNewBug(long _alert, String _project);
    
    
    /**
     * @param _alert
     * @param _project
     * @return
     */
    public EventSimpleDto newNewBugs(long _alert, String _project);
    
    
    /**
     * @param _alert
     * @param _project
     * @return
     */
    public EventSimpleDto newReadyBugs(long _alert, String _project);
    
    
    /**
     * @param _alert
     * @param _project
     * @return
     */
    public EventSimpleDto newReminterBugs(long _alert, String _project);
    
    
    /**
     * @param _alert
     * @param _project
     * @return
     */
    public EventSimpleDto newReopenedBugs(long _alert, String _project);
    
    
    /**
     * @param _alert
     * @param _project
     * @return
     */
    public EventSimpleDto newResolvedBugs(long _alert, String _project);
    
    
    public EventSimpleDto newStatusBug(long _alert, String _project, String _status);
    
    
    /**
     * @param _alert
     * @param _project
     * @return
     */
    public EventSimpleDto newTotalBugs(long _alert, String _project);
    
    
    /**
     * @param _alert
     * @param _project
     * @return
     */
    public EventSimpleDto newUnconfirmedBugs(long _alert, String _project);
    
    
    /**
     * @param _alert
     * @param _project
     * @return
     */
    public EventSimpleDto newUpdatedBugs(long _alert, String _project);
    
    
    /**
     * @param _alert
     * @param _project
     * @return
     */
    public EventSimpleDto newVerifiedBugs(long _alert, String _project);
    
}
