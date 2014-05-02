/**
 * 
 */

package org.komea.eventory.query.xpath;



import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.komea.eventory.api.engine.ICEPEventStorage;
import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.eventory.api.formula.tuple.IEventGroup;



/**
 * @author sleroy
 */
public class XPathTree
{
    
    
    /**
     * 
     */
    private static final String        DEFAULT = "default";
    private final List<XPathEventList> streams = new ArrayList<XPathEventList>();
    
    
    
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
     * Builds an xpath tree from an event group.
     * 
     * @param _value
     *            the value.
     */
    public XPathTree(final IEventGroup _value) {
    
    
        addEventList(new XPathEventList(DEFAULT, _value.getEvents()));
    }
    
    
    /**
     * Adds an event list
     * 
     * @param _xPathEventList
     *            the xpath event list.
     */
    public void addEventList(final XPathEventList _xPathEventList) {
    
    
        Validate.notNull(_xPathEventList);
        
        streams.add(_xPathEventList);
    }
    
    
    /**
     * Dump xpath tree into a string
     * 
     * @return the xpath tree.
     */
    public String dumpTree() {
    
    
        return XPathUtils.dumpXpath(this);
    }
    
    
    /**
     * Returns the list streams
     * 
     * @return the streams.
     */
    public List<XPathEventList> getStreams() {
    
    
        return streams;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "XPathTree [streams=" + streams + "]";
    }
}
