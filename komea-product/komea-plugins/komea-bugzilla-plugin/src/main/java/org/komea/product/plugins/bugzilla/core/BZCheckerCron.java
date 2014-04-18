/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.bugzilla.core;

import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.backend.service.plugins.IEventTypeService;
import org.komea.product.database.model.EventType;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.api.IBZServerProxy;
import org.komea.product.plugins.bugzilla.api.IBZServerProxyFactory;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
public class BZCheckerCron implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger("bugzilla-cron");

    private static final String REMIDER_DEFAUT = "10";

    @Autowired
    private IEventPushService alertService;

    @Autowired
    private IBZConfigurationDAO bugZillaConfiguration;

    private final EventTypeFactory eventTypeFactory = new EventTypeFactory();

    @Autowired
    private IEventTypeService eventTypeService;

    @Autowired
    private IBZServerProxyFactory proxyFactory;

    /**
     * Build the event types
     *
     * @param _bzserver
     * @param _bugzillaProxy
     * @param _project
     */
    public void buildEventTypes(
            final BZServerConfiguration _bzserver,
            final IBZServerProxy _bugzillaProxy,
            final String _project) {

        LOGGER.info("Controlling severities and status of bugzilla server");
        final List<EventType> eventTypes = eventTypeFactory.allEventTypes(_bzserver);
        for (final EventType eventType : eventTypes) {
            eventTypeService.registerEvent(eventType);
        }

    }

    /**
     * Check all servers
     */
    public void checkServers() {

        LOGGER.info("Checking for update");

        for (final BZServerConfiguration conf : bugZillaConfiguration.selectAll()) {
            IBZServerProxy bugzillaProxy = null;
            try {

                bugzillaProxy = proxyFactory.newConnector(conf);
                Validate.notNull(bugzillaProxy);

            } catch (final Exception ex) {
                LOGGER.error("Error during the bugzilla server update {} : reason {}",
                        conf.getAddress(), ex.getMessage(), ex);
            } finally {
                if (bugzillaProxy != null) {
                    IOUtils.closeQuietly(bugzillaProxy);
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {

        checkServers();

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
     * @return IBZConfigurationDAO
     */
    public IBZConfigurationDAO getBugZillaConfiguration() {

        return bugZillaConfiguration;
    }

    public IBZServerProxyFactory getProxyFactory() {

        return proxyFactory;
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
     * @param _bugZillaConfiguration IBZConfigurationDAO
     */
    public void setBugZillaConfiguration(final IBZConfigurationDAO _bugZillaConfiguration) {

        bugZillaConfiguration = _bugZillaConfiguration;
    }

    public void setProxyFactory(final IBZServerProxyFactory _proxyFactory) {

        proxyFactory = _proxyFactory;
    }
}
