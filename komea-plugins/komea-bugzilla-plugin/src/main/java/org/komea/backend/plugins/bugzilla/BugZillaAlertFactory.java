/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla;

import java.util.Date;
import org.komea.backend.plugins.bugzilla.api.IBugZillaAlertFactory;
import org.komea.product.database.alert.Alert;
import org.komea.product.database.alert.IAlert;
import org.komea.product.database.alert.enums.Criticity;
import org.komea.product.database.model.Project;
import org.springframework.stereotype.Service;

/**
 *
 * @author rgalerme
 */
@Service
public class BugZillaAlertFactory implements IBugZillaAlertFactory {

    public IAlert newTotalBugs(long _alert, String _project) {
        Alert alert = new Alert();
        alert.setProvider("BUGZILLA");
        alert.setMessage("Total number of bugs in BugZilla Server");
        alert.setCategory("BUGTRACKER");
        alert.setValue(_alert);
        alert.setFullMessage(alert.getMessage());
        alert.setProject(_project);
        alert.setCriticity(Criticity.INFO);
        alert.setDate(new Date());

        return alert;
    }

    @Override
    public IAlert newUnconfirmedBugs(long _alert, String _project) {
        Alert alert = new Alert();
        alert.setProvider("BUGZILLA");
        alert.setMessage("Total number of unconfirmed bugs in BugZilla Project");
        alert.setCategory("BUGTRACKER");
        alert.setValue(_alert);
        alert.setFullMessage(alert.getMessage());
        alert.setProject(_project);
        alert.setCriticity(Criticity.INFO);
        alert.setDate(new Date());

        return alert;
    }

    @Override
    public IAlert newNewBugs(long _alert, String _project) {
        Alert alert = new Alert();
        alert.setProvider("BUGZILLA");
        alert.setMessage("Total number of new type bugs in BugZilla Project");
        alert.setCategory("BUGTRACKER");
        alert.setValue(_alert);
        alert.setFullMessage(alert.getMessage());
        alert.setProject(_project);
        alert.setCriticity(Criticity.INFO);
        alert.setDate(new Date());

        return alert;
    }

    @Override
    public IAlert newAssignedBugs(long _alert, String _project) {
        Alert alert = new Alert();
        alert.setProvider("BUGZILLA");
        alert.setMessage("Total number of assigned bugs in BugZilla Project");
        alert.setCategory("BUGTRACKER");
        alert.setValue(_alert);
        alert.setFullMessage(alert.getMessage());
        alert.setProject(_project);
        alert.setCriticity(Criticity.INFO);
        alert.setDate(new Date());

        return alert;
    }

    @Override
    public IAlert newReopenedBugs(long _alert, String _project) {
        Alert alert = new Alert();
        alert.setProvider("BUGZILLA");
        alert.setMessage("Total number of reopened bugs in BugZilla Project");
        alert.setCategory("BUGTRACKER");
        alert.setValue(_alert);
        alert.setFullMessage(alert.getMessage());
        alert.setProject(_project);
        alert.setCriticity(Criticity.INFO);
        alert.setDate(new Date());

        return alert;
    }

    @Override
    public IAlert newReadyBugs(long _alert, String _project) {
        Alert alert = new Alert();
        alert.setProvider("BUGZILLA");
        alert.setMessage("Total number of ready bugs in BugZilla Project");
        alert.setCategory("BUGTRACKER");
        alert.setValue(_alert);
        alert.setFullMessage(alert.getMessage());
        alert.setProject(_project);
        alert.setCriticity(Criticity.INFO);
        alert.setDate(new Date());

        return alert;
    }

    @Override
    public IAlert newResolvedBugs(long _alert, String _project) {
        Alert alert = new Alert();
        alert.setProvider("BUGZILLA");
        alert.setMessage("Total number of resolved bugs in BugZilla Project");
        alert.setCategory("BUGTRACKER");
        alert.setValue(_alert);
        alert.setFullMessage(alert.getMessage());
        alert.setProject(_project);
        alert.setCriticity(Criticity.INFO);
        alert.setDate(new Date());

        return alert;
    }

    @Override
    public IAlert newVerifiedBugs(long _alert, String _project) {
        Alert alert = new Alert();
        alert.setProvider("BUGZILLA");
        alert.setMessage("Total number of verified bugs in BugZilla Project");
        alert.setCategory("BUGTRACKER");
        alert.setValue(_alert);
        alert.setFullMessage(alert.getMessage());
        alert.setProject(_project);
        alert.setCriticity(Criticity.INFO);
        alert.setDate(new Date());

        return alert;
    }

    @Override
    public IAlert newNewBug(long _alert, String _project) {
        Alert alert = new Alert();
        alert.setProvider("BUGZILLA");
        alert.setMessage("Total number of new bugs in BugZilla Project");
        alert.setCategory("BUGTRACKER");
        alert.setValue(_alert);
        alert.setFullMessage(alert.getMessage());
        alert.setProject(_project);
        alert.setCriticity(Criticity.MAJOR);
        alert.setDate(new Date());

        return alert;
    }

    @Override
    public IAlert newUpdatedBugs(long _alert, String _project) {
        Alert alert = new Alert();
        alert.setProvider("BUGZILLA");
        alert.setMessage("Total number of updated bugs in BugZilla Project");
        alert.setCategory("BUGTRACKER");
        alert.setValue(_alert);
        alert.setFullMessage(alert.getMessage());
        alert.setProject(_project);
        alert.setCriticity(Criticity.MINOR);
        alert.setDate(new Date());

        return alert;
    }

    @Override
    public IAlert newReminterBugs(long _alert, String _project) {
        Alert alert = new Alert();
        alert.setProvider("BUGZILLA");
        alert.setMessage("Total number of new type bugs in BugZilla Project");
        alert.setCategory("BUGTRACKER");
        alert.setValue(_alert);
        alert.setFullMessage(alert.getMessage());
        alert.setProject(_project);
        alert.setCriticity(Criticity.MINOR);
        alert.setDate(new Date());

        return alert;
    }

    @Override
    public IAlert newStatusBug(long _alert, String _project, String _status) {
           Alert alert = new Alert();
        alert.setProvider("BUGZILLA");
        alert.setMessage("Total number of "+_status+"  bugs in BugZilla Project");
        alert.setCategory("BUGTRACKER");
        alert.setValue(_alert);
        alert.setFullMessage(alert.getMessage());
        alert.setProject(_project);
        alert.setCriticity(Criticity.INFO);
        alert.setDate(new Date());
        return alert;
    }
}
