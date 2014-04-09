
package org.komea.product.service.dto.errors;


import java.util.List;

import com.google.common.collect.Lists;

public class ErrorMessage
{
    
    private String                    message;
    
    private List<ErrorMessageElement> stackTraceElements;
    
    private ErrorMessage              cause;
    
    private String                    exceptionClassType;
    
    public ErrorMessage() {
    
        // TODO Auto-generated ErrorMessage stub
    }
    
    public ErrorMessage(final Throwable _ex) {
    
        exceptionClassType = _ex.getClass().getCanonicalName();
        message = _ex.getMessage();
        int nb = _ex.getStackTrace().length;
        stackTraceElements = Lists.newArrayListWithCapacity(nb);
        for (int i = 0; i < nb; i++) {
            stackTraceElements.add(new ErrorMessageElement(_ex.getStackTrace()[i]));
        }
        if (_ex.getCause() != null) {
            cause = new ErrorMessage(_ex.getCause());
        }
    }
    
    public ErrorMessage getCause() {
    
        return cause;
    }
    
    public String getMessage() {
    
        return message;
    }
    
    public List<ErrorMessageElement> getStackTraceElements() {
    
        return stackTraceElements;
    }
    
    public void setCause(final ErrorMessage _cause) {
    
        cause = _cause;
    }
    
    public String getExceptionClassType() {
    
        return exceptionClassType;
    }
    
    public void setExceptionClassType(final String _exceptionClassType) {
    
        exceptionClassType = _exceptionClassType;
    }
    
    public void setMessage(final String _message) {
    
        message = _message;
    }
    
    public void setStackTraceElements(final List<ErrorMessageElement> _stackTraceElements) {
    
        stackTraceElements = _stackTraceElements;
    }
    
}
