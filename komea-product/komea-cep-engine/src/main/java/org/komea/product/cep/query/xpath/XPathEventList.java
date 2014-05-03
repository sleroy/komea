/**
 * 
 */

package org.komea.product.cep.query.xpath;



import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.Validate;



/**
 * @author sleroy
 */
public class XPathEventList
{
    
    
    private Collection   event = new ArrayList<Object>();
    
    
    private final String name;
    
    
    
    /**
     * Builds an xpath event list
     * 
     * @param _name
     *            the name
     * @param _events
     *            the event.
     */
    public XPathEventList(final String _name, final Collection<?> _events) {
    
    
        super();
        name = _name;
        event = _events;
        Validate.notNull(_name);
        Validate.notNull(_events);
    }
    
    
    /**
     * @return the event
     */
    public Collection getEvent() {
    
    
        return event;
    }
    
    
    /**
     * @return the name
     */
    public String getName() {
    
    
        return name;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "XPathEventList [event=" + event + ", name=" + name + "]";
    }
    
}
