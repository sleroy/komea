/**
 * 
 */

package org.komea.product.plugins.scm.api;

import org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy;



/**
 * This interface defines the service that analysis a scm repository.
 * 
 * @author sleroy
 */
public interface IScmRepositoryAnalysisService
{
    
    
    /**
     * Analysis of the scm repository
     * 
     * @param _newProxy
     *            the proxy
     */
    void analysis(IScmRepositoryProxy _newProxy);
    
}
