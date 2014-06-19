/**
 *
 */

package org.komea.product.backend.csv.errors;



import org.komea.product.backend.exceptions.KomeaRuntimeException;



/**
 * @author sleroy
 */
public class CSVConversionException extends KomeaRuntimeException
{


    /**
     * @param _message
     * @param _throwable
     */
    public CSVConversionException(final String _message, final Throwable _throwable) {


        super("An error occured during the CSV Conversion :" + _message, _throwable);

    }


}
