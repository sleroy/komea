/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.komea.backend.plugins.bugzilla.api.IBugZillaAlertFactory;
import org.komea.backend.plugins.bugzilla.api.IBugZillaConfigurationService;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerConfiguration;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerProxy;
import org.komea.backend.plugins.bugzilla.data.BugZillaContext;
import org.komea.backend.plugins.bugzilla.data.BugZillaServer;
import org.komea.backend.plugins.bugzilla.data.BugZillaStatus;
import org.komea.backend.plugins.bugzilla.data.BugzillaBug;
import org.komea.backend.plugins.bugzilla.sah.BugUtils;
import org.komea.backend.plugins.bugzilla.sah.BugsCalculator;
import org.komea.backend.plugins.bugzilla.sah.EventService;
import org.komea.backend.plugins.bugzilla.sah.model.Bug;
import org.komea.product.backend.service.esper.IEventPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
@Service
public class BugZillaCheckerBean {

    @Autowired
    private IBugZillaAlertFactory alertFactory;

    @Autowired
    private IEventPushService alertService;

    @Autowired
    private IBugZillaConfigurationService bugZillaConfiguration;

    private void swMaturity(final List<BugzillaBug> bugzillaBugs,
            final String project, final BugZillaServer server) {
        final List<Bug> bugs = BugUtils.convert(bugzillaBugs);
        final BugsCalculator bugsCalculator = new BugsCalculator(bugs);
        EventService.sendAllEvents(bugsCalculator, alertService, project, server);
    }

    @Scheduled(fixedDelay = 1000)
    public void checkServers() {

        // Integer warn = providerSettings.getSettingValue("bugzilla_reminder_warning");
        for (final IBugZillaServerConfiguration conf : bugZillaConfiguration.getServers()) {
            final IBugZillaServerProxy bugzillaProxy = conf.openProxy();
            // bugzillaProxy.connexion();
            final List<String> projectNames = bugzillaProxy.getListProjects();
            for (final String project : projectNames) {
                final List<BugzillaBug> listBugs = bugzillaProxy.getListBugs(project);
                final BugZillaContext bugZillaContext = conf.getBugZillaContext();
                bugZillaContext.updateBugs(project, listBugs);
                final int bug = listBugs.size();

                swMaturity(listBugs, project, conf.getServer());

                alertService.sendEventDto(alertFactory.newTotalBugs(bug, project));
                alertService
                        .sendEventDto(alertFactory.newNewBug(
                                        bugZillaContext.getFilterBugs(project, BugZillaStatus.ADD).size(),
                                        project));
                alertService.sendEventDto(alertFactory.newUpdatedBugs(bugZillaContext
                        .getFilterBugs(project, BugZillaStatus.UPDATED).size(), project));
                alertService.sendEventDto(alertFactory.newAssignedBugs(bugZillaContext
                        .getFilterBugs(project, BugZillaStatus.ASSIGNED).size(), project));
                final Set<String> status = bugZillaContext.getStatus();
                for (final String stat : status) {
                    final List<BugzillaBug> filterBugsByStatus
                            = bugZillaContext.getFilterBugsByStatus(stat, project);
                    if (filterBugsByStatus != null) {
                        alertService.sendEventDto(alertFactory.newStatusBug(
                                filterBugsByStatus.size(), project, stat));
                    }
                }
                final List<BugzillaBug> reminderAlert = getReminderAlert(conf.getReminderAlert(), listBugs);
                alertService.sendEventDto(alertFactory.newReminterBugs(reminderAlert.size(),
                        project));
            }
            try {
                bugzillaProxy.close();
            } catch (final IOException ex) {
                Logger.getLogger(BugZillaCheckerBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Method getAlertFactory.
     *
     * @return IBugZillaAlertFactory
     */
    public IBugZillaAlertFactory getAlertFactory() {

        return alertFactory;
    }

    /**
     * Method getAlertService.
     *
     * @return IEventPushService
     */
    public IEventPushService getAlertService() {

        return alertService;
    }

    /**
     * Method getBugZillaConfiguration.
     *
     * @return IBugZillaConfigurationService
     */
    public IBugZillaConfigurationService getBugZillaConfiguration() {

        return bugZillaConfiguration;
    }

    /**
     * Method setAlertFactory.
     *
     * @param _alertFactory IBugZillaAlertFactory
     */
    public void setAlertFactory(final IBugZillaAlertFactory _alertFactory) {

        alertFactory = _alertFactory;
    }

    /**
     * Method setAlertService.
     *
     * @param _alertService IEventPushService
     */
    public void setAlertService(final IEventPushService _alertService) {

        alertService = _alertService;
    }

    /**
     * Method setBugZillaConfiguration.
     *
     * @param _bugZillaConfiguration IBugZillaConfigurationService
     */
    public void setBugZillaConfiguration(final IBugZillaConfigurationService _bugZillaConfiguration) {

        bugZillaConfiguration = _bugZillaConfiguration;
    }

    /**
     * Method getReminderAlert.
     *
     * @param days int
     * @param listBugs List<BugzillaBug>
     * @return List<BugzillaBug>
     */
    private List<BugzillaBug> getReminderAlert(final int days, final List<BugzillaBug> listBugs) {

        final List<BugzillaBug> result = new ArrayList<BugzillaBug>();
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -days);

        for (final BugzillaBug bugzillaBug : listBugs) {

            if (bugzillaBug.getDate_creation().after(calendar.getTime())) {
                result.add(bugzillaBug);
            }
        }

        return result;
    }
}
