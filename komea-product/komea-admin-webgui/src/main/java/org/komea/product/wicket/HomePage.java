
package org.komea.product.wicket;



import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;



public class HomePage extends LayoutPage
{
    
    
    private static final long serialVersionUID = 1L;
    
    
    
    public HomePage(final PageParameters parameters) {
    
    
        super(parameters);
        
        // add(new Label("version", getApplication().getFrameworkSettings().getVersion()));
        // // Add a FeedbackPanel for displaying our messages
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);
        
        // Add a form with an onSubmit implementation that sets a message
        add(new Form("form")
        {
            
            
            @Override
            protected void onSubmit() {
            
            
                info("the form was submitted!");
            }
        });
        
        
    }
}
