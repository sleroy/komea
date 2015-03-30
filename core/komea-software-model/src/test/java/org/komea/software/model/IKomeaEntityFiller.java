package org.komea.software.model;

/**
 * This interface is a helper to convert a Pojo into a Komea Entity. The pojo is
 * converted thanks to BeanMap. The pojo must provide getter/setters.
 *
 * @author sleroy
 *
 * @param <T>
 */
public interface IKomeaEntityFiller<T> {

	void put(T _pojo);
}
