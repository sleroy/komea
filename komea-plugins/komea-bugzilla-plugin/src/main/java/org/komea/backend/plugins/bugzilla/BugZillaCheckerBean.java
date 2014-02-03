/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.komea.backend.plugins.bugzilla.api.IBugZillaAlertFactory;
import org.komea.backend.plugins.bugzilla.api.IBugZillaConfigurationService;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerConfiguration;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerProxy;
import org.komea.product.backend.fs.IObjectStorage;
import org.komea.product.backend.service.IPluginStorageService;
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
    private IBugZillaConfigurationService bugZillaConfiguration;
    
    @Scheduled(fixedDelay = 10)
    public void checkServers() {
//        Integer warn = providerSettings.getSettingValue("bugzilla_reminder_warning");
        for (IBugZillaServerConfiguration conf : bugZillaConfiguration.getServers()) {
            IBugZillaServerProxy bugzillaProxy = conf.openProxy();
//            bugzillaProxy.connexion();
            List<String> projectNames = bugzillaProxy.getListProjects();
            for (String project : projectNames) {
                List<BugzillaBug> listBugs = bugzillaProxy.getListBugs(project);
                BugZillaContext bugZillaContext = conf.getBugZillaContext();
                bugZillaContext.updateBugs(project, listBugs);
                int bug = listBugs.size();
                
                alertService.sendEvent(alertFactory.newTotalBugs(bug, project));
                alertService.sendEvent(alertFactory.newNewBug(
                        bugZillaContext.getFilterBugs(project, BugZillaStatus.ADD).size(), project));
                alertService.sendEvent(alertFactory.newUpdatedBugs(
                        bugZillaContext.getFilterBugs(project, BugZillaStatus.UPDATED).size(),
                        project));
                alertService.sendEvent(alertFactory.newAssignedBugs(
                        bugZillaContext.getFilterBugs(project, BugZillaStatus.ASSIGNED).size(),
                        project));
                Set<String> status = bugZillaContext.getStatus();
                for (String stat : status) {
                    List<BugzillaBug> filterBugsByStatus = bugZillaContext.getFilterBugsByStatus(stat, project);
                    if (filterBugsByStatus != null) {
                        alertService.sendEvent(alertFactory.newStatusBug(filterBugsByStatus.size(), project, stat));
                    }
                }
                List<BugzillaBug> reminderAlert = getReminderAlert(10, listBugs);
                alertService.sendEvent(alertFactory.newReminterBugs(reminderAlert.size(), project));
            }
            try {
                bugzillaProxy.close();
            } catch (IOException ex) {
                Logger.getLogger(BugZillaCheckerBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private List<BugzillaBug> getReminderAlert(int days, List<BugzillaBug> listBugs) {
        List<BugzillaBug> result = new ArrayList<BugzillaBug>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -days);
        
        for (BugzillaBug bugzillaBug : listBugs) {
            
            if (bugzillaBug.getDate_creation().after(calendar.getTime())) {
                result.add(bugzillaBug);
            }
        }
        
        return result;
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
    
    public IBugZillaConfigurationService getBugZillaConfiguration() {
        
        return bugZillaConfiguration;
    }
    
    public void setBugZillaConfiguration(IBugZillaConfigurationService _bugZillaConfiguration) {
        
        bugZillaConfiguration = _bugZillaConfiguration;
    }
    
    public IBugZillaAlertFactory getAlertFactory() {
        
        return alertFactory;
    }
    
    public void setAlertFactory(IBugZillaAlertFactory _alertFactory) {
        
        alertFactory = _alertFactory;
    }
}
