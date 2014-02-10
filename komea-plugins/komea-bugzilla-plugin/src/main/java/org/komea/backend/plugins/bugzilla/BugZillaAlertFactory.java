/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla;

import java.util.Date;
import org.komea.backend.plugins.bugzilla.api.IBugZillaAlertFactory;
import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.alert.enums.Criticity;
import org.komea.product.database.model.Project;
import org.springframework.stereotype.Service;

/**
 *
 * @author rgalerme
 */
@Service
public class BugZillaAlertFactory implements IBugZillaAlertFactory {

    public IEvent newTotalBugs(long _alert, String _project) {
        IEvent event = new Event();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of bugs in BugZilla Server");
        event.setCategory("BUGTRACKER");
        event.setValue(_alert);
        event.setFullMessage(event.getMessage());
        event.setProject(_project);
        event.setCriticity(Criticity.INFO);
        event.setDate(new Date());

        return event;
    }

    @Override
    public IEvent newUnconfirmedBugs(long _alert, String _project) {
        IEvent event = new Event();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of unconfirmed bugs in BugZilla Project");
        event.setCategory("BUGTRACKER");
        event.setValue(_alert);
        event.setFullMessage(event.getMessage());
        event.setProject(_project);
        event.setCriticity(Criticity.INFO);
        event.setDate(new Date());

        return event;
    }

    @Override
    public IEvent newNewBugs(long _alert, String _project) {
        IEvent event = new Event();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of new type bugs in BugZilla Project");
        event.setCategory("BUGTRACKER");
        event.setValue(_alert);
        event.setFullMessage(event.getMessage());
        event.setProject(_project);
        event.setCriticity(Criticity.INFO);
        event.setDate(new Date());

        return event;
    }

    @Override
    public IEvent newAssignedBugs(long _alert, String _project) {
        IEvent event = new Event();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of assigned bugs in BugZilla Project");
        event.setCategory("BUGTRACKER");
        event.setValue(_alert);
        event.setFullMessage(event.getMessage());
        event.setProject(_project);
        event.setCriticity(Criticity.INFO);
        event.setDate(new Date());

        return event;
    }

    @Override
    public IEvent newReopenedBugs(long _alert, String _project) {
        IEvent event = new Event();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of reopened bugs in BugZilla Project");
        event.setCategory("BUGTRACKER");
        event.setValue(_alert);
        event.setFullMessage(event.getMessage());
        event.setProject(_project);
        event.setCriticity(Criticity.INFO);
        event.setDate(new Date());

        return event;
    }

    @Override
    public IEvent newReadyBugs(long _alert, String _project) {
        IEvent event = new Event();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of ready bugs in BugZilla Project");
        event.setCategory("BUGTRACKER");
        event.setValue(_alert);
        event.setFullMessage(event.getMessage());
        event.setProject(_project);
        event.setCriticity(Criticity.INFO);
        event.setDate(new Date());

        return event;
    }

    @Override
    public IEvent newResolvedBugs(long _alert, String _project) {
        IEvent event = new Event();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of resolved bugs in BugZilla Project");
        event.setCategory("BUGTRACKER");
        event.setValue(_alert);
        event.setFullMessage(event.getMessage());
        event.setProject(_project);
        event.setCriticity(Criticity.INFO);
        event.setDate(new Date());

        return event;
    }

    @Override
    public IEvent newVerifiedBugs(long _alert, String _project) {
        IEvent event = new Event();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of verified bugs in BugZilla Project");
        event.setCategory("BUGTRACKER");
        event.setValue(_alert);
        event.setFullMessage(event.getMessage());
        event.setProject(_project);
        event.setCriticity(Criticity.INFO);
        event.setDate(new Date());

        return event;
    }

    @Override
    public IEvent newNewBug(long _alert, String _project) {
        IEvent event = new Event();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of new bugs in BugZilla Project");
        event.setCategory("BUGTRACKER");
        event.setValue(_alert);
        event.setFullMessage(event.getMessage());
        event.setProject(_project);
        event.setCriticity(Criticity.MAJOR);
        event.setDate(new Date());

        return event;
    }

    @Override
    public IEvent newUpdatedBugs(long _alert, String _project) {
        IEvent event = new Event();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of updated bugs in BugZilla Project");
        event.setCategory("BUGTRACKER");
        event.setValue(_alert);
        event.setFullMessage(event.getMessage());
        event.setProject(_project);
        event.setCriticity(Criticity.MINOR);
        event.setDate(new Date());

        return event;
    }

    @Override
    public IEvent newReminterBugs(long _alert, String _project) {
        IEvent event = new Event();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of new type bugs in BugZilla Project");
        event.setCategory("BUGTRACKER");
        event.setValue(_alert);
        event.setFullMessage(event.getMessage());
        event.setProject(_project);
        event.setCriticity(Criticity.MINOR);
        event.setDate(new Date());

        return event;
    }

    @Override
    public IEvent newStatusBug(long _alert, String _project, String _status) {
           IEvent event = new Event();
        event.setProvider("BUGZILLA");
        event.setMessage("Total number of "+_status+"  bugs in BugZilla Project");
        event.setCategory("BUGTRACKER");
        event.setValue(_alert);
        event.setFullMessage(event.getMessage());
        event.setProject(_project);
        event.setCriticity(Criticity.INFO);
        event.setDate(new Date());
        return event;
    }
}
