/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.customer;

import java.util.List;
import org.komea.product.backend.service.customer.ICustomerService;
import org.komea.product.database.model.Customer;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.AbstractDeleteAction;

/**
 *
 * @author rgalerme
 */
public class CustomerDeleteAction extends AbstractDeleteAction<Customer> {

    private final List<Customer> customerAffichage;
    private final ICustomerService customerDao;

    public CustomerDeleteAction(List<Customer> customerAffichage, ICustomerService customerDao,LayoutPage page) {
         super(page, "dialogdelete");
        this.customerAffichage = customerAffichage;
        this.customerDao = customerDao;
    }



    @Override
    public void deleteAction() {
        this.customerDao.deleteCustomer(getObject());
        this.customerAffichage.remove(getObject());
    }
    
}
