/**
 *
 */
package org.komea.product.backend.exceptions;

/**
 * This class defines exception when a kpi does not provides a valid formula.
 *
 * @author sleroy
 */
public class InvalidQueryFormulaException extends KomeaException {

    /**
     * This kpi does not provides a valid formula.
     *
     * @param _kpi the kpi
     */
    public InvalidQueryFormulaException(final String _formula, final Throwable _throwable) {

        super("Formula " + _formula + " is not valid.", _throwable);
    }

}
