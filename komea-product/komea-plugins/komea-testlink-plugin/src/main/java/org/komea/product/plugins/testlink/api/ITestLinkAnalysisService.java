/**
 * 
 */

package org.komea.product.plugins.testlink.api;



import org.komea.product.plugins.testlink.model.TestLinkProject;



/**
 * @author sleroy
 *
 */
public interface ITestLinkAnalysisService
{
    
    
    /**
     * Checks a testlink project.
     * 
     * @param openProxy
     * @param projet
     */
    public abstract void checkTestlinkProject(ITestLinkServerProxy openProxy, TestLinkProject projet);
    
}
