/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla.userinterface;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.backend.plugins.bugzilla.api.IBugZillaConfigurationService;
import org.komea.product.wicket.LayoutPage;

/**
 *
 * @author rgalerme
 */
public final class BugZillaPage extends LayoutPage {

    @SpringBean
    private IBugZillaConfigurationService bugZillaService;

    public BugZillaPage(final PageParameters _parameters) {
        super(_parameters);
        
        
        //TODO:  process page parameters
    }

    @Override
    public String getTitle() {

        return getString("BugZillaPage.title");
    }
}
