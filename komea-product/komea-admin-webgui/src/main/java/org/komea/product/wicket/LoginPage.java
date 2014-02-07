
package org.komea.product.wicket;



import org.apache.wicket.request.mapper.parameter.PageParameters;



/**
 * Settings page
 * 
 * @author sleroy
 */
public class LoginPage extends LayoutPage
{
    
    
    public LoginPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        
        
    }
    
    
    @Override
    public String getTitle() {
    
    
        return "Please login";
    }
}
