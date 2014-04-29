/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.api.service.errors.userinterface;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.api.service.ldap.ILdapService;
import org.komea.product.backend.service.ldap.LdapServer;
import org.komea.product.wicket.LayoutPage;

/**
 *
 * @author rgalerme
 */
public final class LdapPage extends LayoutPage {

    @SpringBean
    private ILdapService ldapService;
    
    
    public LdapPage(PageParameters _parameters) {
        super(_parameters);
        
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        feedbackPanel.setOutputMarkupPlaceholderTag(true);
        add(feedbackPanel);
        
        
        LdapServer ldapServer = new LdapServer();
        ldapService.load(ldapServer);
        LdapForm ldForm = new LdapForm(ldapService, ldapServer, feedbackPanel, this, "form", new CompoundPropertyModel<LdapServer>(ldapServer));
        ldForm.add(new Label("legend", getString("ldap.save.form.title")));
        add(ldForm);
        
        
    }

    @Override
    public String getTitle() {
        return getString("ldap.save.title");
    }

    

}
