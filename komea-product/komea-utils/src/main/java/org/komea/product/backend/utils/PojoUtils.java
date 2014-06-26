/**
 *
 */

package org.komea.product.backend.utils;



import java.util.Map;
import java.util.Map.Entry;

import jodd.bean.BeanUtil;

import org.springframework.beans.BeanUtils;



/**
 * @author sleroy
 */
public class PojoUtils
{
    
    
    public static <T> T injectInPojo(final Class<T> _class, final Map<String, ?> _properties) {
    
    
        final T bean = BeanUtils.instantiate(_class);
        for (final Entry<String, ?> entry : _properties.entrySet()) {
            BeanUtil.setProperty(bean, entry.getKey(), entry.getValue());
        }
        return bean;
    }
}
