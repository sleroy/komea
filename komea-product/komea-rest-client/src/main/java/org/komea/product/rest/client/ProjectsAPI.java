
package org.komea.product.rest.client;



import java.net.ConnectException;
import java.util.List;

import javax.ws.rs.core.GenericType;

import org.komea.product.database.dto.ProjectDto;
import org.komea.product.rest.client.api.IProjectsAPI;
import org.komea.product.service.dto.errors.InternalServerException;



public class ProjectsAPI extends AbstractRestCientAPI implements IProjectsAPI
{
    
    
    /**
     * @author sleroy
     */
    private final class GenericTypeExtension extends GenericType<List<ProjectDto>>
    {
        //
    }
    
    
    
    private static final String PROJECT_PATH = "projects";
    
    
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.rest.client.api.IProjectsAPI#allProjects()
     */
    @Override
    public List<ProjectDto> allProjects() throws ConnectException, InternalServerException {
    
    
        final String url = PROJECT_PATH + "/all";
        return get(url, new GenericTypeExtension());
    }
    
    //
}
