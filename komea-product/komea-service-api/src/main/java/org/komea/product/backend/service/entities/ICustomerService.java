package org.komea.product.backend.service.entities;

import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.model.Customer;
import org.komea.product.database.model.CustomerCriteria;

/**
 * Komea service to manage customers
 * <p>
 *
 */
public interface ICustomerService extends IGenericService<Customer, Integer, CustomerCriteria> {

    void deleteCustomer(Customer _customer);
}
