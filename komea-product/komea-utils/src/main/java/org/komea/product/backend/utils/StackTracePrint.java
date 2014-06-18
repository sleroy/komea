/**
 *
 */

package org.komea.product.backend.utils;



import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;



/**
 * @author sleroy
 */
public class StackTracePrint
{
    
    
    /**
     * Prints a stack trace into a string
     *
     * @param _throwable
     *            the exceptin
     * @return the string
     */
    public static String printTrace(final Throwable _throwable) {
    
    
        final StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(stringWriter);
            _throwable.printStackTrace(printWriter);
        } finally {
            IOUtils.closeQuietly(printWriter);
        }
        return stringWriter.getBuffer().toString();
        
        
    }
}
