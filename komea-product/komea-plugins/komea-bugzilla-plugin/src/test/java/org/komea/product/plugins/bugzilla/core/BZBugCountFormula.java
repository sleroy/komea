/**
 * 
 */

package org.komea.product.plugins.bugzilla.core;

import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.KpiResult;

/**
 * This class defines a formula that counts bugilla bug.
 * 
 * @author sleroy
 */
public class BZBugCountFormula implements ICEPFormula<IEvent, KpiResult> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.product.cep.api.formula.ICEPFormula#compute(org.komea.eventory
	 * .api.engine.ICEPStatement, java.util.Map)
	 */
	@Override
	public KpiResult compute(final ICEPStatement<IEvent> _arg0) {

		return new KpiResult();
	}

}
