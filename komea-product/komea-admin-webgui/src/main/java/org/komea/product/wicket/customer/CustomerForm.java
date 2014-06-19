/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.customer;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.komea.product.backend.service.entities.ICustomerService;
import org.komea.product.database.model.Customer;
import org.komea.product.wicket.StatelessLayoutPage;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;

/**
 *
 * @author rgalerme
 */
public class CustomerForm extends Form<Customer> {

    private final ICustomerService customerService;
    private final Component feedBack;
    private final StatelessLayoutPage page;
    private final Customer customer;
    private final boolean isNew;

    public CustomerForm(boolean _isNew, String form, CompoundPropertyModel<Customer> compoundPropertyModel, ICustomerService _customerService, Component _feedBack, StatelessLayoutPage _page, Customer _customer) {
        super(form, compoundPropertyModel);
        this.isNew = _isNew;
        this.customerService = _customerService;
        this.feedBack = _feedBack;
        this.page = _page;
        this.customer = _customer;
        feedBack.setVisible(false);
        TextFieldBuilder<String> keyField = TextFieldBuilder.<String>createRequired("name", this.customer, "name").highlightOnErrors()
                .simpleValidator(0, 255).withTooltip(getString("global.field.tooltip.name"));

        if (isNew) {
            keyField.UniqueStringValidator(getString("global.field.name"), customerService);
        } else {
            keyField.buildTextField().add(new AttributeModifier("readonly", "readonly"));
        }

        add(keyField.build());

        //button
        add(new AjaxLinkLayout<StatelessLayoutPage>("cancel", page) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                StatelessLayoutPage page = getCustom();
                page.setResponsePage(new CustomerPage(page.getPageParameters()));
            }
        });

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
                // repaint the feedback panel so that it is hidden
                target.add(feedBack);
                customerService.saveOrUpdate(customer);
                page.setResponsePage(new CustomerPage(page.getPageParameters()));

            }
        });
    }

}
