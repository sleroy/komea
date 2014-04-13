/**
 * 
 */

package org.komea.product.cep.query.xpath;



import java.util.Iterator;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;



/**
 * @author sleroy
 */
public class XPathUtils
{
    
    
    /**
     * Dump the xpath tree.
     * 
     * @param _object
     *            the tree
     * @return the xpath tree into a string;
     */
    public static String dumpXpath(final Object _object) {
    
    
        final JXPathContext newContext = JXPathContext.newContext(_object);
        final Iterator<Pointer> iterate = newContext.iteratePointers("//*");
        final StringBuilder sBuilder = new StringBuilder();
        while (iterate.hasNext()) {
            sBuilder.append(iterate.next()).append('\n');
        }
        return sBuilder.toString();
    }
}
