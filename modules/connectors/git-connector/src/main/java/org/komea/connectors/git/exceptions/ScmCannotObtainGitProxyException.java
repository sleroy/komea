/**
 * 
 */

package org.komea.connectors.git.exceptions;



import org.komea.connectors.scm.ScmRuntimeException;



/**
 * @author sleroy
 */
public class ScmCannotObtainGitProxyException extends GitRuntimeException
{
    
    
    public ScmCannotObtainGitProxyException(final String _string, final Throwable _e) {
    
    
        super(_string, _e);
    }
    
}
