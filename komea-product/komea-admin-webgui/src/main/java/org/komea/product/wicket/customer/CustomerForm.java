/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.customer;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.komea.product.database.dao.CustomerDao;
import org.komea.product.database.model.Customer;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;

/**
 *
 * @author rgalerme
 */
public class CustomerForm extends Form<Customer> {

    private final CustomerDao customerService;
    private final Component feedBack;
    private final LayoutPage page;
    private final Customer customer;

    public CustomerForm(String form, CompoundPropertyModel<Customer> compoundPropertyModel, CustomerDao _customerService, Component _feedBack, LayoutPage _page, Customer _customer) {
        super(form, compoundPropertyModel);
        this.customerService = _customerService;
        this.feedBack = _feedBack;
        this.page = _page;
        this.customer = _customer;
        feedBack.setVisible(false);

        add(TextFieldBuilder.<String>createRequired("name", this.customer, "name").highlightOnErrors()
                .simpleValidator(0, 255).withTooltip("Customer requires a name").build());

        //button
        add(new AjaxLinkLayout<LayoutPage>("cancel", page) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                LayoutPage page = getCustom();
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
                info("Submitted information");
                // repaint the feedback panel so that it is hidden
                target.add(feedBack);
                if (customer.getId() == null) {
                    customerService.insert(customer);
                } else {
                    customerService.updateByPrimaryKey(customer);
                }

                page.setResponsePage(new CustomerPage(page.getPageParameters()));

            }
        });
    }

}
