/**
 * 
 */
package org.komea.product.wicket.utils;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.komea.product.database.api.IHasKey;

/**
 * @author sleroy
 * 
 */
final class IHasKeyChoiceRenderer extends ChoiceRenderer<IHasKey> {

	@Override
	public Object getDisplayValue(final IHasKey t) {

		return t.getDisplayName();
	}

	@Override
	public String getIdValue(final IHasKey t, final int i) {

		return String.valueOf(t.getId());
	}

}