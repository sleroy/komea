
package org.komea.product.rest.client;


import java.net.ConnectException;
import java.util.List;

import javax.ws.rs.core.GenericType;

import org.komea.product.database.dto.TeamDto;
import org.komea.product.rest.client.api.ITeamsAPI;
import org.komea.product.service.dto.errors.InternalServerException;

public class TeamsAPI extends AbstractRestCientAPI implements ITeamsAPI {
    
    private static final String TEAM_PATH = "teams";
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.rest.client.api.ITeamsAPI#allTeams()
     */
    @Override
    public List<TeamDto> allTeams() throws ConnectException, InternalServerException {
    
        String url = TEAM_PATH + "/all";
        return get(url, new GenericType<List<TeamDto>>() {
        });
    }
    //
}
