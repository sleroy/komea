/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla;

import java.util.List;

import javax.annotation.PostConstruct;

import org.komea.backend.plugins.bugzilla.api.IBugZillaAlertFactory;
import org.komea.backend.plugins.bugzilla.api.IBugZillaConfiguration;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerConfiguration;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerProxy;
import org.komea.product.backend.service.esper.IAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author rgalerme
 */
public class BugZillaCheckerBean {

    @Autowired
    private IAlertService alertService;

    @Autowired
    private IBugZillaAlertFactory alertFactory;

    @Autowired
    private IBugZillaConfiguration bugZillaConfiguration;

    @Scheduled(fixedDelay = 10)
    public void checkServers() {
//        Integer warn = providerSettings.getSettingValue("bugzilla_reminder_warning");
        Integer warn = 10;
        for (IBugZillaServerConfiguration conf : bugZillaConfiguration.getServers()) {
            IBugZillaServerProxy bugZillaService = conf.openProxy();
            List<String> projectNames = bugZillaService.getListProjects();
            for (String project : projectNames) {
                int bug = bugZillaService.getListBugs(project).size();
                alertService.sendEvent(alertFactory.newTotalBugs(bug, project));
                alertService.sendEvent(alertFactory.newUnconfirmedBugs(
                        bugZillaService.getFilterBugs(project, BugZillaStatus.UNCONFIRMED).size(),
                        project));
                alertService.sendEvent(alertFactory.newNewBugs(
                        bugZillaService.getFilterBugs(project, BugZillaStatus.NEW).size(), project));
                alertService.sendEvent(alertFactory.newAssignedBugs(
                        bugZillaService.getFilterBugs(project, BugZillaStatus.ASSIGNED).size(),
                        project));
                alertService.sendEvent(alertFactory.newReopenedBugs(
                        bugZillaService.getFilterBugs(project, BugZillaStatus.REOPENED).size(),
                        project));
                alertService
                        .sendEvent(alertFactory.newReadyBugs(
                                        bugZillaService.getFilterBugs(project, BugZillaStatus.READY).size(),
                                        project));
                alertService.sendEvent(alertFactory.newResolvedBugs(
                        bugZillaService.getFilterBugs(project, BugZillaStatus.RESOLVED).size(),
                        project));
                alertService.sendEvent(alertFactory.newVerifiedBugs(
                        bugZillaService.getFilterBugs(project, BugZillaStatus.VERIFIED).size(),
                        project));
                alertService.sendEvent(alertFactory.newNewBug(
                        bugZillaService.getFilterBugs(project, BugZillaStatus.ADD).size(), project));
                alertService.sendEvent(alertFactory.newUpdatedBugs(
                        bugZillaService.getFilterBugs(project, BugZillaStatus.UPDATED).size(),
                        project));

                if (bug < warn) {
                    // / Check mon serveur bugzilla
                    alertService.sendEvent(alertFactory.newReminterBugs(bugZillaService
                            .getFilterBugs(project, BugZillaStatus.REMINDER).size(), project));
                }
            }

        }
    }

    public IAlertService getAlertService() {

        return alertService;
    }

    @PostConstruct
    public void init() {

    }

    public void setAlertService(final IAlertService alertService) {

        this.alertService = alertService;
    }
}
