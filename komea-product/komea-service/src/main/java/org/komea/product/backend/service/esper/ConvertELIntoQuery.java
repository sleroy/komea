/**
 *
 */
package org.komea.product.backend.service.esper;

import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.exceptions.InvalidQueryFormulaException;
import org.komea.product.cep.formula.ElFormula;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to convert a spring el into a cep query.
 * 
 * @author sleroy
 */
public class ConvertELIntoQuery {

	private static final Logger	LOGGER	= LoggerFactory.getLogger(ConvertELIntoQuery.class);

	/**
	 * Parses the formula into a query.
	 * 
	 * @param _formula
	 *            the formula
	 * @return true if the formula is valid.
	 */
	public static boolean isValidFormula(final String _formula) {
		IQuery parseEL = null;
		try {
			parseEL = parseEL(_formula);
		} catch (final Exception e) {
			LOGGER.warn("Invalid formula {}", _formula);
		}
		return isValidFormulaObject(parseEL);

	}

	public static boolean isValidFormulaObject(final Object _formula) {
		return _formula instanceof IQuery;

	}

	/**
	 * Parses the formula into a query.
	 * 
	 * @param _formula
	 *            the formula
	 * @return the query.
	 */
	public static IQuery parseEL(final String _formula) {
		try {
			final ElFormula<IQuery> elFormula = new ElFormula<IQuery>(_formula, IQuery.class);
			return elFormula.getValue(null);
		} catch (final Exception ex) {
			throw new InvalidQueryFormulaException(_formula, ex);
		}
	}

}
