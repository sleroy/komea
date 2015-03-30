/**
 *
 */

package org.komea.connectors.git.exceptions;


/**
 * @author sleroy
 */
public class ScmCannotObtainGitProxyException extends GitRuntimeException
{
    
    public ScmCannotObtainGitProxyException(final String _string, final Throwable _e) {
    
        super(_string, _e);
    }

}
