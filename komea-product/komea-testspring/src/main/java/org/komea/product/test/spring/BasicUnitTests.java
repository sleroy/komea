/**
 * 
 */

package org.komea.product.test.spring;



import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.beans.BeanUtils;

import com.google.common.base.Strings;



/**
 * This class has the purpose to perform the basic tests on classes to avoid boilerplate code;
 * 
 * @author sleroy
 */
public class BasicUnitTests
{
    
    
    private final Class<?> implementationClass;
    
    
    
    /**
     * 
     */
    public BasicUnitTests(final Class<?> _implementationClass) {
    
    
        implementationClass = _implementationClass;
        
        
    }
    
    
    /**
     * Tests getter and setters
     * 
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public void testGetterSetter() {
    
    
        final PropertyDescriptor[] propertyDescriptors =
                BeanUtils.getPropertyDescriptors(implementationClass);
        
        final Object instantiate = BeanUtils.instantiate(implementationClass);
        
        for (final PropertyDescriptor descriptor : propertyDescriptors) {
            if (descriptor.getReadMethod() != null && descriptor.getWriteMethod() != null) {
                final Object parameter =
                        createParameter(descriptor.getWriteMethod().getParameterTypes()[0]);
                
                try {
                    descriptor.getWriteMethod().invoke(instantiate, parameter);
                    Assert.assertEquals(parameter, descriptor.getReadMethod().invoke(instantiate));
                } catch (final Exception e) {
                    throw new IllegalArgumentException("Test has failed for the reason " + e, e);
                }
            }
            
            
        }
    }
    
    
    /**
     * Test toString() methods
     */
    public void testToString() {
    
    
        final Object instantiate = BeanUtils.instantiate(implementationClass);
        Assert.assertNotNull("toString never returns null", instantiate);
        Assert.assertTrue("toString should not be empty",
                !Strings.isNullOrEmpty(instantiate.toString()));
        
    }
    
    
    private Object createParameter(final Class<?> _class) {
    
    
        try {
            return Mockito.mock(_class);
            
        } catch (final MockitoException exception) {
            try {
                return BeanUtils.instantiate(_class);
            } catch (final Exception e) {
                return null;
            }
            
        }
        
    }
}
