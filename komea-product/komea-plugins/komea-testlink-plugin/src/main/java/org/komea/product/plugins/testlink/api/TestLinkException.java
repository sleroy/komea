/**
 * 
 */

package org.komea.product.plugins.testlink.api;



/**
 * @author sleroy
 */
public class TestLinkException extends RuntimeException
{
    
    
    /**
     * @param _message
     * @param _mue
     */
    public TestLinkException(final String _message, final Throwable _mue) {
    
    
        super("Testlink plugin meets an error : \n\t\t" + _message, _mue);
    }
}
