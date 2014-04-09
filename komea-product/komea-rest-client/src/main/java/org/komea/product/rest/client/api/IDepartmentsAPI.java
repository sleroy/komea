
package org.komea.product.rest.client.api;


import java.net.ConnectException;
import java.util.List;

import org.komea.product.database.dto.DepartmentDto;
import org.komea.product.service.dto.errors.InternalServerException;

/**
 * Komea rest client api to manage projects
 * <p>
 * 
 * @author $Author: jguidoux $
 * @since 4 févr. 2014
 */
public interface IDepartmentsAPI {
    
    /**
     * This method return the departments list stored in Komea
     * 
     * @return the complete departments list
     * @throws ConnectException
     *             launch if it can't connect to the server
     * @throws InternalServerException
     *             launch if exception happened in server side
     */
    List<DepartmentDto> allDepartments() throws ConnectException, InternalServerException;
}
