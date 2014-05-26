/**
 * 
 */

package org.komea.product.backend.groovy;

import org.komea.product.backend.exceptions.KomeaException;

/**
 * @author sleroy
 */
public class GroovyParsingException extends KomeaException {

	/**
	 * @param _groovyScript
	 * @param _throwable
	 */
	public GroovyParsingException(final String _groovyScript, final Throwable _throwable) {

		super("Script groovy\n: " + _groovyScript + "\n=> Could not be parsed", _throwable);

	}

}
