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

import javax.annotation.PostConstruct;

import org.komea.backend.plugins.bugzilla.api.IBugZillaAlertFactory;
import org.komea.backend.plugins.bugzilla.api.IBugZillaConfigurationService;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerConfiguration;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerProxy;
import org.komea.product.backend.service.esper.IEventPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;



/**
 * @author rgalerme
 */
public class BugZillaCheckerBean
{
    
    
    @Autowired
    private IEventPushService             alertService;
    
    @Autowired
    private IBugZillaAlertFactory         alertFactory;
    
    @Autowired
    private IBugZillaConfigurationService bugZillaConfiguration;
    
    
    
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
                    final List<BugzillaBug> filterBugsByStatus =
                            bugZillaContext.getFilterBugsByStatus(stat, project);
                    if (filterBugsByStatus != null) {
                        alertService.sendEventDto(alertFactory.newStatusBug(
                                filterBugsByStatus.size(), project, stat));
                    }
                }
                final List<BugzillaBug> reminderAlert = getReminderAlert(10, listBugs);
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
    
    
    public IBugZillaAlertFactory getAlertFactory() {
    
    
        return alertFactory;
    }
    
    
    public IEventPushService getAlertService() {
    
    
        return alertService;
    }
    
    
    public IBugZillaConfigurationService getBugZillaConfiguration() {
    
    
        return bugZillaConfiguration;
    }
    
    
    @PostConstruct
    public void init() {
    
    
    }
    
    
    public void setAlertFactory(final IBugZillaAlertFactory _alertFactory) {
    
    
        alertFactory = _alertFactory;
    }
    
    
    public void setAlertService(final IEventPushService _alertService) {
    
    
        alertService = _alertService;
    }
    
    
    public void setBugZillaConfiguration(final IBugZillaConfigurationService _bugZillaConfiguration) {
    
    
        bugZillaConfiguration = _bugZillaConfiguration;
    }
    
    
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
