
package org.komea.product.rest.client.api;


import java.net.ConnectException;
import java.util.List;

import org.komea.product.database.model.Project;

/**
 * Komea rest client api to manage projects
 * <p>
 * 
 * @author $Author: jguidoux $
 * @since 4 févr. 2014
 */
public interface IProjectsAPI
{
    
    /**
     * This method return the project list stored in Komea
     * 
     * @return the complete project list
     * @throws ConnectException
     *             launch if it can't connect to the server
     */
    List<Project> allProjects() throws ConnectException;
}
