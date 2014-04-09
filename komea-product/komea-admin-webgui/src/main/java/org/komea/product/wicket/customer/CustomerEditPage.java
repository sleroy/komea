/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.customer;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.database.dao.CustomerDao;
import org.komea.product.database.model.Customer;
import org.komea.product.wicket.LayoutPage;

/**
 *
 * @author rgalerme
 */
public final class CustomerEditPage extends LayoutPage {

    @SpringBean
    private CustomerDao customerService;

    public CustomerEditPage(PageParameters _parameters) {
       this(_parameters, new Customer());
    }
    
    

    public CustomerEditPage(PageParameters params, Customer _customer) {
        super(params);
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        feedbackPanel.setOutputMarkupPlaceholderTag(true);
        add(feedbackPanel);
        CustomerForm customerForm = new CustomerForm("form", new CompoundPropertyModel<Customer>(_customer), customerService, feedbackPanel, this, _customer);
        add(customerForm);
    }
    
       @Override
    public String getTitle() {
    
    
        return getString("CustomerEditPage.title");
    }
}
