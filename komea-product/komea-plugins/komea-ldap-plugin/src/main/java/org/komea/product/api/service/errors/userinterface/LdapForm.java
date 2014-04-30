/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.api.service.errors.userinterface;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.komea.product.api.service.ldap.ILdapService;
import org.komea.product.backend.service.ldap.LdapAuthTypeEnum;
import org.komea.product.backend.service.ldap.LdapServer;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.SelectBoxBuilder;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rgalerme
 */
public class LdapForm extends Form<LdapServer> {

    private final ILdapService ldapService;
    private final LdapServer ldapServer;
    private final Component feedBack;
    private final LayoutPage page;

    public LdapForm(ILdapService _ldapService, LdapServer _ldapServer, Component _feedBack, LayoutPage _page, String id, IModel<LdapServer> model) {
        super(id, model);
        this.ldapService = _ldapService;
        this.ldapServer = _ldapServer;
        this.feedBack = _feedBack;
        this.page = _page;
        feedBack.setVisible(false);

        final WebMarkupContainer webMarkupContainer = new WebMarkupContainer("success");
        webMarkupContainer.setVisible(false);
        webMarkupContainer.setOutputMarkupId(true);
        webMarkupContainer.setOutputMarkupPlaceholderTag(true);
        add(webMarkupContainer);

        final WebMarkupContainer webMarkupContainer2 = new WebMarkupContainer("errors");
        webMarkupContainer2.setVisible(false);
        webMarkupContainer2.setOutputMarkupId(true);
        webMarkupContainer2.setOutputMarkupPlaceholderTag(true);
        add(webMarkupContainer2);

        add(TextFieldBuilder.create("ldapUrl", ldapServer, "ldapUrl")
                .withTooltip(getString("global.save.form.field.tooltip.serverloc")).simpleValidator(3, 255).build());

        add(TextFieldBuilder.<String>create("ldapLogin", ldapServer, "ldapLogin")
                .simpleValidator(0, 255).withTooltip(getString("global.save.form.field.tooltip.login")).build());

        add(TextFieldBuilder.<String>create("ldapPassword", ldapServer, "ldapPassword")
                .simpleValidator(0, 255).withTooltip(getString("global.save.form.field.tooltip.password")).build());

        add(TextFieldBuilder.<String>create("ldapBase", ldapServer, "ldapBase")
                .simpleValidator(0, 255).withTooltip(getString("ldap.save.form.field.tooltip.base")).build());

        add(SelectBoxBuilder.<LdapAuthTypeEnum>createWithEnum("LdapAuthTypeEnum", ldapServer, LdapAuthTypeEnum.class)
                .withTooltip(getString("ldap.save.form.field.tooltip.aut")).build());

        add(new AjaxButton("submit", this) {

            @Override
            protected void onError(final AjaxRequestTarget target, final Form<?> form) {

                feedBack.setVisible(true);
                // repaint the feedback panel so errors are shown
                target.add(feedBack);
            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {

                feedBack.setVisible(false);
                target.add(feedBack);
                boolean success = true;
                try {
                    ldapService.saveOrUpdate(ldapServer);
                } catch (final Exception e) {
                    success = false;
                }
                if (success) {
                    webMarkupContainer.setVisible(true);
                    target.add(webMarkupContainer);
                } else {
                    webMarkupContainer2.setVisible(true);
                    target.add(webMarkupContainer2);
                }

            }
        });
    }

}
