
package org.komea.product.rest.client;



import java.net.ConnectException;
import java.util.List;

import javax.ws.rs.core.GenericType;

import org.komea.product.database.dto.PersonDto;
import org.komea.product.rest.client.api.IPersonsAPI;
import org.komea.product.service.dto.errors.InternalServerException;



public class PersonsAPI extends AbstractRestCientAPI implements IPersonsAPI
{
    
    
    private static final String PROJECT_PATH = "persons";
    
    
    
    @Override
    public List<PersonDto> allPersons() throws ConnectException, InternalServerException {
    
    
        final String url = PROJECT_PATH + "/all";
        return get(url, new GenericType<List<PersonDto>>()
        {
            // /
        });
    }
    //
}
