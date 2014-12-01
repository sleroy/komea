package org.komea.core.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * This class converts a pojo into a map.
 *
 * @author sleroy
 *
 */
public class PojoToMap {

	private static final Logger	LOGGER	= LoggerFactory.getLogger(PojoToMap.class);

	public PojoToMap() {
		super();
	}

	public Map<String, Object> convertPojoInMap(final Object _pojo) {
		final Map<String, Object> values = Maps.newHashMap();
		BeanInfo info;
		try {
			info = Introspector.getBeanInfo(_pojo.getClass());

			for (final PropertyDescriptor pdescriptor : info.getPropertyDescriptors()) {
				if (!pdescriptor.isHidden() && !pdescriptor.isExpert() && pdescriptor.getReadMethod() != null
						&& pdescriptor.getWriteMethod() != null) {
					values.put(pdescriptor.getName(), pdescriptor.getReadMethod().invoke(_pojo));
				}
			}
		} catch (final Exception e) {
			LOGGER.error("Problem to convert a pojo into a map", e.getMessage(), e);
		}
		return values;
	}

}
