/**
 *
 */

package org.komea.product.wicket;



import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;



/**
 * @author sleroy
 */
public class MyInternalErrorPage extends StatelessLayoutPage
{
    
    
    public MyInternalErrorPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        // In WebPage
        add(new FeedbackPanel("feedback"));
        error("An error has been encountered");
        
    }
    
    
    public MyInternalErrorPage(final PageParameters _pageParameters, final Exception _e) {


        super(_pageParameters);
        // In WebPage
        add(new FeedbackPanel("feedback"));
        error("An error has been encountered : " + _e.getMessage());
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(new StringWriter());
        _e.printStackTrace(printWriter);
        error(stringWriter.getBuffer().toString());
    }
    
    
    @Override
    public boolean isErrorPage() {
    
    
        return true;
    }
    
    
    @Override
    public boolean isVersioned() {
    
    
        return false;
    }
    
    
    @Override
    protected void configureResponse(final WebResponse _response) {
    
    
        super.configureResponse(_response);
        _response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    
}
