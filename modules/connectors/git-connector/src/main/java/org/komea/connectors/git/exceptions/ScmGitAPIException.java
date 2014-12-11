/**
 * 
 */

package org.komea.connectors.git.exceptions;



import org.eclipse.jgit.api.errors.GitAPIException;



/**
 * @author sleroy
 */
public class ScmGitAPIException extends GitRuntimeException
{
    
    
    /**
     * @param _message
     * @param _e
     */
    public ScmGitAPIException(final String _message, final GitAPIException _e) {
    
    
        super(_message, _e);
        
    }
    
}
