
package org.komea.product.rest.client;


import java.net.ConnectException;
import java.util.List;

import org.komea.product.database.model.PersonGroup;
import org.komea.product.rest.client.api.IDepartmentsAPI;
import org.komea.product.rest.util.GenericListType;

public class DepartmentsAPI extends AbstractRestCientAPI implements IDepartmentsAPI
{
    
    private static final String DEPARTMENTS_PATH = "departments";
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.rest.client.api.IDepartmentsAPI#allDepartments()
     */
    @Override
    public List<PersonGroup> allDepartments() throws ConnectException {
    
        String url = DEPARTMENTS_PATH + "/all";
        return get(url, new GenericListType<PersonGroup>());
    }
    //
}
