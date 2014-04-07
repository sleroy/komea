/**
 * 
 */

package org.komea.product.plugins.scm.api.error;



/**
 * @author sleroy
 */
public abstract class ScmRuntimeException extends RuntimeException
{
    
    
    private static final String SCM_PLUGIN_IN_KOMEA_MEET_AN_EXCEPTION =
                                                                              "SCM Plugin in Komea meet an exception :";
    
    private static final long   serialVersionUID                      = 6672185832632453620L;
    
    
    
    public ScmRuntimeException(final String _message) {
    
    
        this(_message, null);
    }
    
    
    public ScmRuntimeException(final String _message, final Throwable _e) {
    
    
        super(SCM_PLUGIN_IN_KOMEA_MEET_AN_EXCEPTION + _message, _e);
    }
    
    
    public ScmRuntimeException(final Throwable _e) {
    
    
        this(_e.getMessage(), _e);
    }
    
}
