
package org.komea.product.wicket;



import org.apache.wicket.request.mapper.parameter.PageParameters;



/**
 * Settings page
 * 
 * @author sleroy
 */
public class UnauthorizedPage extends LayoutPage
{
    
    
    public UnauthorizedPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        
        
    }
    
    
    @Override
    public String getTitle() {
    
    
        return "Access to this page is not authorized";
    }
}
