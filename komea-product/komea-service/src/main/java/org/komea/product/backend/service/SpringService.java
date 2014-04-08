/**
 * 
 */

package org.komea.product.backend.service;



import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;



/**
 * This service offers the possibility to developers to autowire pojo.
 * 
 * @author sleroy
 */
public class SpringService implements ApplicationContextAware, ISpringService
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringService.class);
    
    
    private ApplicationContext  applicationContext;
    
    
    
    /**
     * 
     */
    public SpringService() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.ISpringService#autowirePojo(java.lang.Object)
     */
    @Override
    public void autowirePojo(final Object _pojo) {
    
    
        Validate.notNull(_pojo);
        Validate.notNull(applicationContext, "Spring application has not been initialized");
        LOGGER.trace("Autowiring pojo {}", _pojo.getClass());
        final AutowireCapableBeanFactory autowireCapableBeanFactory =
                applicationContext.getAutowireCapableBeanFactory();
        autowireCapableBeanFactory.autowireBean(_pojo);
        autowireCapableBeanFactory.initializeBean(autowireCapableBeanFactory, "custombean");
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(final ApplicationContext _applicationContext)
            throws BeansException {
    
    
        LOGGER.info("Spring autowiring service is initialized");
        applicationContext = _applicationContext;
        
        
    }
}
