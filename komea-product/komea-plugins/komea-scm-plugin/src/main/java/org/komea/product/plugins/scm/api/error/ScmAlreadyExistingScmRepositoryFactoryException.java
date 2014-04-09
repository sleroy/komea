/**
 * 
 */

package org.komea.product.plugins.scm.api.error;






/**
 * @author sleroy
 */
public class ScmAlreadyExistingScmRepositoryFactoryException extends ScmRuntimeException
{
    
    
    /**
     * @param _branchName
     */
    public ScmAlreadyExistingScmRepositoryFactoryException(final String _branchName) {
    
    
        super("Scm Repository factory is already existing " + _branchName);
    }
    
    
    /**
     * @param _message
     * @param _e
     */
    public ScmAlreadyExistingScmRepositoryFactoryException(final String _message, final Throwable _e) {
    
    
        super(_message, _e);
        
    }
    
    
    /**
     * @param _e
     */
    public ScmAlreadyExistingScmRepositoryFactoryException(final Throwable _e) {
    
    
        super(_e);
        
    }
    
}
