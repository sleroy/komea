/**
 * 
 */

package org.komea.product.plugins.scm.api.error;



import java.util.List;



/**
 * Builds the scm repository analysis exception when an exception has happened during the analysis of a branch.
 * 
 * @author sleroy
 */
public class ScmRepositoryAnalysisException extends ScmRuntimeException
{
    
    
    private final List<Throwable> errorThrowables;
    
    
    
    public ScmRepositoryAnalysisException(final List<Throwable> _errorThrowables) {
    
    
        super("SCM Analysis performed met a list of exceptions :" + _errorThrowables);
        errorThrowables = _errorThrowables;
    }
    
    
    public List<Throwable> getErrorThrowables() {
    
    
        return errorThrowables;
    }
    
}
