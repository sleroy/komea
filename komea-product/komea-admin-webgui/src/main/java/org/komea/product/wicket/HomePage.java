
package org.komea.product.wicket;



import org.apache.wicket.request.mapper.parameter.PageParameters;



public class HomePage extends LayoutPage
{
    
    
    private static final long serialVersionUID = 1L;
    
    
    
    public HomePage(final PageParameters parameters) {
    
    
        super(parameters);
        
        
    }
    
    
    @Override
    public String getTitle() {
    
    
        return "Welcome in Komea Administration!";
    }
}
