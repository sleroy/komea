
package org.komea.product.wicket;



import org.apache.wicket.request.mapper.parameter.PageParameters;



/**
 * Settings page
 * 
 * @author sleroy
 */
public class UnauthorizedPage extends StatelessLayoutPage
{
    
    
    public UnauthorizedPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        
        
    }

    @Override
    public String getTitle() {
        return getString("unauthorized.title");
    }
    
    
}
