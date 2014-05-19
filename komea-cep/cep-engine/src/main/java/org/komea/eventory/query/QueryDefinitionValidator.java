/**
 * 
 */

package org.komea.eventory.query;



import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author sleroy
 */
public class QueryDefinitionValidator
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryDefinitionValidator.class);
    
    
    
    /**
     * Validates the query implementation.
     * 
     * @param _queryDefinition
     *            the query definition.
     * @return true if the query is validated.
     */
    public boolean validate(final ICEPQueryImplementation _queryDefinition) {
    
    
        if (_queryDefinition == null) {
            LOGGER.error("Expects a query definition");
            return false;
        }
        if (_queryDefinition.getBackupDelay() == null) {
            LOGGER.error("Expects a delay to backup kpi");
            return false;
        }
        if (_queryDefinition.getFormula() == null) {
            LOGGER.error("Expects a formula");
            return false;
        }
        if (_queryDefinition.getFilterDefinitions().isEmpty()) {
            LOGGER.error("Expects a filter definition");
        }
        
        return true;
    }
    
}
