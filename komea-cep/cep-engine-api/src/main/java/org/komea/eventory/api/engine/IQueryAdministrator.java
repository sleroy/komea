/**
 * 
 */

package org.komea.eventory.api.engine;



import java.util.List;



/**
 * This class defines all methods to administrate the queries inside the engine.
 * 
 * @author sleroy
 */
public interface IQueryAdministrator
{
    
    
    /**
     * Returns true if the query exist inside the engine.
     * 
     * @param _engineKey
     *            the query key
     */
    boolean existQuery(String _engineKey);
    
    
    /**
     * Find the query with the given name
     * 
     * @param _query
     *            the query
     * @return the queyr or null.
     */
    ICEPQuery getQuery(String _query);
    
    
    /**
     * Returns the list of query names declared inside the cep engine.
     * 
     * @return the list of query names;
     */
    List<String> getQueryNames();
    
    
    /**
     * Register a new query inside the CEP Engine.
     * 
     * @param _query
     *            the query
     */
    void registerQuery(String _queryName, ICEPQuery _query);
    
    
    /**
     * Returns true if the query has been properly removed from the registry.
     * 
     * @param _queryName
     *            the query name.
     * @return
     */
    boolean removeQuery(String _queryName);
}
