/**
 * 
 */

package org.komea.product.plugins.timemanagement.model;



/**
 * This class defines the time management filter
 * 
 * @author sleroy
 */
public interface ITimeManagementFilter
{
    
    
    /**
     * Tests if a time management data line matches
     * 
     * @param _timeLine
     *            the time line.
     */
    boolean matches(ITimeManagementDataLine _timeLine);
}
