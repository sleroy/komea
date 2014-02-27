
package org.komea.product.rest.client;



import java.net.ConnectException;
import java.util.List;

import javax.ws.rs.core.GenericType;

import org.komea.product.database.model.Kpi;
import org.komea.product.rest.client.api.IKpisAPI;
import org.komea.product.service.dto.errors.InternalServerException;



public class KpisAPI extends AbstractRestCientAPI implements IKpisAPI
{
    
    
    /**
     * @author sleroy
     */
    private final class GenericTypeExtension extends GenericType<List<Kpi>>
    {
        // /
    }
    
    
    
    private static final String KPI_PATH = "kpis";
    
    
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.rest.client.api.IKpisAPI#allKpis()
     */
    @Override
    public List<Kpi> allKpis() throws ConnectException, InternalServerException {
    
    
        final String url = KPI_PATH + "/all";
        return get(url, new GenericTypeExtension());
    }
    //
}
