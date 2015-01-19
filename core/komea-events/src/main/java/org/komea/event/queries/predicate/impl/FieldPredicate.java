/**
 *
 */
package org.komea.event.queries.predicate.impl;

import org.komea.event.model.beans.FlatEvent;

import com.google.common.base.Predicate;

/**
 * This class defines a field predicate.
 *
 * @author sleroy
 */
public class FieldPredicate implements Predicate<FlatEvent> {

	/**
	 * Builds a new predicate
	 *
	 * @param _fieldName
	 *            the field name
	 * @param _target
	 *            the target
	 * @return the predicate
	 */
	public static Predicate<FlatEvent> build(final String _fieldName,
			final Object _target) {

		return new FieldPredicate(_fieldName, _target);
	}

	private final Object	target;

	private final String	fieldName;

	public FieldPredicate(final String _fieldName, final Object _target) {
		fieldName = _fieldName;
		target = _target;
	}

	/*
	 * (non-Javadoc)
	 * @see com.google.common.base.Predicate#apply(java.lang.Object)
	 */
	@Override
	public boolean apply(final FlatEvent _arg0) {

		return _arg0.fieldEquals(fieldName, target);
	}
}
