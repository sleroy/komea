/**
 *
 */

package org.komea.product.plugins.mantis.service;



import org.komea.product.backend.exceptions.KomeaRuntimeException;



/**
 * @author sleroy
 */
public class MantisPluginException extends KomeaRuntimeException
{


    /**
     * @param _throwable
     */
    public MantisPluginException(final Throwable _throwable) {


        super(_throwable);
    }


}
