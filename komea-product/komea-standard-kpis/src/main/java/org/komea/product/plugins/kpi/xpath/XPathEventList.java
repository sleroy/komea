/**
 * 
 */

package org.komea.product.plugins.kpi.xpath;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



/**
 * @author sleroy
 */
public class XPathEventList
{
    
    
    private Collection events = new ArrayList<Object>();
    
    
    private String     name;
    
    
    
    /**
     * Builds an xpath event list
     * 
     * @param _name
     *            the name
     * @param _events
     *            the events.
     */
    public XPathEventList(final String _name, final Collection<Object> _events) {
    
    
        super();
        name = _name;
        events = _events;
    }
    
    
    /**
     * @return the events
     */
    public Collection getEvents() {
    
    
        return events;
    }
    
    
    /**
     * @return the name
     */
    public String getName() {
    
    
        return name;
    }
    
    
    /**
     * @param _events
     *            the events to set
     */
    public void setEvents(final List<Object> _events) {
    
    
        events = _events;
    }
    
    
    /**
     * @param _name
     *            the name to set
     */
    public void setName(final String _name) {
    
    
        name = _name;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "XPathEventList [events=" + events + ", name=" + name + "]";
    }
    
}
