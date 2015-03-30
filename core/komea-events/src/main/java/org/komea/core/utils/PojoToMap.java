package org.komea.core.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * This class converts a pojo into a map.
 *
 * @author sleroy
 */
public class PojoToMap {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PojoToMap.class);
	private BeanInfo infos;
	private Class<?> clazz;

	public PojoToMap() {

		super();
	}

	public PojoToMap(final Class<?> _class) {

		super();
		initializeBeanInfosIfNeeded(_class);
	}

	public Map<String, Serializable> convertPojoInMap(final Object _pojo) {

		initializeBeanInfosIfNeeded(_pojo.getClass());
		final Map<String, Serializable> values = Maps.newHashMap();

		try {

			for (final PropertyDescriptor pdescriptor : infos
					.getPropertyDescriptors()) {
				if (isValidField(_pojo, pdescriptor)) {
					final Object object = pdescriptor.getReadMethod().invoke(
							_pojo);
					if (object instanceof Serializable) {
						values.put(pdescriptor.getName(), (Serializable) object);
					}
				}
			}
		} catch (final Exception e) {
			LOGGER.error("Problem to convert a pojo into a map", e);
		}
		return values;
	}

	private void initializeBeanInfosIfNeeded(final Class<?> _pojoClass) {

		if (infos == null || !_pojoClass.equals(clazz)) {

			try {
				clazz = _pojoClass;
				infos = Introspector.getBeanInfo(_pojoClass);
			} catch (final IntrospectionException e) {
				LOGGER.error("Unable to inspect pojo type.", e);
			}
		}

	}

	private boolean isSystemProperty(final PropertyDescriptor pdescriptor) {

		return pdescriptor.getName().equals("class");
	}

	/**
	 * This method check if the propertyDescriptor is transient in Class clazz.
	 * It will go upper in hierarchy tree, also as taking in consideration the
	 * implemented interfaces, as for clazz, as and for implemented interfaces
	 * by super classes.
	 *
	 * @param propertyDescriptor
	 * @param _clazz
	 * @return true if the property from propertyDescriptor in class clazz is
	 *         transient
	 */
	private boolean isTransient(final PropertyDescriptor propertyDescriptor,
			final Class<?> _clazz) {
		try {
			if (_clazz != null) {
				final Field field = _clazz.getDeclaredField(propertyDescriptor
						.getName());
				return Modifier.isTransient(field.getModifiers());
			} else {// if we are here this mean we processed
				// whole tree for class and interfaces and
				// not found field, we will consider that it is just an get
				// Method which can't have transient modifier
				return false;
			}
		} catch (final SecurityException e) {// if we have no access because of
			// security, we will mark it as
			// transient, as from
			// template engine we also will not be able to access it
			return true;
		} catch (final NoSuchFieldException e) {
			// in this case we will go upper with parent and interfaces
			// check parent class
			if (isTransient(propertyDescriptor, _clazz.getSuperclass())) {
				return true;
			}
			// check implemented interfaces
			final Class<?>[] interfaces = _clazz.getInterfaces();
			for (final Class<?> _interfase : interfaces) {
				if (isTransient(propertyDescriptor, _interfase)) {
					return true;
				}
			}
			return false;
		}

	}

	/**
	 * @param _pojo
	 * @param pdescriptor
	 * @return
	 */
	private boolean isValidField(final Object _pojo,
			final PropertyDescriptor pdescriptor) {
		return !isSystemProperty(pdescriptor) && !pdescriptor.isHidden()
				&& !pdescriptor.isExpert()
				&& pdescriptor.getReadMethod() != null
				&& !isTransient(pdescriptor, _pojo.getClass());
	}

}
