/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.backend.plugins.bugzilla;



import java.util.Date;

import org.komea.backend.plugins.bugzilla.api.IBugZillaAlertFactory;
import org.komea.product.database.dto.EventSimpleDto;
import org.springframework.stereotype.Service;



/**
 * @author rgalerme
 */
@Service
public class BugZillaAlertFactory implements IBugZillaAlertFactory
{
    
    
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
