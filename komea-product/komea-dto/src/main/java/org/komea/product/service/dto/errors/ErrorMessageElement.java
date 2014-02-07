
package org.komea.product.service.dto.errors;


import org.codehaus.jackson.annotate.JsonIgnore;

public class ErrorMessageElement
{
    
    private String declaringClass;
    private String methodName;
    private String fileName;
    private int    lineNumber;
    
    public ErrorMessageElement() {
    
        // TODO Auto-generated ErrorMessageElement stub
    }
    
    public ErrorMessageElement(final StackTraceElement _element) {
    
        declaringClass = _element.getClassName();
        methodName = _element.getMethodName();
        fileName = _element.getFileName();
        lineNumber = _element.getLineNumber();
    }
    
    public String getDeclaringClass() {
    
        return declaringClass;
    }
    
    public void setDeclaringClass(final String _declaringClass) {
    
        declaringClass = _declaringClass;
    }
    
    public String getMethodName() {
    
        return methodName;
    }
    
    @JsonIgnore
    public boolean isNativeMethod() {
    
        return lineNumber == -2;
    }
    
    @Override
    public String toString() {
    
        return getDeclaringClass()
                + "."
                + methodName
                + (isNativeMethod() ? "(Native Method)" : fileName != null && lineNumber >= 0 ? "(" + fileName + ":" + lineNumber + ")"
                        : fileName != null ? "(" + fileName + ")" : "(Unknown Source)");
    }
    
    public void setMethodName(final String _methodName) {
    
        methodName = _methodName;
    }
    
    public String getFileName() {
    
        return fileName;
    }
    
    public void setFileName(final String _fileName) {
    
        fileName = _fileName;
    }
    
    public int getLineNumber() {
    
        return lineNumber;
    }
    
    public void setLineNumber(final int _lineNumber) {
    
        lineNumber = _lineNumber;
    }
    
}
