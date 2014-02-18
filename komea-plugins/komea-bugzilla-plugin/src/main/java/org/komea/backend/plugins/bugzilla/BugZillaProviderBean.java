/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.backend.plugins.bugzilla;



import javax.annotation.PostConstruct;

import org.komea.product.backend.plugin.api.EventTypeDef;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This class defines the bugzilla provider plugin. Informations are collected at runtime.
 * 
 * @author rgalerme
 */
@ProviderPlugin(
        type = ProviderType.BUGTRACKER,
        name = "BUGZILLA",
        icon = "/bugzilla.gif",
        eventTypes = {
                @EventTypeDef(
                        category = "BUGTRACKER",
                        description = "Total Number of Bugs in BugZilla server",
                        key = "BUGZILLA_TOTAL_BUGS",
                        name = "Bugzilla Total Bugs",
                        entityType = EntityType.PROJECT,
                        severity = Severity.INFO),
                @EventTypeDef(
                        category = "BUGTRACKER",
                        description = "Number of unconfirmed bugs",
                        key = "BUGZILLA_TOTAL_BUGS",
                        name = "BugZilla Unconfirmed bugs",
                        entityType = EntityType.PROJECT,
                        severity = Severity.INFO), })
public class BugZillaProviderBean
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BugZillaProviderBean.class);
    
    
    
    @PostConstruct
    public void init() {
    
    
        LOGGER.info("Loading bugZilla plugin");
    }
    
}
