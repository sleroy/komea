/**
 * 
 */

package org.komea.product.plugins.kpi.xpath;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;
import org.apache.commons.lang.Validate;
import org.komea.product.cep.api.ICEPEventStorage;
import org.komea.product.cep.api.ICEPStatement;



/**
 * @author sleroy
 */
public class XPathTree
{
    
    
    private final List<XPathEventList> eventLists = new ArrayList<XPathEventList>();
    
    
    
    /**
     * Builds the xpath tree
     * 
     * @param _statement
     *            the statement.
     */
    public XPathTree(final ICEPStatement _statement) {
    
    
        final List<ICEPEventStorage<?>> eventStorages = _statement.getEventStorages();
        for (final ICEPEventStorage eventStorage : eventStorages) {
            addEventList(new XPathEventList(eventStorage.getFilterName(), eventStorage.getCache()
                    .getAllValues()));
        }
        
    }
    
    
    /**
     * Adds an event list
     * 
     * @param _xPathEventList
     *            the xpath event list.
     */
    public void addEventList(final XPathEventList _xPathEventList) {
    
    
        Validate.notNull(_xPathEventList);
        
        eventLists.add(_xPathEventList);
    }
    
    
    /**
     * Dump xpath tree into a string
     * 
     * @return the xpath tree.
     */
    public String dumpTree() {
    
    
        final JXPathContext newContext = JXPathContext.newContext(this);
        final Iterator<Pointer> iterate = newContext.iteratePointers("//*");
        final StringBuilder sBuilder = new StringBuilder();
        while (iterate.hasNext()) {
            sBuilder.append(iterate.next()).append('\n');
        }
        return sBuilder.toString();
    }
    
    
    public List<XPathEventList> getEventLists() {
    
    
        return eventLists;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "XPathTree [eventLists=" + eventLists + "]";
    }
}
