/**
 * 
 */

package org.komea.eventory.api.formula;

import java.io.Serializable;

import org.komea.eventory.api.engine.ICEPStatement;

/**
 * This class defines the formula that is employed by the query to compute its
 * result.
 * 
 * @author sleroy
 */
public interface ICEPFormula<TEvent extends Serializable, TRes> {

	/**
	 * Compute the value.
	 * 
	 * @param _statement
	 *            the statement
	 * @param the
	 *            parameters
	 * @return the result.
	 */
	TRes compute(ICEPStatement<TEvent> _statement);

}
