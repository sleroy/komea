/**
 * 
 */

package org.komea.product.plugins.git.api.errors;



import java.io.IOException;

import org.komea.product.plugins.scm.api.error.ScmRuntimeException;



/**
 * @author sleroy
 */
public class ScmCannotObtainGitProxyException extends ScmRuntimeException
{
    
    
    public ScmCannotObtainGitProxyException(final String _string, final Throwable _e) {
    
    
        super(_string, _e);
    }
    
}
