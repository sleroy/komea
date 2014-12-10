
package org.komea.core.utils;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * This class converts a pojo into a map.
 *
 * @author sleroy
 */
public class PojoToMap
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PojoToMap.class);
    private BeanInfo            infos;
    private Class<?>            clazz;
    
    public PojoToMap() {
    
        super();
    }
    
    public Map<String, Serializable> convertPojoInMap(final Object _pojo) {
    
        initializeBeanInfosIfNeeded(_pojo);
        final Map<String, Serializable> values = Maps.newHashMap();
        
        try {
            for (final PropertyDescriptor pdescriptor : this.infos.getPropertyDescriptors()) {
                if (!pdescriptor.isHidden() && !pdescriptor.isExpert() && pdescriptor.getReadMethod() != null) {
                    values.put(pdescriptor.getName(), (Serializable) pdescriptor.getReadMethod().invoke(_pojo));
                }
            }
        } catch (final Exception e) {
            LOGGER.error("Problem to convert a pojo into a map", e.getMessage(), e);
        }
        return values;
    }
    
    private void initializeBeanInfosIfNeeded(final Object _pojo) {
    
        if (this.infos == null || !_pojo.getClass().equals(this.clazz)) {
            
            try {
                this.clazz = _pojo.getClass();
                this.infos = Introspector.getBeanInfo(_pojo.getClass());
            } catch (final IntrospectionException e) {
                LOGGER.error("Unable to inspect pojo type.", e.getMessage(), e);
            }
        }
        
    }
    
}
