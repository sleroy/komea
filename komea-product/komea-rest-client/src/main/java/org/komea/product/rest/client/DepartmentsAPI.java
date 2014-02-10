
package org.komea.product.rest.client;


import java.net.ConnectException;
import java.util.List;

import javax.ws.rs.core.GenericType;

import org.komea.product.database.model.PersonGroup;
import org.komea.product.rest.client.api.IDepartmentsAPI;
import org.komea.product.service.dto.errors.InternalServerException;

public class DepartmentsAPI extends AbstractRestCientAPI implements IDepartmentsAPI {
    
    private static final String DEPARTMENTS_PATH = "departments";
    
    /**
     * (non-Javadoc)
     * 
     * @throws InternalServerException
     * @see org.komea.product.rest.client.api.IDepartmentsAPI#allDepartments()
     */
    @Override
    public List<PersonGroup> allDepartments() throws ConnectException, InternalServerException {
    
        String url = DEPARTMENTS_PATH + "/all";
        return get(url, new GenericType<List<PersonGroup>>() {
        });
    }
    //
}
