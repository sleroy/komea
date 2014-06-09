/**
 *
 */

package org.komea.product.backend.exceptions;



/**
 * This interface defines the base exception of Komea.
 *
 * @author sleroy
 */
public abstract class KomeaRuntimeException extends RuntimeException
{


    /**
     *
     */
    private static final long serialVersionUID = 3292215099176541490L;



    /**
     * Komea Exception : the message
     *
     * @param _message
     *            the message
     */
    public KomeaRuntimeException(final String _message) {


        super(_message, null);
    }


    /**
     * Builds an exception
     *
     * @param _message
     *            the message
     * @param _throwable
     *            the throwable
     */
    public KomeaRuntimeException(final String _message, final Throwable _throwable) {


        super("Komea meets an unexpected exception : \n\t-> reason :" + _message, _throwable);

    }


    /**
     * Builds an exception
     *
     * @param _throwable
     *            the throwable;
     */
    public KomeaRuntimeException(final Throwable _throwable) {


        super(_throwable.getMessage(), _throwable);
    }

}
