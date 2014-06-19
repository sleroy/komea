/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.customer;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.entities.ICustomerService;
import org.komea.product.database.model.Customer;
import org.komea.product.wicket.StatelessLayoutPage;

/**
 *
 * @author rgalerme
 */
public final class CustomerEditPage extends StatelessLayoutPage {

    @SpringBean
    private ICustomerService customerService;

    public CustomerEditPage(PageParameters _parameters) {
        this(_parameters, new Customer(), true);
    }

    public CustomerEditPage(PageParameters params, Customer _customer) {
        this(params, _customer, false);
    }

    public CustomerEditPage(PageParameters params, Customer _customer, boolean isNew) {
        super(params);
        
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        feedbackPanel.setOutputMarkupPlaceholderTag(true);
        add(feedbackPanel);
        CustomerForm customerForm = new CustomerForm(isNew,"form", new CompoundPropertyModel<Customer>(_customer), customerService, feedbackPanel, this, _customer);
                String message;
        if (isNew) {
            message = getString("customer.add.title");
        } else {
            message = getString("customer.edit.title");
        }
        customerForm.add(new Label("legend", message));
        add(customerForm);
    }

    @Override
    public String getTitle() {

        return getString("customer.main.title");
    }
}
