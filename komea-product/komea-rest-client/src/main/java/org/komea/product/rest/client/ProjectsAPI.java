
package org.komea.product.rest.client;


import java.net.ConnectException;
import java.util.List;

import javax.ws.rs.core.GenericType;

import org.komea.product.database.model.Project;
import org.komea.product.rest.client.api.IProjectsAPI;

public class ProjectsAPI extends AbstractRestCientAPI implements IProjectsAPI
{
    
    private static final String PROJECT_PATH = "projects";
    
    @Override
    public List<Project> allProjects() throws ConnectException {
    
        String url = PROJECT_PATH + "/all";
        return get(url, new GenericType<List<Project>>()
        {
        });
    }
    
    //
}
