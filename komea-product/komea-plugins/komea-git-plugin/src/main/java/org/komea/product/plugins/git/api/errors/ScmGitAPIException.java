/**
 * 
 */

package org.komea.product.plugins.git.api.errors;



import org.eclipse.jgit.api.errors.GitAPIException;
import org.komea.product.plugins.scm.api.error.ScmRuntimeException;



/**
 * @author sleroy
 */
public class ScmGitAPIException extends ScmRuntimeException
{
    
    
    /**
     * @param _message
     * @param _e
     */
    public ScmGitAPIException(final String _message, final GitAPIException _e) {
    
    
        super(_message, _e);
        
    }
    
}
