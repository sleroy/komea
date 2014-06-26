/**
 * 
 */

package org.komea.product.backend.api.exceptions;



import org.komea.product.backend.exceptions.KomeaRuntimeException;
import org.komea.product.database.model.Kpi;



/**
 * @author sleroy
 */
public class GroovyScriptException extends KomeaRuntimeException
{
    
    
    public enum GroovyValidationStatus {
        EXECUTION_FAILED, OK, PARSING_FAILED, UNKNOWN
    }
    
    
    
    private GroovyValidationStatus status;
    
    
    
    /**
     * @param _message
     */
    public GroovyScriptException(
            final Kpi _kpi,
            final GroovyValidationStatus _result,
            final Throwable _reason) {
    
    
        super("The kpi "
                + _kpi.getKey() + " provides a formula that failed to be executed for the reason "
                + _result + " " + _kpi.getEsperRequest(), _reason);
        status = _result;
        //
    }
    
    
    /**
     * @param _groovyScript
     * @param _status
     * @param _e
     *            // TODO Auto-generated constructor stub
     */
    public GroovyScriptException(
            final String _groovyScript,
            final GroovyValidationStatus _status,
            final Exception _e) {
    
    
        super("This script could not be executed by groovy : "
                + _groovyScript + " for the reason " + _status, _e);
        status = _status;
    }
    
    
    public GroovyValidationStatus getStatus() {
    
    
        return status;
    }
    
    
    public void setStatus(final GroovyValidationStatus _status) {
    
    
        status = _status;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "GroovyScriptException [\\n\\tstatus="
                + status + ", \\n\\ttoString()=" + super.toString() + "]";
    }
    
}
