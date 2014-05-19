package org.komea.product.backend.groovy;

import groovy.lang.GroovyClassLoader;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.io.IOUtils;
import org.komea.eventory.api.engine.IQuery;
import org.komea.eventory.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 */
@Component
public class GroovyEngineService implements IGroovyEngineService {

	private static final Logger	LOGGER	= LoggerFactory.getLogger("groovy-service");
	private GroovyClassLoader	groovyClassLoader;

	@PreDestroy
	public void destroy() {

		LOGGER.error("Destorying Groovy engine.");
		IOUtils.closeQuietly(groovyClassLoader);
		groovyClassLoader = null;
	}

	@PostConstruct
	public void init() {

		LOGGER.info("Initializing Groovy engine.... please wait...");

		final ClassLoader parent = Thread.currentThread().getContextClassLoader();
		groovyClassLoader = new GroovyClassLoader(parent);

	}

	@Override
	public boolean isValidFormula(final String _formula) {

		return parseGroovyScript(_formula) instanceof IQuery;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.product.backend.groovy.IGroovyEngineService#parseGroovyScript
	 * (java.lang.String)
	 */
	@Override
	public <T> T parseGroovyScript(final String _groovyScript) {

		Class<T> groovyClass;
		try {
			groovyClass = groovyClassLoader.parseClass(_groovyScript);
			return ClassUtils.instantiate(groovyClass);
		} catch (final Exception e) {
			LOGGER.error("Script {} presents an error {}", _groovyScript, e);
			throw new GroovyParsingException(_groovyScript, e);
		}

	}
}
