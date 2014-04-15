/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.bugzilla.userinterface;



import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;



/**
 * @author rgalerme
 */
public class BugZillaForm extends Form<BZServerConfiguration>
{
    
    
    private final IBZConfigurationDAO   bService;
    private final BZServerConfiguration bugServer;
    private final Component             feedBack;
    private final String                oldAdress;
    private final LayoutPage            page;
    
    
    
    public BugZillaForm(
            final IBZConfigurationDAO _bService,
            final BZServerConfiguration _bugServer,
            final Component _feedBack,
            final LayoutPage _page,
            final String id,
            final IModel<BZServerConfiguration> model) {
    
    
        super(id, model);
        bService = _bService;
        bugServer = _bugServer;
        feedBack = _feedBack;
        page = _page;
        oldAdress = bugServer.getAddress() == null ? "" : bugServer.getAddress().toString();
        feedBack.setVisible(false);
        
        
        add(TextFieldBuilder.createURL("address", bugServer, "address")
                .withTooltip("Server requires an URL").simpleValidator(3, 255).build());
        add(TextFieldBuilder.<String> createRequired("login", bugServer, "login")
                .simpleValidator(0, 255).withTooltip("Server need a login").build());
        add(TextFieldBuilder.<String> createRequired("password", bugServer, "password")
                .simpleValidator(0, 255).withTooltip("Server need a password").build());
        add(TextFieldBuilder.<String> createRequired("reminderAlert", bugServer, "reminderAlert")
                .withTooltip("").build());
        
        // button
        add(new AjaxLinkLayout<LayoutPage>("cancel", page)
        {
            
            
            @Override
            public void onClick(final AjaxRequestTarget art) {
            
            
                final LayoutPage page = getCustom();
                page.setResponsePage(new BugZillaPage(page.getPageParameters()));
            }
        });
        
        add(new AjaxButton("submit", this)
        {
            
            
            @Override
            protected void onError(final AjaxRequestTarget target, final Form<?> form) {
            
            
                feedBack.setVisible(true);
                error("error found");
                // repaint the feedback panel so errors are shown
                target.add(feedBack);
            }
            
            
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
            
            
                feedBack.setVisible(false);
                info("Submitted information");
                // repaint the feedback panel so that it is hidden
                target.add(feedBack);
                bService.saveOrUpdate(bugServer);
                page.setResponsePage(new BugZillaPage(page.getPageParameters()));
                
            }
        });
    }
    
}
