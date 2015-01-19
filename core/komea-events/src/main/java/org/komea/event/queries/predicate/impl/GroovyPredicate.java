/**
 *
 */
package org.komea.event.queries.predicate.impl;

import groovy.lang.Closure;

import org.komea.event.model.beans.FlatEvent;

import com.google.common.base.Predicate;

/**
 * @author sleroy
 *
 */
public class GroovyPredicate implements Predicate<FlatEvent> {

	private final Closure<Boolean>	closure;

	/**
	 * @param _closure
	 */
	public GroovyPredicate(Closure<Boolean> _closure) {
		closure = _closure;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.common.base.Predicate#apply(java.lang.Object)
	 */
	@Override
	public boolean apply(FlatEvent _arg0) {

		return closure.call(_arg0);
	}

}
