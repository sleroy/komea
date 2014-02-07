
package org.komea.product.service.dto.errors;


public class ErrorMessageConvertor
{
    
    public static Exception convertToException(final ErrorMessage _errorMessage) {
    
        Exception exception;
        
        try {
            exception = (Exception) Thread.currentThread().getContextClassLoader().loadClass(_errorMessage.getExceptionClassType())
                    .newInstance();
        } catch (Exception e) {
            exception = new Exception(_errorMessage.getMessage());
        }
        
        StackTraceElement[] stackTrace = new StackTraceElement[_errorMessage.getStackTraceElements().size()];
        int i = 0;
        for (ErrorMessageElement elem : _errorMessage.getStackTraceElements()) {
            stackTrace[i] = convertToStackTraceElement(elem);
            i++;
        }
        exception.setStackTrace(stackTrace);
        if (_errorMessage.getCause() != null) {
            exception.initCause(convertToException(_errorMessage.getCause()));
        }
        return exception;
    }
    
    private static StackTraceElement convertToStackTraceElement(final ErrorMessageElement _elem) {
    
        StackTraceElement stacktraceElem = new StackTraceElement(_elem.getDeclaringClass(), _elem.getMethodName(), _elem.getFileName(),
                _elem.getLineNumber());
        return stacktraceElem;
    }
    
}
