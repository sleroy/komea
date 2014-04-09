/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.bugzilla.core;



import java.util.Date;

import org.komea.product.plugins.bugzilla.api.IBugZillaAlertFactory;
import org.komea.product.database.dto.EventSimpleDto;
import org.springframework.stereotype.Service;



/**
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
@Service
public class BugZillaAlertFactory implements IBugZillaAlertFactory
{
    
    
    /**
     * Method newAssignedBugs.
     * @param _alert long
     * @param _project String
     * @return EventSimpleDto
     * @see org.komea.backend.plugins.bugzilla.api.IBugZillaAlertFactory#newAssignedBugs(long, String)
     */
    @Override
    public EventSimpleDto newAssignedBugs(final long _alert, final String _project) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of assigned bugs in BugZilla Project");
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        
        event.setDate(new Date());
        
        return event;
    }
    
    
    /**
     * Method newNewBug.
     * @param _alert long
     * @param _project String
     * @return EventSimpleDto
     * @see org.komea.backend.plugins.bugzilla.api.IBugZillaAlertFactory#newNewBug(long, String)
     */
    @Override
    public EventSimpleDto newNewBug(final long _alert, final String _project) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of new bugs in BugZilla Project");
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        
        event.setDate(new Date());
        
        return event;
    }
    
    
    /**
     * Method newNewBugs.
     * @param _alert long
     * @param _project String
     * @return EventSimpleDto
     * @see org.komea.backend.plugins.bugzilla.api.IBugZillaAlertFactory#newNewBugs(long, String)
     */
    @Override
    public EventSimpleDto newNewBugs(final long _alert, final String _project) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of new type bugs in BugZilla Project");
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        
        event.setDate(new Date());
        
        return event;
    }
    
    
    /**
     * Method newReadyBugs.
     * @param _alert long
     * @param _project String
     * @return EventSimpleDto
     * @see org.komea.backend.plugins.bugzilla.api.IBugZillaAlertFactory#newReadyBugs(long, String)
     */
    @Override
    public EventSimpleDto newReadyBugs(final long _alert, final String _project) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of ready bugs in BugZilla Project");
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        
        event.setDate(new Date());
        
        return event;
    }
    
    
    /**
     * Method newReminterBugs.
     * @param _alert long
     * @param _project String
     * @return EventSimpleDto
     * @see org.komea.backend.plugins.bugzilla.api.IBugZillaAlertFactory#newReminterBugs(long, String)
     */
    @Override
    public EventSimpleDto newReminterBugs(final long _alert, final String _project) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of new type bugs in BugZilla Project");
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        
        event.setDate(new Date());
        
        return event;
    }
    
    
    /**
     * Method newReopenedBugs.
     * @param _alert long
     * @param _project String
     * @return EventSimpleDto
     * @see org.komea.backend.plugins.bugzilla.api.IBugZillaAlertFactory#newReopenedBugs(long, String)
     */
    @Override
    public EventSimpleDto newReopenedBugs(final long _alert, final String _project) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of reopened bugs in BugZilla Project");
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        
        event.setDate(new Date());
        
        return event;
    }
    
    
    /**
     * Method newResolvedBugs.
     * @param _alert long
     * @param _project String
     * @return EventSimpleDto
     * @see org.komea.backend.plugins.bugzilla.api.IBugZillaAlertFactory#newResolvedBugs(long, String)
     */
    @Override
    public EventSimpleDto newResolvedBugs(final long _alert, final String _project) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of resolved bugs in BugZilla Project");
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        
        event.setDate(new Date());
        
        return event;
    }
    
    
    /**
     * Method newStatusBug.
     * @param _alert long
     * @param _project String
     * @param _status String
     * @return EventSimpleDto
     * @see org.komea.backend.plugins.bugzilla.api.IBugZillaAlertFactory#newStatusBug(long, String, String)
     */
    @Override
    public EventSimpleDto newStatusBug(
            final long _alert,
            final String _project,
            final String _status) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of " + _status + "  bugs in BugZilla Project");
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        
        event.setDate(new Date());
        return event;
    }
    
    
    /**
     * Method newTotalBugs.
     * @param _alert long
     * @param _project String
     * @return EventSimpleDto
     * @see org.komea.backend.plugins.bugzilla.api.IBugZillaAlertFactory#newTotalBugs(long, String)
     */
    @Override
    public EventSimpleDto newTotalBugs(final long _alert, final String _project) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of bugs in BugZilla Server");
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        
        event.setDate(new Date());
        
        return event;
    }
    
    
    /**
     * Method newUnconfirmedBugs.
     * @param _alert long
     * @param _project String
     * @return EventSimpleDto
     * @see org.komea.backend.plugins.bugzilla.api.IBugZillaAlertFactory#newUnconfirmedBugs(long, String)
     */
    @Override
    public EventSimpleDto newUnconfirmedBugs(final long _alert, final String _project) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of unconfirmed bugs in BugZilla Project");
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        
        event.setDate(new Date());
        
        return event;
    }
    
    
    /**
     * Method newUpdatedBugs.
     * @param _alert long
     * @param _project String
     * @return EventSimpleDto
     * @see org.komea.backend.plugins.bugzilla.api.IBugZillaAlertFactory#newUpdatedBugs(long, String)
     */
    @Override
    public EventSimpleDto newUpdatedBugs(final long _alert, final String _project) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of updated bugs in BugZilla Project");
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        
        event.setDate(new Date());
        
        return event;
    }
    
    
    /**
     * Method newVerifiedBugs.
     * @param _alert long
     * @param _project String
     * @return EventSimpleDto
     * @see org.komea.backend.plugins.bugzilla.api.IBugZillaAlertFactory#newVerifiedBugs(long, String)
     */
    @Override
    public EventSimpleDto newVerifiedBugs(final long _alert, final String _project) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of verified bugs in BugZilla Project");
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        
        event.setDate(new Date());
        
        return event;
    }
}
