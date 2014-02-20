
package org.komea.product.backend.service.esper;



/**
 * This interface defines an query for an ETL as Esper.
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public interface IQueryDefinition
{
    
    
    /**
     * Returns the name of the query.
     * 
    
     * @return String
     */
    String getName();
    
    
    /**
     * Returns the query.
     * 
    
     * @return the query */
    String getQuery();
}
