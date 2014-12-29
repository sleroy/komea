
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
                if (!isSystemProperty(pdescriptor) && !pdescriptor.isHidden() && !pdescriptor.isExpert() && pdescriptor.getReadMethod() != null && !isTransient(pdescriptor, _pojo.getClass())) {
                    values.put(pdescriptor.getName(), (Serializable) pdescriptor.getReadMethod().invoke(_pojo));
                }
            }
        } catch (final Exception e) {
            LOGGER.error("Problem to convert a pojo into a map", e.getMessage(), e);
        }
        return values;
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
                            Field field = _clazz.getDeclaredField(propertyDescriptor
                                            .getName());
                            return Modifier.isTransient(field.getModifiers());
                    } else {// if we are here this mean we processed
                                    // whole tree for class and interfaces and
                                    // not found field, we will consider that it is just an get
                                    // Method which can't have transient modifier
                            return false;
                    }
            } catch (SecurityException e) {// if we have no access because of
                                                                            // security, we will mark it as
                                                                            // transient, as from
                    // template engine we also will not be able to access it
                    return true;
            } catch (NoSuchFieldException e) {
                    // in this case we will go upper with parent and interfaces
                    // check parent class
                    if (isTransient(propertyDescriptor, _clazz.getSuperclass())) {
                        return true;
                    }
                    // check implemented interfaces
                    Class<?>[] interfaces = _clazz.getInterfaces();
                    for (Class<?> _interfase : interfaces) {
                            if (isTransient(propertyDescriptor, _interfase)) {
                                return true;
                            }
                    }
                    return false;
            }

    }

    private boolean isSystemProperty(final PropertyDescriptor pdescriptor) {
    
        return pdescriptor.getName().equals("class");
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
