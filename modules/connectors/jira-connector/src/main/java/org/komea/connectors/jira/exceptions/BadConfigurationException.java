
package org.komea.connectors.jira.exceptions;

import org.komea.core.exceptions.KomeaException;


/**
 * @author rgalerme
 */
public class BadConfigurationException extends KomeaException
{
    
    public BadConfigurationException(final String message) {
    
        super(message);
    }
    
}
