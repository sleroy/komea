
package org.komea.product.rest.client;


import java.net.ConnectException;
import java.net.URISyntaxException;

import org.komea.product.rest.client.api.IDepartmentsAPI;
import org.komea.product.rest.client.api.IEventsAPI;
import org.komea.product.rest.client.api.IMeasuresAPI;
import org.komea.product.rest.client.api.IPersonsAPI;
import org.komea.product.rest.client.api.IProjectsAPI;
import org.komea.product.rest.client.api.IProvidersAPI;
import org.komea.product.rest.client.api.ITeamsAPI;

/**
 * Komea factory to create rest api client objects
 * <p>
 * 
 * @author $Author: jguidoux $
 * @since 15 janv. 2014
 */
public enum RestClientFactory {
    
    INSTANCE;
    
    /**
     * This method create a departments api object
     * 
     * @param _serverBaseURL
     *            the server base url
     * @return a departmentsAPI instance
     * @throws ConnectException
     *             launch if the server can't connect to the server
     * @throws URISyntaxException
     *             launch if the server URL in not valid
     */
    public IDepartmentsAPI createDeparmtentsAPI(final String _serverBaseURL) throws ConnectException, URISyntaxException {
    
        DepartmentsAPI departmentAPI = new DepartmentsAPI();
        departmentAPI.setServerBaseURL(_serverBaseURL);
        return departmentAPI;
    }
    
    /**
     * This method create a events api object
     * 
     * @param _serverBaseURL
     *            the server base url
     * @return a eventsAPI instance
     * @throws ConnectException
     *             launch if the server can't connect to the server
     * @throws URISyntaxException
     *             launch if the server URL in not valid
     */
    public IEventsAPI createEventsAPI(final String _serverBaseURL) throws ConnectException, URISyntaxException {
    
        IEventsAPI eventsAPI = new EventsAPI();
        eventsAPI.setServerBaseURL(_serverBaseURL);
        return eventsAPI;
    }
    
    public IMeasuresAPI createMeasuresAPI(final String _serverBaseURL) throws ConnectException, URISyntaxException {
    
        MeasuresAPI measuresAPI = new MeasuresAPI();
        measuresAPI.setServerBaseURL(_serverBaseURL);
        return measuresAPI;
    }
    
    /**
     * This method create a projects api object
     * 
     * @param _serverBaseURL
     *            the server base url
     * @return a projectsAPI instance
     * @throws ConnectException
     *             launch if the server can't connect to the server
     * @throws URISyntaxException
     *             launch if the server URL in not valid
     */
    public IProjectsAPI createProjectsAPI(final String _serverBaseURL) throws ConnectException, URISyntaxException {
    
        ProjectsAPI projectAPI = new ProjectsAPI();
        projectAPI.setServerBaseURL(_serverBaseURL);
        return projectAPI;
    }
    
    /**
     * This method create a team api object
     * 
     * @param _serverBaseURL
     *            the server base url
     * @return a projectsAPI instance
     * @throws ConnectException
     *             launch if the server can't connect to the server
     * @throws URISyntaxException
     *             launch if the server URL in not valid
     */
    public ITeamsAPI createTeamsAPI(final String _serverBaseURL) throws ConnectException, URISyntaxException {
    
        TeamsAPI teamAPI = new TeamsAPI();
        teamAPI.setServerBaseURL(_serverBaseURL);
        return teamAPI;
    }
    
    /**
     * This method create a providers api object
     * 
     * @param _serverBaseURL
     *            the server base url
     * @return a providersAPI instance
     * @throws ConnectException
     *             launch if the server can't connect to the server
     * @throws URISyntaxException
     *             launch if the server URL in not valid
     */
    public IProvidersAPI createProvidersAPI(final String _serverBaseURL) throws ConnectException, URISyntaxException {
    
        IProvidersAPI providersAPI = new ProvidersAPI();
        providersAPI.setServerBaseURL(_serverBaseURL);
        return providersAPI;
    }
    
    /**
     * This method create a providers api object
     * 
     * @param _serverBaseURL
     *            the server base url
     * @return a providersAPI instance
     * @throws ConnectException
     *             launch if the server can't connect to the server
     * @throws URISyntaxException
     *             launch if the server URL in not valid
     */
    public IPersonsAPI createPersonsAPI(final String _serverBaseURL) throws ConnectException, URISyntaxException {
    
        PersonsAPI personAPI = new PersonsAPI();
        personAPI.setServerBaseURL(_serverBaseURL);
        return personAPI;
    }
}
