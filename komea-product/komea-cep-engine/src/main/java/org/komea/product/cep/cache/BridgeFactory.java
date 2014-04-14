/**
 * 
 */

package org.komea.product.cep.cache;



import java.lang.reflect.Constructor;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.Validate;
import org.komea.eventory.api.bridge.IEventBridge;
import org.komea.eventory.api.bridge.IEventBridgeFactory;
import org.komea.eventory.api.engine.ICEPConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;



/**
 * This class defines the cache factory
 * 
 * @author sleroy
 */
@Service
public class BridgeFactory implements IEventBridgeFactory
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BridgeFactory.class);
    private Constructor<?>      constructor;
    
    
    @Value(
        value = "${bridgeImplementation}")
    private String              implementation;
    
    
    
    /**
     * 
     */
    public BridgeFactory() {
    
    
        super();
    }
    
    
    @PostConstruct
    public void init() {
    
    
        LOGGER.info("Bridge implementation is {}", implementation);
        Validate.isTrue(!Strings.isNullOrEmpty(implementation));
        try {
            final Class<?> implemClass =
                    Thread.currentThread().getContextClassLoader().loadClass(implementation);
            constructor = implemClass.getConstructor(ICEPConfiguration.class);
            
            
        } catch (final Exception e) {
            throw new BeanInitializationException(e.getMessage(), e);
        }
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.bridge.IEventBridgeFactory#newBridge(org.komea.eventory.api.engine.ICEPConfiguration)
     */
    @Override
    public IEventBridge newBridge(final ICEPConfiguration _arg0) {
    
    
        try {
            return (IEventBridge) constructor.newInstance(_arg0);
        } catch (final Exception e) {
            throw new BeanInitializationException(e.getMessage(), e);
        }
    }
    
    
}
