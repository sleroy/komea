/**
 * 
 */

package org.komea.product.cep.filter;



import java.io.Serializable;

import org.apache.commons.jxpath.JXPathContext;
import org.komea.eventory.api.filters.IEventFilter;



/**
 * This class defines an xpath filter;
 * 
 * @author sleroy
 */
public class XPathFilter implements IEventFilter
{
    
    
    private final String xpathFormula;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.IEventFilter#isFiltered(java.io.Serializable)
     */
    public XPathFilter(final String _xpathFormula) {
    
    
        super();
        xpathFormula = _xpathFormula;
    }
    
    
    @Override
    public boolean isFiltered(final Serializable _event) {
    
    
        final JXPathContext newContext = JXPathContext.newContext(_event);
        return (Boolean) newContext.getValue(xpathFormula, Boolean.class);
    }
}
