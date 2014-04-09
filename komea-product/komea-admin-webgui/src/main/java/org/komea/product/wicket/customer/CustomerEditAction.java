/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.customer;

import org.komea.product.database.model.Customer;
import org.komea.product.wicket.widget.api.IEditAction;

/**
 *
 * @author rgalerme
 */
public class CustomerEditAction implements IEditAction<Customer> {

    private final CustomerPage customerPage;

    public CustomerEditAction(CustomerPage customerPage) {
        this.customerPage = customerPage;
    }
    
    
    @Override
    public void selected(Customer _customer) {
        this.customerPage.setResponsePage(new CustomerEditPage(this.customerPage.getPageParameters(),_customer));
    }
    
}
