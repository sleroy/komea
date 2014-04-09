
package org.komea.product.rest.client.api;


import java.net.ConnectException;
import java.util.List;

import org.komea.product.database.dto.TeamDto;
import org.komea.product.service.dto.errors.InternalServerException;

/**
 * Komea rest client api to manage teams
 * <p>
 * 
 * @author $Author: jguidoux $
 * @since 4 févr. 2014
 */
public interface ITeamsAPI {
    
    /**
     * This method return the teams list stored in Komea
     * 
     * @return the complete teams list
     * @throws ConnectException
     *             launch if it can't connect to the server
     * @throws InternalServerException
     *             launch if exception happened in server side
     */
    List<TeamDto> allTeams() throws ConnectException, InternalServerException;
}
