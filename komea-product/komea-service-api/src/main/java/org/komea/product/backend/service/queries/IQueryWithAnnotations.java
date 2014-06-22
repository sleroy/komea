/**
 *
 */

package org.komea.product.backend.service.queries;



import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.service.plugins.IQueryAnnotations;



/**
 * @author sleroy
 */
public interface IQueryWithAnnotations<T extends IQuery>
{
    
    
    IQueryAnnotations getAnnotations();


    T getQuery();
}
