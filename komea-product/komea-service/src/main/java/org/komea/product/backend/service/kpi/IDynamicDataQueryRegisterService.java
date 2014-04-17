/**
 * 
 */

package org.komea.product.backend.service.kpi;



import java.util.List;

import org.komea.cep.dynamicdata.IDynamicDataQuery;



/**
 * @author sleroy
 *
 */
public interface IDynamicDataQueryRegisterService
{
    
    
    public abstract boolean existQuery(String _engineKey);
    
    
    public abstract IDynamicDataQuery getQuery(String _query);
    
    
    public abstract List<String> getQueryNames();
    
    
    public abstract void registerQuery(String _queryName, IDynamicDataQuery _query);
    
    
    public abstract boolean removeQuery(String _queryName);
    
}
