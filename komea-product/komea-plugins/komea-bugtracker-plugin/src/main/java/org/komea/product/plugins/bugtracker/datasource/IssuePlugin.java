/**
 * 
 */

package org.komea.product.plugins.bugtracker.datasource;



import java.util.List;

import org.komea.product.plugins.bugtracking.model.IIssue;
import org.komea.product.plugins.bugtracking.model.IIssuePlugin;
import org.komea.product.plugins.datasource.DynamicDataTable;



/**
 * @author sleroy
 */
public class IssuePlugin extends DynamicDataTable<IIssue> implements IIssuePlugin
{
    
    
    /**
     * 
     */
    private static final long serialVersionUID = -2071347500313251773L;
    
    
    
    /**
     * @param _issues
     */
    public IssuePlugin(final List<IIssue> _issues) {
    
    
        super(_issues);
    }
    
    
}
