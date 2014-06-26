/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.mantis.service;



import org.komea.product.backend.api.PluginAdminPages;
import org.komea.product.backend.api.PluginMountPage;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.plugins.mantis.userinterface.MantisPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This class defines the bugzilla provider plugin. Informations are collected
 * at runtime.
 *
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
@ProviderPlugin(
        type = ProviderType.BUGTRACKER,
        name = MantisProviderPlugin.BUGZILLA_PROVIDER_PLUGIN,
        icon = "bugzilla",
        url = "/bugzilla-provider",
        eventTypes = {})
@PluginAdminPages(@PluginMountPage(
        pluginName = MantisProviderPlugin.BUGZILLA_PROVIDER_PLUGIN,
        page = MantisPage.class))
public class MantisProviderPlugin
{


    public static final String  BUGZILLA_PROVIDER_PLUGIN = "BugZilla Provider plugin";

    private static final Logger LOGGER                   = LoggerFactory
            .getLogger("mantis-provider");


}
