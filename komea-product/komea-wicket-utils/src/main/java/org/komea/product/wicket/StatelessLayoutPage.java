
package org.komea.product.wicket;



import java.util.Iterator;

import org.apache.wicket.Component;
import org.apache.wicket.request.mapper.parameter.PageParameters;



/**
 * Defines the general layout for all komea pages.
 *
 * @author sleroy
 */
public abstract class StatelessLayoutPage extends LayoutPage
{
    
    
    public StatelessLayoutPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        setStatelessHint(true);
        // DEBUG MODE
        checkStateless();
        
    }


    /**
     * Check if the page is stateless
     */
    public void checkStateless() {


        if (!isPageStateless()) {
            final Iterator<Component> iterator = this.iterator();
            while (iterator.hasNext()) {
                final Component next = iterator.next();
                if (!next.isStateless()) {
                    LOGGER.info("Page {} component {} is not stateless", getPageRelativePath(),
                            next.getPath());
                }
            }
        }
    }
    
    
    @Override
    public boolean isVersioned() {
    
    
        return false;
    }
}
