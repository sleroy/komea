package org.komea.product.eventory.utils.validation;

import java.lang.annotation.ElementType;

import javax.validation.Path;
import javax.validation.Path.Node;

import org.hibernate.validator.internal.engine.resolver.DefaultTraversableResolver;

public class MyValidationTraversableResolver extends DefaultTraversableResolver {

	public MyValidationTraversableResolver() {
		super();
	}

	@Override
	public boolean isCascadable(final Object _traversableObject,
			final Node _traversableProperty, final Class<?> _rootBeanType,
			final Path _pathToTraversableObject, final ElementType _elementType) {
		return true;
	}

	@Override
	public boolean isReachable(final Object _traversableObject,
			final Node _traversableProperty, final Class<?> _rootBeanType,
			final Path _pathToTraversableObject, final ElementType _elementType) {
		return true;
	}

}