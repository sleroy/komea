// $Id: ConstraintViolationException.java 17620 2009-10-04 19:19:28Z hardy.ferentschik $
/*
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat, Inc. and/or its affiliates, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.komea.microservices.events.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;

/**
 * Reports the result of constraint violations `@author Sylvain Leroy
 *
 * @author Emmanuel Bernard
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class KomeaConstraintViolationException extends ValidationException {

	private final Set<ConstraintViolation<?>> constraintViolations;

	/**
	 * Creates a constraint violation report
	 * 
	 * @param constraintViolations
	 *            <code>Set</code> of <code>ConstraintViolation</code>
	 */
	public KomeaConstraintViolationException(
			final Set<ConstraintViolation<?>> constraintViolations) {

		super("Constraintd violated for an object : " + constraintViolations);
		this.constraintViolations = constraintViolations;
	}

	/**
	 * Creates a constraint violation report
	 * 
	 * @param message
	 *            error message
	 * @param constraintViolations
	 *            <code>Set</code> of <code>ConstraintViolation</code>
	 */
	public KomeaConstraintViolationException(final String message,
			final Set<ConstraintViolation<?>> constraintViolations) {

		super(message);
		this.constraintViolations = constraintViolations;
	}

	/**
	 * Set of constraint violations reported during a validation
	 * 
	 * 
	 * @return <code>Set</code> of <code>ConstraintViolation</code>
	 */
	public Set<ConstraintViolation<?>> getConstraintViolations() {

		return this.constraintViolations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return "ConstraintViolationException [constraintViolations="
				+ this.constraintViolations + "]";
	}

}
