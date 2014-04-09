/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.customer;

import java.util.List;
import org.komea.product.database.dao.CustomerDao;
import org.komea.product.database.model.Customer;
import org.komea.product.wicket.widget.api.IDeleteAction;

/**
 *
 * @author rgalerme
 */
public class CustomerDeleteAction implements IDeleteAction<Customer> {

    private final List<Customer> customerAffichage;
    private final CustomerDao customerDao;

    public CustomerDeleteAction(List<Customer> customerAffichage, CustomerDao customerDao) {
        this.customerAffichage = customerAffichage;
        this.customerDao = customerDao;
    }
    
    @Override
    public void delete(Customer _customer) {
        this.customerDao.deleteByPrimaryKey(_customer.getId());
        this.customerAffichage.remove(_customer);
    }
    
}
