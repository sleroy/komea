/**
 * 
 */

package org.komea.product.backend.service.esper;



import org.komea.product.database.enums.RetentionPeriod;
import org.komea.product.database.enums.Severity;



/**
 * @author sleroy
 */
public class RetentionQueryBuilder
{
    
    
    private final RetentionPeriod retentionTime;
    private final Severity        severity;
    
    
    
    /**
     * @param _severity
     * @param _retentionTime
     */
    public RetentionQueryBuilder(final Severity _severity, final RetentionPeriod _retentionTime) {
    
    
        super();
        severity = _severity;
        retentionTime = _retentionTime;
        
        
    }
    
    
    public String build() {
    
    
        return "SELECT * FROM  Event" + retentionTime.getWindow();
    }
}
